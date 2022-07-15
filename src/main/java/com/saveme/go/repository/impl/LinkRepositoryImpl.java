package com.saveme.go.repository.impl;

import com.saveme.go.entity.Link;
import com.saveme.go.repository.LinkRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Singleton
public class LinkRepositoryImpl implements LinkRepository {
    private final EntityManager entityManager;

    public LinkRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @ReadOnly
    public Link getShortByOriginal(String link) {
        String qlString = "SELECT l FROM com.saveme.go.entity.Link as l WHERE l.originalLink = '" + link + "'";
        return getLink(qlString);
    }

    @Override
    @ReadOnly
    public Link getOriginalByShort(String link) {
        String qlString = "SELECT l FROM com.saveme.go.entity.Link as l WHERE l.shortLink = '" + link + "'";
        return getLink(qlString);
    }

    @Override
    @Transactional
    public Link save(Link link) {
        entityManager.merge(link);
        return link;
    }

    private Link getLink(String qlString) {
        TypedQuery<Link> query = entityManager.createQuery(qlString, Link.class);
        var result = query.getResultList();
        return !result.isEmpty() ? result.get(0) : null;
    }
}
