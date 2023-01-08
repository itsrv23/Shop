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
import ru.got.shop.exception.IllegalPriceValueException;
import ru.got.shop.mapper.AdMapper;
import ru.got.shop.model.Ad;
import ru.got.shop.model.Ad_;
import ru.got.shop.repository.AdRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/v2/ads")
@RestController
@RequiredArgsConstructor
public class AdsCriteriaSearchController {

    private final AdRepository adRepository;

    private final AdMapper adMapper;

    /**
     * Ads Criteria search
     */
    @PostMapping("/filter")
    public List<AdDto> getAdsByCriteria(@RequestBody AdCriteriaDto adCriteriaDto, Pageable pageable) {
        if (adCriteriaDto.getMinPrice()>adCriteriaDto.getMaxPrice()) {
            throw new IllegalPriceValueException("Entered min value should be less than max value. ");
        }

        List<Ad> adByFilter = adRepository.findAll(
                (root, query, cb) -> {
                    Predicate conjunction = cb.conjunction();

                    if (Objects.nonNull(adCriteriaDto.getTitle())) {
                        Predicate like = cb.like(cb.upper(root.get(Ad_.title)), "%" + adCriteriaDto.getTitle().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    if (Objects.nonNull(adCriteriaDto.getDescription())) {
                        Predicate like = cb.like(cb.upper(root.get(Ad_.description)), "%" + adCriteriaDto.getDescription().toUpperCase() + "%");
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
                }
                , pageable).getContent();

        return adMapper.toDtos(adByFilter);
    }
}
