package com.example.demo.application.model.cache.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class DictionaryAbstractClass<T> implements CacheDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> dictionaryClazz;

    public DictionaryAbstractClass(Class<T> clazz) {
        this.dictionaryClazz = clazz;
    }

    @Override
    public List<T> getAll() {
        List resultList = entityManager.createQuery("from " + dictionaryClazz.getName()).getResultList();
        return resultList;
    }
}
