package com.abc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    Repository repository;

    @Test
    public void create() throws IOException {
        for(int i=0;i<100000;i++)
            repository.save(new Entity("name"+i, "desc"+i, Json.fromJson("{\"attrib\":\"a"+i+"\", \"value\":\"val"+i+"\"}")));
    }

    @Test
    public void testMe() {

        for(Entity e : repository.findAll(new PageRequest(0, 20))){
            System.out.println(e);
        }
    }

    @Test
    public void testByName() {

//        Page<Entity> entities = repository.findAll(new PageRequest(1, 20));
        for(Entity e : repository.findByName("name100", new PageRequest(0, 20))){
            System.out.println(e);
        }
    }

    @Test
    public void testJson(){

        for(Entity e : repository.findByAttrib("a100")){
            System.out.println(e);
        }
    }
}