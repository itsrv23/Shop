package ru.got.shop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ads_pictures")
@Setter
@Getter
public class Picture implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    private Ads id;
    @Column(name = "file_size")
    private Integer fileSize;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "media_type")
    private String mediaType;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Picture picture = (Picture) o;
        return id != null && Objects.equals(id, picture.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
