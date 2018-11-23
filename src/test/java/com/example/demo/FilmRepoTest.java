package com.example.demo;

import com.example.demo.application.model.Film;
import com.example.demo.application.repository.FilmRepo;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@SpringBootTest
public class FilmRepoTest {

    @Autowired
    private FilmRepo filmRepo;

//    @Test
    public void check() {
        List<Film> all = filmRepo.findAll();

        Assert.assertNotNull(all.get(0));
    }

}
