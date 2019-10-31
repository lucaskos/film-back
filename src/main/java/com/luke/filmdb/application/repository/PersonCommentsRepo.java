package com.luke.filmdb.application.repository;

import com.luke.filmdb.application.model.comments.PersonComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonCommentsRepo extends JpaRepository<PersonComment, Long> {
}
