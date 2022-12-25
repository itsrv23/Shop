package ru.got.shop.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ads_pictures")
@Setter
@Getter
@ToString
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
    @Column(name = "file_path")
    private String filePath;
}
