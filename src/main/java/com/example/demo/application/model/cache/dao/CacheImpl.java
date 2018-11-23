package com.example.demo.application.model.cache.dao;

import com.example.demo.commons.CacheConstants;
import com.example.demo.application.model.cache.dictionaries.GenresDictionary;
import com.example.demo.application.model.cache.dictionaries.PersonRole;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

//@Service
//@Qualifier("cache")
public class CacheImpl {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
    @Cacheable(CacheConstants.ROLES)
    public List<PersonRole> getRoles() {
        Query query = entityManager.createQuery("from PersonRole");
        List resultList = query.getResultList();
        return resultList;
    }

//    @Override
    @Cacheable(CacheConstants.GENRES)
    public List<GenresDictionary> getGenres() {
        Query query = entityManager.createQuery("from GenresDictionary");
        List resultList = query.getResultList();
        return resultList;
    }
}
