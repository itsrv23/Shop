package ru.got.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.got.shop.security.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users_account")
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
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
    private Role roleGroup;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private UserAvatar avatarId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    @ToString.Exclude
    private List<Ads> ads;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    @ToString.Exclude
    private List<AdsComment> adsComment;
}
