package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.exception.*;
import ru.got.shop.mapper.AdsCommentMapper;
import ru.got.shop.mapper.ResponseWrapperAdsCommentMapper;
import ru.got.shop.model.Ads;
import ru.got.shop.model.AdsComment;
import ru.got.shop.repository.AdsCommentRepository;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.security.Role;
import ru.got.shop.service.AdsCommentService;
import ru.got.shop.service.UserService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AdsCommentServiceImpl implements AdsCommentService, AuthenticationFacade {

    private final ResponseWrapperAdsCommentMapper responseWrapperAdsCommentMapper;
    private final AdsCommentMapper adsCommentMapper;
    private final AdsCommentRepository adsCommentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public AdsCommentDto addAdsComment(Integer adId, AdsCommentDto comment) {
        if (comment.getText() == null) {
            throw new IllegalArgumentException("Text of comment is empty. ");
        }
        AdsComment newComment = adsCommentMapper.toEntity(comment);
        newComment.setId(null);
        newComment.setUserId(userRepository.findFirstByEmail(getLogin()).orElseThrow(() -> new ru.got.shop.exception.UserNotFoundException(getLogin())));
        newComment.setCreatedAt(OffsetDateTime.now());
        newComment.setAdsId(adsRepository.findById(adId)
                .orElseThrow(() -> new ru.got.shop.exception.AdsNotFoundException(adId)));
        return adsCommentMapper.toDto(adsCommentRepository.save(newComment));
    }

    @Override
    public AdsCommentDto deleteAdsComment(Integer adId, Integer id) {
        AdsCommentDto commentToDelete = getAdsComment(adId, id);
        testUserHasAccessToEditComment(commentToDelete);
        adsCommentRepository.deleteById(id);
        return commentToDelete;
    }

    @Override
    public AdsCommentDto getAdsComment(Integer adId, Integer id) {
        testAdsHasComment(adId, id);
        Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(() -> new AdsNotFoundException(adId));
        List<AdsComment> allAdsCommentsByAdId = adsByAdId.getAdsComment();
        AdsComment neededCommentWithId = allAdsCommentsByAdId.stream()
                .filter(adsCom -> (adsCom.getId().equals(id)))
                .findFirst()
                .orElseThrow(() -> new AdsCommentNotFoundException(id));
        return adsCommentMapper.toDto(neededCommentWithId);
    }

    @Override
    public ResponseWrapperAdsCommentDto getAdsComments(Integer adId) {
        Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(() -> new AdsNotFoundException(adId));
        List<AdsComment> allAdsCommentsByAdId = adsByAdId.getAdsComment();
        return responseWrapperAdsCommentMapper.toDto(allAdsCommentsByAdId.size(),
                adsCommentMapper.toDtos(allAdsCommentsByAdId));
    }

    @Override
    public AdsCommentDto updateAdsComment(Integer adId, Integer id, AdsCommentDto comment) {
        testAdsHasComment(adId, id);
        testUserHasAccessToEditComment(comment);
        AdsComment updateComment = adsCommentMapper.toEntity(comment);
        updateComment.setId(id);
        updateComment.setAdsId(adsRepository.findById(adId)
                .orElseThrow(() -> new AdsNotFoundException(adId)));
        updateComment = adsCommentRepository.save(updateComment);
        return adsCommentMapper.toDto(updateComment);
    }

    private boolean testAdsHasComment(Integer adId, Integer id) {
        Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(() -> new AdsNotFoundException(adId));
        AdsComment commentById = adsCommentRepository.findById(id)
                .orElseThrow(() -> new AdsCommentNotFoundException(id));
        if (!adsByAdId.getAdsComment().contains(commentById)) {
            throw new AdsWithCommentNotFoundException(adId, id);
        }
        return true;
    }

    private boolean testUserHasAccessToEditComment(AdsCommentDto comment) {
        if (!Role.ROLE_ADMIN.equals((userRepository.findFirstByEmail(getLogin()))
                .orElseThrow(() -> new UserNotFoundException(getLogin())).getRoleGroup())
                &&
                (!Objects.equals(userService.findUser(getLogin()).getId(), comment.getAuthor()))) {
            throw new ForbiddenException("Access id denied, someone else's comment. ");
        }
        return true;
    }
}
