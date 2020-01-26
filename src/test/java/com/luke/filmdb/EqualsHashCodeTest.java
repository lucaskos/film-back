package com.luke.filmdb;

import com.luke.filmdb.application.model.generic.DataModelObject;
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
    public void filmEualHashCodeTest() {

    }

}