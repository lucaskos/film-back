package com.luke.filmdb;

import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.FilmRelations;
import com.luke.filmdb.application.model.comments.FilmComment;
import com.luke.filmdb.application.model.generic.DataModelObject;
import com.luke.filmdb.application.model.user.Role;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.commons.MapperCommons;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EqualsHashCodeTest extends MapperCommons {


    @Test
    public void dataObjectEqualHashCodeTest() {
        EqualsVerifier.forClass(DataModelObject.class)
                .usingGetClass()
                .verify();
    }

    @Test
    public void filmEuqalHashCodeTest() {
        EqualsVerifier.forClass(Film.class)
                .withRedefinedSuperclass()
                .usingGetClass()
                .withIgnoredFields("id")
                .withPrefabValues(FilmRelations.class, new FilmRelations(1L), new FilmRelations(2L))
                .withPrefabValues(FilmComment.class, new FilmComment(Film.getFilmWithTitleYear("TEST", 0)),
                        new FilmComment(Film.getFilmWithTitleYear("TEST1", 1)))
                .verify();

    }

    @Test
    public void userEqualHashCode() {
        EqualsVerifier.forClass(User.class)
                .withRedefinedSuperclass()
                .usingGetClass()
//                .withIgnoredFields("id")
                .withIgnoredFields("password")
                .withPrefabValues(Role.class, Role.getRoleWithNameAndId(1L, "TEST1"), Role.getRoleWithNameAndId(2L, "TEST2"))
                .verify();
    }

}