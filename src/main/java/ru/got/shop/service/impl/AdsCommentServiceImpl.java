package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.mapper.AdsCommentMapper;
import ru.got.shop.mapper.ResponseWrapperAdsCommentMapper;
import ru.got.shop.openapi.dto.AdsComment;
import ru.got.shop.openapi.dto.ResponseWrapperAdsComment;
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
    public AdsComment addAdsComments(String adPk, AdsComment comment) {
        ru.got.shop.model.AdsComment newComment = adsCommentMapper.toEntity(comment);
        newComment.setAdsId(adsRepository.findById(Integer.parseInt(adPk))
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist")));
        log.info("adding successfull " + newComment);
        return adsCommentMapper.toDto(adsCommentRepository.save(newComment));
    }

    @Override
    public void deleteAdsComment(String adPk, Integer id) {
        if (! adsContainsAdsComment(adPk, id)) {
            throw new EntityNotFoundException("Ads does not contain adsComment or does not exist");
        }
        adsCommentRepository.deleteById(id);
        log.info("remowal successfull with adsId " + adPk + " and id " + id);
    }

    @Override
    public AdsComment getAdsComment(String adPk, Integer id) {
        if (! adsContainsAdsComment(adPk, id)) {
            throw new EntityNotFoundException("Ads does not contain adsComment or does not exist");
        }

        ru.got.shop.model.Ads ads = adsRepository
                .findById(Integer.parseInt(adPk))
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
        List<ru.got.shop.model.AdsComment> adsComments = ads.getAdsComment();

        ru.got.shop.model.AdsComment foundComment = adsComments
                .stream()
                .filter(adsCom -> (adsCom.getPk().equals(id)))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("AdsCommentNotFound or does not exist"));

        AdsComment adsComment = adsCommentMapper.toDto(foundComment);

        log.info("getting successfull " + adsComment.toString());
        return adsComment;
    }

    @Override
    public ResponseWrapperAdsComment getAdsComments(String adPk) {
        ru.got.shop.model.Ads ads = adsRepository.findById(Integer.parseInt(adPk))
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
        List<ru.got.shop.model.AdsComment> adsComments = ads.getAdsComment();
        ResponseWrapperAdsComment responseWrapperAdsComment = responseWrapperAdsCommentMapper.toDto(adsComments.size(),
                adsCommentMapper.toDtos(adsComments));

        log.info("getting successfull " + responseWrapperAdsComment.toString());
        return responseWrapperAdsComment;
    }

    @Override
    public AdsComment updateAdsComment(String adPk, Integer id, AdsComment comment) {
        if (! adsContainsAdsComment(adPk, id)) {
            throw new EntityNotFoundException("Ads does not contain adsComment or does not exist");
        }

        ru.got.shop.model.AdsComment updateComment = adsCommentMapper.toEntity(comment);
        updateComment.setPk(id);
        updateComment.setAdsId(adsRepository.findById(Integer.parseInt(adPk))
                .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist")));
        updateComment = adsCommentRepository.save(updateComment);

        log.info("updating successfull " + updateComment);
        return adsCommentMapper.toDto(updateComment);
    }

    private boolean adsContainsAdsComment (String adPk, Integer id) {
        try {
            ru.got.shop.model.Ads ads = adsRepository.findById(Integer.parseInt(adPk))
                    .orElseThrow(()-> new EntityNotFoundException("AdsNotFound or does not exist"));
            ru.got.shop.model.AdsComment comment = adsCommentRepository.findById(id)
                     .orElseThrow(()-> new EntityNotFoundException("AdsCommentNotFound or does not exist"));
            return ads.getAdsComment().contains(comment);
        } catch (EntityNotFoundException e) { return false; }
    }
}
