package ru.got.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer pk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "title")
    private String title;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adsId")
    @ToString.Exclude
    private List<AdsComment> adsComment;
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
//    private List<Picture> pictures;
}
