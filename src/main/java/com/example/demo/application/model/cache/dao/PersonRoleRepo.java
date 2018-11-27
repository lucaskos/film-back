package com.example.demo.application.model.cache.dao;

import com.example.demo.application.model.cache.dictionaries.PersonRole;
import com.example.demo.commons.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@Cacheable(CacheConstants.ROLES)
public interface PersonRoleRepo extends JpaRepository<PersonRole, Long> {
}
