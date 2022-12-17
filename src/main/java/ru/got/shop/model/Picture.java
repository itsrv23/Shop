package ru.got.shop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ads_pictures")
@Setter
@Getter
@RequiredArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "content_size")
    private long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "file_bytes")
    @Lob
    private byte[] data;
    @Column(name = "file_name")
    private String fileName;
}
