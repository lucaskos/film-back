package com.example.demo.model.cache.dao;

import com.example.demo.commons.CacheConstants;
import com.example.demo.commons.QualifierConstants;
import com.example.demo.model.cache.dictionaries.PersonRole;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Luke on 01.11.2017.
 */
@Repository(QualifierConstants.PERSON_DIC_DAO)
public class PersonRoleDaoImpl extends DictionaryAbstractClass<PersonRole> {

    public PersonRoleDaoImpl() {
        super(PersonRole.class);
    }

    @Override
    @Cacheable(CacheConstants.ROLES)
    public List<PersonRole> getAll() {
        return super.getAll();
    }

}
