package com.saveme.go.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "link")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    @Id
    @GeneratedValue
    private UUID uuid;
    @Column(name = "original_link")
    private String originalLink;
    @Column(name = "short_link")
    private String shortLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return Objects.equals(getOriginalLink(), link.getOriginalLink())
                && Objects.equals(getShortLink(), link.getShortLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOriginalLink(), getShortLink());
    }
}
