package com.example.demo;

import com.example.demo.model.Film;
import com.example.demo.repository.FilmRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class FilmRepoTest {

    @Autowired
    private FilmRepo filmRepo;

    @Test
    public void check() {
        List<Film> all = filmRepo.findAll();

        Assert.assertNotNull(all.get(0));
    }

}
