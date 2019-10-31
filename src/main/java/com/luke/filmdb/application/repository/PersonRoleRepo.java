package com.luke.filmdb.application.repository;

import com.luke.filmdb.application.model.dictionaries.PersonRole;
import com.luke.filmdb.commons.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@Cacheable(CacheConstants.ROLES)
public interface PersonRoleRepo extends JpaRepository<PersonRole, Long> {
}
