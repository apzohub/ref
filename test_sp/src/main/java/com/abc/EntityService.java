package com.abc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
//@Validated
@PropertySource("classpath:test.properties")
public class EntityService {

    @Value("${name}")
    private String name;

    @Autowired
    Repository repository;


    public String create(){
        // save a single Entity
        repository.save(new Entity("Jack", "Smith"));

        // save a list of AbstractEntitys
        repository.save(Arrays.asList(new Entity("Adam", "Johnson"), new Entity("Kim", "Smith"),
                new Entity("David", "Williams"), new Entity("Peter", "Davis")));

        return "Done";
    }

    public Entity read(String id){
        return repository.findOne(id);
    }

    public List<Entity> findAll(){
        List<Entity> result = new ArrayList<>();

        for(Entity e : repository.findAll()){
            result.add(e);
        }

        return result;
    }

    public List<Entity> findByName(String name){
        List<Entity> ret = new ArrayList<>();
        for(Entity e : repository.findByName(name)){
            ret.add(e);
        }
        return ret;
    }
}
