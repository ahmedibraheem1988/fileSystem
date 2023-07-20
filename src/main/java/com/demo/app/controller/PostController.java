package com.demo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.demo.app.entity.Item;
import com.demo.app.repository.ItemRepository;

@Controller
public class PostController {

   @Autowired
	ItemRepository repo;

    @QueryMapping
    public Item recentPosts(@Argument Long id) {
        return repo.findById(id).orElse(null);
    }
    
   
    
}

