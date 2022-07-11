package com.saveme.go.repository;

import com.saveme.go.entity.Link;

public interface LinkRepository {

    Link getShortByOriginal(String link);

    Link getOriginalByShort(String link);

    Link save(Link link);
}
