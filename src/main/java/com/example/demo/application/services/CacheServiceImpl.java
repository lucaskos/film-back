package com.example.demo.application.services;

import com.example.demo.commons.CacheConstants;
import com.example.demo.application.model.dictionaries.GenresDictionary;
import com.example.demo.application.model.dictionaries.PersonRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

//@Service
//@Qualifier("cacheServiceImpl")
//@AllArgsConstructor
public class CacheServiceImpl {

    private CacheManager cacheManager;

    @PersistenceContext
    private EntityManager entityManager;

    //    @PostConstruct
    public void init() {
        getGenres();
        getRoles();
    }

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
