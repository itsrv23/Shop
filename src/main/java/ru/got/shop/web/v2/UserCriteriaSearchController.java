package ru.got.shop.web.v2;

import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.mapper.UserMapper;
import ru.got.shop.model.User;
import ru.got.shop.model.User_;
import ru.got.shop.model.dto.UserDto;
import ru.got.shop.repository.UserRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/v2/users")
@RestController
@RequiredArgsConstructor
public class UserCriteriaSearchController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Sample Criteria search
     */
    @PreAuthorize("hasAuthority('users.read:write')")
    @PostMapping("/filter")
    public List<UserDto> getUsers(@RequestBody UserDto userDto,@ParameterObject Pageable pageable) {
        List<User> users = userRepository.findAll(
                (root, query, cb) -> {
                    Predicate conjunction = cb.conjunction();
                    // Если есть id, ищем только по нему
                    if (Objects.nonNull(userDto.getId())) {
                        return cb.and(conjunction, cb.equal(root.get(User_.ID), userDto.getId()));
                    }

                    if (Objects.nonNull(userDto.getFirstName())) {
                        Predicate like = cb.like(cb.upper(root.get(User_.firstName)), "%" + userDto.getFirstName().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    if (Objects.nonNull(userDto.getLastName())) {
                        Predicate like = cb.like(cb.upper(root.get(User_.lastName)), "%" + userDto.getLastName().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    if (Objects.nonNull(userDto.getPhone())) {
                        Predicate like = cb.like(cb.upper(root.get(User_.phone)), "%" + userDto.getPhone().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    if (Objects.nonNull(userDto.getEmail())) {
                        Predicate like = cb.like(cb.upper(root.get(User_.EMAIL)), "%" + userDto.getEmail().toUpperCase() + "%");
                        conjunction = cb.and(conjunction, like);
                    }
                    query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
                    return conjunction;
                }
                , pageable).getContent();
        return userMapper.toDtos(users);
    }
}
