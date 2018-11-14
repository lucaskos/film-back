package com.example.demo.model.cache.service;

import com.example.demo.commons.CacheConstants;
import com.example.demo.model.cache.dictionaries.GenresDictionary;
import com.example.demo.model.cache.dictionaries.PersonRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

    private final CacheManager cacheManager;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public CacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PostConstruct
    public void init() {
        getGenres();
        getRoles();
    }

    @Override
    @Cacheable(CacheConstants.ROLES)
    public List<PersonRole> getRoles() {
        org.springframework.cache.Cache cache = cacheManager.getCache(CacheConstants.GENRES);
        List resultList = new ArrayList();
        if (cache.get(CacheConstants.ROLES) == null) {
            Query query = entityManager.createQuery("from PersonRole");
            resultList = query.getResultList();
        }
        return resultList;
    }

    @Override
    @Cacheable(CacheConstants.GENRES)
    public List<GenresDictionary> getGenres() {
        org.springframework.cache.Cache cache = cacheManager.getCache(CacheConstants.ROLES);
        List resultList = new ArrayList();
        if (cache.get(CacheConstants.GENRES) == null) {
            Query query = entityManager.createQuery("from GenresDictionary");
            resultList = query.getResultList();
        }
        return resultList;
    }


}
