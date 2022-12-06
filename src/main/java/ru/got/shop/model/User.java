package ru.got.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.got.shop.openapi.dto.RegReq;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users_account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @ToString.Exclude
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RegReq.RoleEnum roleGroup;

    @Column(name = "avatar_id")
    private Long avatarId;
    //todo create entity avatar
}
