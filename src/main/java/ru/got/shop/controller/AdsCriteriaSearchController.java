package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.dto.AdCriteriaDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Ads_;
import ru.got.shop.repository.AdsRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/v2/ads")
@RestController
@RequiredArgsConstructor
public class AdsCriteriaSearchController {

    private final AdsRepository adsRepository;

    private final AdsMapper adsMapper;

    /**
     * Ads Criteria search
     */
    @PostMapping("/filter")
    public List<AdDto> getAdsByCriteria(@RequestBody AdCriteriaDto adCriteriaDto, Pageable pageable) {
        List<Ads> adsByFilter = adsRepository.findAll(
                (root, query, cb) -> {
                    Predicate conjunction = cb.conjunction();

                    if (Objects.nonNull(adCriteriaDto.getTitle())) {
                        Predicate like = cb.like(cb.upper(root.get(Ads_.title)), "%" + adCriteriaDto.getTitle().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    if (Objects.nonNull(adCriteriaDto.getDescription())) {
                        Predicate like = cb.like(cb.upper(root.get(Ads_.description)), "%" + adCriteriaDto.getDescription().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    if (Objects.nonNull(adCriteriaDto.getMinPrice())) {
                        Predicate greater = cb.greaterThanOrEqualTo(root.get(Ads_.price), adCriteriaDto.getMinPrice());
                        conjunction = cb.and(conjunction, greater);
                    }
                    if (Objects.nonNull(adCriteriaDto.getMaxPrice())) {
                        Predicate less = cb.lessThanOrEqualTo(root.get(Ads_.price), adCriteriaDto.getMaxPrice());
                        conjunction = cb.and(conjunction, less);
                    }
                    query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
                    return conjunction;
                }
                , pageable).getContent();

        return adsMapper.toDtos(adsByFilter);
    }
}
