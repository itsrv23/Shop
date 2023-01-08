package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.got.shop.dto.AdCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.exception.*;
import ru.got.shop.mapper.AdCommentMapper;
import ru.got.shop.mapper.ResponseWrapperAdCommentMapper;
import ru.got.shop.model.Ad;
import ru.got.shop.model.AdComment;
import ru.got.shop.repository.AdCommentRepository;
import ru.got.shop.repository.AdRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.security.Role;
import ru.got.shop.service.AdCommentService;
import ru.got.shop.service.UserService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AdCommentServiceImpl implements AdCommentService, AuthenticationFacade {

    private final ResponseWrapperAdCommentMapper responseWrapperAdCommentMapper;
    private final AdCommentMapper adCommentMapper;
    private final AdCommentRepository adCommentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public AdCommentDto addAdsComment(Integer adId, AdCommentDto comment) {

        AdComment newComment = adCommentMapper.toEntity(comment);
        newComment.setId(null);
        newComment.setUserId(userRepository.findFirstByEmail(getLogin()).orElseThrow(() -> new ru.got.shop.exception.UserNotFoundException(getLogin())));
        newComment.setCreatedAt(OffsetDateTime.now());
        newComment.setAdId(adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException(adId)));
        return adCommentMapper.toDto(adCommentRepository.save(newComment));
    }

    @Override
    public AdCommentDto deleteAdsComment(Integer adId, Integer id) {
        AdCommentDto commentToDelete = getAdsComment(adId, id);
        testUserHasAccessToEditComment(commentToDelete);
        adCommentRepository.deleteById(id);
        return commentToDelete;
    }

    @Override
    public AdCommentDto getAdsComment(Integer adId, Integer id) {
        testAdsHasComment(adId, id);
        Ad adByAdId = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException(adId));
        List<AdComment> allAdCommentsByAdId = adByAdId.getAdComment();
        AdComment neededCommentWithId = allAdCommentsByAdId.stream()
                .filter(adsCom -> (adsCom.getId().equals(id)))
                .findFirst()
                .orElseThrow(() -> new AdCommentNotFoundException(id));
        return adCommentMapper.toDto(neededCommentWithId);
    }

    @Override
    public ResponseWrapperAdsCommentDto getAdsComments(Integer adId) {
        Ad adByAdId = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException(adId));
        List<AdComment> allAdCommentsByAdId = adByAdId.getAdComment();
        return responseWrapperAdCommentMapper.toDto(allAdCommentsByAdId.size(),
                adCommentMapper.toDtos(allAdCommentsByAdId));
    }

    @Override
    public AdCommentDto updateAdsComment(Integer adId, Integer id, AdCommentDto comment) {
        testAdsHasComment(adId, id);
        testUserHasAccessToEditComment(comment);
        AdComment updateComment = adCommentMapper.toEntity(comment);
        updateComment.setId(id);
        updateComment.setAdId(adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException(adId)));
        updateComment = adCommentRepository.save(updateComment);
        return adCommentMapper.toDto(updateComment);
    }

    private boolean testAdsHasComment(Integer adId, Integer id) {
        Ad adByAdId = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException(adId));
        AdComment commentById = adCommentRepository.findById(id)
                .orElseThrow(() -> new AdCommentNotFoundException(id));
        if (!adByAdId.getAdComment().contains(commentById)) {
            throw new AdWithCommentNotFoundException(adId, id);
        }
        return true;
    }

    private boolean testUserHasAccessToEditComment(AdCommentDto comment) {
        if (!Role.ROLE_ADMIN.equals((userRepository.findFirstByEmail(getLogin()))
                .orElseThrow(() -> new UserNotFoundException(getLogin())).getRoleGroup())
                &&
                (!Objects.equals(userService.findUser(getLogin()).getId(), comment.getAuthor()))) {
            throw new ForbiddenException("Access id denied, someone else's comment. ");
        }
        return true;
    }
}
