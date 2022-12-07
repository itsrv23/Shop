package ru.got.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
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

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private Integer price;

    @Column(name = "title")
    private String title;


}
