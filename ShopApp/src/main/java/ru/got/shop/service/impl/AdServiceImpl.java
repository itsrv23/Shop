package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.dto.*;
import ru.got.shop.exception.AdNotFoundException;
import ru.got.shop.exception.CustomIOException;
import ru.got.shop.exception.IllegalPriceValueException;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.mapper.AdMapper;
import ru.got.shop.mapper.FullAdMapper;
import ru.got.shop.mapper.PictureMapper;
import ru.got.shop.model.*;
import ru.got.shop.repository.AdRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.service.AdService;
import ru.got.shop.service.PictureService;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService, AuthenticationFacade {

    private final AdMapper adMapper;
    private final FullAdMapper fullAdMapper;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    @Qualifier("pictureDiskServiceImpl")
    private final PictureService pictureService;
    private final PictureMapper pictureMapper;

    @Override
    public AdDto addAd(AdCreateDto adCreateDto, MultipartFile file) {
        try {
            User user = getUser();
            Picture picture = pictureMapper.mapToPicture(file);
            picture = pictureService.download(picture);
            Ad ad = adMapper.buildAds(user, adCreateDto, picture);
            Ad ad1 = adRepository.save(ad);
            return adMapper.toDto(ad1);
        } catch (IOException e) {
            throw new CustomIOException("Something went wrong while picture file reading ");
        }
    }

    @Override
    public byte[] getImageById(String uuid) {
        return pictureService.upload(UUID.fromString(uuid));
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<Ad> ads = adRepository.findAll();
        List<AdDto> adDtoList = adMapper.toDtos(ads);
        return ResponseWrapperAdsDto.builder().results(adDtoList).count(adDtoList.size()).build();
    }

    @Transactional
    @Override
    public ResponseWrapperAdsDto getMyAds() {
        List<Ad> adList = adRepository.findAllByUserId(getUser());
        if (adList.isEmpty()) {
            throw new AdNotFoundException("There is an empty myAdsList.");
        }
        List<AdDto> adDtoList = adMapper.toDtos(adList);
        return ResponseWrapperAdsDto.builder().results(adDtoList).count(adDtoList.size()).build();
    }

    @Override
    public FullAdDto getFullAdDto(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        User user = userRepository.findById(ad.getUserId().getId()).orElseThrow(() -> new UserNotFoundException(id));
        return fullAdMapper.toDto(user, ad);
    }

    @Override
    public void removeAd(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        adRepository.delete(ad);
    }

    @Override
    public AdDto updateAd(Integer id, AdCreateDto adCreateDto) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        ad = adMapper.updateAds(adCreateDto, ad);
        return adMapper.toDto(adRepository.save(ad));
    }

    @Override
    public AdDto updatePicture(Integer id, MultipartFile file) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        Picture picture;
        try {
            picture = pictureMapper.mapToPicture(file);
        } catch (IOException e) {
            throw new CustomIOException("Something went wrong wile picture reading");
        }
        if (ad.getPicture() == null) {
            Picture pictureNew = pictureService.download(picture);
            ad.setPicture(pictureNew);
            adRepository.save(ad);
        } else {
            UUID uuid = ad.getPicture().getUuid();
            pictureService.update(uuid, picture);
            adRepository.save(ad);
        }
        return adMapper.toDto(ad);
    }

    private User getUser() {
        String login = getLogin();
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public List<AdDto> getAdsByCriteria(AdCriteriaDto adCriteriaDto, Pageable pageable) {
        if (adCriteriaDto.getMinPrice() > adCriteriaDto.getMaxPrice()) {
            throw new IllegalPriceValueException("Entered mininum value should be less than maximum value. ");
        }

        List<Ad> adByFilter = adRepository.findAll((root, query, cb) -> {
            Predicate conjunction = cb.conjunction();

            if (Objects.nonNull(adCriteriaDto.getTitle())) {
                Predicate like = cb.like(cb.upper(root.get(Ad_.title)),
                        "%" + adCriteriaDto.getTitle().toUpperCase() + "%");
                conjunction = cb.and(conjunction, like);
            }
            if (Objects.nonNull(adCriteriaDto.getDescription())) {
                Predicate like = cb.like(cb.upper(root.get(Ad_.description)),
                        "%" + adCriteriaDto.getDescription().toUpperCase() + "%");
                conjunction = cb.and(conjunction, like);
            }
            if (Objects.nonNull(adCriteriaDto.getMinPrice())) {
                Predicate greater = cb.greaterThanOrEqualTo(root.get(Ad_.price), adCriteriaDto.getMinPrice());
                conjunction = cb.and(conjunction, greater);
            }
            if (Objects.nonNull(adCriteriaDto.getMaxPrice())) {
                Predicate less = cb.lessThanOrEqualTo(root.get(Ad_.price), adCriteriaDto.getMaxPrice());
                conjunction = cb.and(conjunction, less);
            }
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
            return conjunction;
        }, pageable).getContent();

        return adMapper.toDtos(adByFilter);
    }
}
