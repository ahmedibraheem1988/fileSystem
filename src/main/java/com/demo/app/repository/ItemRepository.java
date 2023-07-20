package com.demo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.app.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {
    
}