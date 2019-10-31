package com.luke.filmdb.application.repository;


import com.luke.filmdb.application.model.dictionaries.GenresDictionary;
import com.luke.filmdb.commons.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Luke on 01.11.2017.
 */
@Cacheable(CacheConstants.GENRES)
public interface GenresRepo extends JpaRepository<GenresDictionary, Long> {
}
