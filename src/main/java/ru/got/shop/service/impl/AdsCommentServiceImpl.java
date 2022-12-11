package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.mapper.AdsCommentMapper;
import ru.got.shop.mapper.ResponseWrapperAdsCommentMapper;
import ru.got.shop.model.dto.AdsCommentDto;
import ru.got.shop.model.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.repository.AdsCommentRepository;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.service.AdsCommentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsCommentServiceImpl implements AdsCommentService {

    private final ResponseWrapperAdsCommentMapper responseWrapperAdsCommentMapper;
    private final AdsCommentMapper adsCommentMapper;
    private final AdsCommentRepository adsCommentRepository;
    private final AdsRepository adsRepository;

    @Override
    public AdsCommentDto addAdsComments(Integer adId, AdsCommentDto comment) {
        ru.got.shop.model.AdsComment newComment = adsCommentMapper.toEntity(comment);
        newComment.setAdsId(adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist")));
        AdsCommentDto addedComment =  adsCommentMapper.toDto(adsCommentRepository.save(newComment));

        log.info("adding successfull " + addedComment);
        return addedComment;
    }

    @Override
    public AdsCommentDto deleteAdsComment(Integer adId, Integer id) {
        if (! adsContainsAdsComment(adId, id)) {
            throw new EntityNotFoundException("Ads does not contain adsComment or does not exist");
        }

        AdsCommentDto deletedComment = adsCommentMapper.toDto(adsCommentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("AdsCommentNotFound or does not exist")));
        adsCommentRepository.deleteById(id);

        log.info("remowal successfull " + deletedComment);
        return deletedComment;
    }

    @Override
    public AdsCommentDto getAdsComment(Integer adId, Integer id) {
        if (! adsContainsAdsComment(adId, id)) {
            throw new EntityNotFoundException("Ads does not contain adsComment or does not exist");
        }

        ru.got.shop.model.Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
        List<ru.got.shop.model.AdsComment> allAdsCommentsByAdId = adsByAdId.getAdsComment();
        ru.got.shop.model.AdsComment neededCommentWithId = allAdsCommentsByAdId
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
        ru.got.shop.model.Ads adsByAdId = adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
        List<ru.got.shop.model.AdsComment> allAdsCommentsByAdId = adsByAdId.getAdsComment();
        ResponseWrapperAdsCommentDto foundComments = responseWrapperAdsCommentMapper.toDto(allAdsCommentsByAdId.size(),
                adsCommentMapper.toDtos(allAdsCommentsByAdId));

        log.info("getting successfull " + foundComments);
        return foundComments;
    }

    @Override
    public AdsCommentDto updateAdsComment(Integer adId, Integer id, AdsCommentDto comment) {
        if (! adsContainsAdsComment(adId, id)) {
            throw new EntityNotFoundException("Ads does not contain adsComment or does not exist");
        }

        ru.got.shop.model.AdsComment updateComment = adsCommentMapper.toEntity(comment);
        updateComment.setPk(id);
        updateComment.setAdsId(adsRepository.findById(adId)
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist")));
        updateComment = adsCommentRepository.save(updateComment);
        AdsCommentDto updatedComment = adsCommentMapper.toDto(updateComment);

        log.info("updating successfull " + updatedComment);
        return updatedComment;
    }

    private boolean adsContainsAdsComment (Integer adId, Integer id) {
        try {
            ru.got.shop.model.Ads adsByAdId = adsRepository.findById(adId)
                    .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
            ru.got.shop.model.AdsComment commentById = adsCommentRepository.findById(id)
                     .orElseThrow(()-> new EntityNotFoundException("AdsCommentNotFound or does not exist"));
            return adsByAdId.getAdsComment().contains(commentById);
        } catch (EntityNotFoundException e) { return false; }
    }
}
