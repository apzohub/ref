package com.abc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class EntityRestService {
    Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    EntityService es;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create(){
        logger.info("create");
        // save a single Entity
        es.create();
        return "Done";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Entity read(@RequestParam("id") String id){
        return es.read(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Entity> readAll(){
        return es.findAll();
    }


}
