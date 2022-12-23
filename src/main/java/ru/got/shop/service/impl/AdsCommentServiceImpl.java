package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.mapper.AdsCommentMapper;
import ru.got.shop.mapper.ResponseWrapperAdsCommentMapper;
import ru.got.shop.mapper.UserMapper;
import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.repository.AdsCommentRepository;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.Role;
import ru.got.shop.service.AdsCommentService;
import ru.got.shop.model.AdsComment;
import ru.got.shop.model.Ads;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsCommentServiceImpl implements AdsCommentService, AuthenticationFacade {

    private final ResponseWrapperAdsCommentMapper responseWrapperAdsCommentMapper;
    private final AdsCommentMapper adsCommentMapper;
    private final AdsCommentRepository adsCommentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public AdsCommentDto addAdsComment(Integer adId, AdsCommentDto comment) {
        if (comment.getText() == null) {
            throw new EntityNotFoundException("CommentNotFound or does not exist");
        }

        AdsComment newComment = adsCommentMapper.toEntity(comment);
        newComment.setPk(null);
        newComment.setUserId(userMapper.toEntity(userService.findUser(getLogin())));
        newComment.setCreatedAt(OffsetDateTime.now());
        newComment.setAdsId(adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist")));
        AdsCommentDto addedComment =  adsCommentMapper.toDto(adsCommentRepository.save(newComment));

        log.info("adding successfull " + addedComment);
        return addedComment;
    }

    @Override
    public AdsCommentDto deleteAdsComment(Integer adId, Integer id) {
        AdsCommentDto commentToDelete = getAdsComment(adId, id);
        testUserHasAccessToEditComment(commentToDelete);
        adsCommentRepository.deleteById(id);

        log.info("remowal successfull " + commentToDelete);
        return commentToDelete;
    }

    @Override
    public AdsCommentDto getAdsComment(Integer adId, Integer id) {
        testAdsHasComment(adId, id);

        Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
        List<AdsComment> allAdsCommentsByAdId = adsByAdId.getAdsComment();
        AdsComment neededCommentWithId = allAdsCommentsByAdId
                .stream()
                .filter(adsCom -> (adsCom.getPk().equals(id)))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("AdsCommentNotFound or does not exist"));
        AdsCommentDto foundComment = adsCommentMapper.toDto(neededCommentWithId);

        log.info("getting successfull " + foundComment);
        return foundComment;
    }

    @Override
    public ResponseWrapperAdsCommentDto getAdsComments(Integer adId) {
        Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
        List<AdsComment> allAdsCommentsByAdId = adsByAdId.getAdsComment();
        ResponseWrapperAdsCommentDto foundComments = responseWrapperAdsCommentMapper.toDto(allAdsCommentsByAdId.size(),
                adsCommentMapper.toDtos(allAdsCommentsByAdId));

        log.info("getting successfull " + foundComments);
        return foundComments;
    }

    @Override
    public AdsCommentDto updateAdsComment(Integer adId, Integer id, AdsCommentDto comment) {
        testAdsHasComment(adId, id);
        testUserHasAccessToEditComment(comment);

        AdsComment updateComment = adsCommentMapper.toEntity(comment);
        updateComment.setPk(id);
        updateComment.setAdsId(adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist")));
        updateComment = adsCommentRepository.save(updateComment);
        AdsCommentDto updatedComment = adsCommentMapper.toDto(updateComment);

        log.info("updating successfull " + updatedComment);
        return updatedComment;
    }

    private boolean testAdsHasComment (Integer adId, Integer id) {
        Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("AdsNotFound or does not exist"));
        AdsComment commentById = adsCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AdsCommentNotFound or does not exist"));
        if (!adsByAdId.getAdsComment().contains(commentById)) {
            throw new EntityNotFoundException("Ads does not contain adsComment");
        }
        return true;
    }

    private boolean testUserHasAccessToEditComment (AdsCommentDto comment) {
        if (!Role.ADMIN.equals(
                (userRepository
                        .findFirstByEmail(getLogin())
                        .orElseThrow(EntityNotFoundException::new))
                        .getRoleGroup())
                &&
                (!Objects.equals(userService.findUser(getLogin()).getId(), comment.getAuthor()))) {
            throw new ForbiddenException("Access id denied, someone else's comment");
        }
        return true;
    }
}
