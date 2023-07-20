package com.demo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.app.entity.File;
import com.demo.app.entity.Item;


public interface FileRepository extends JpaRepository<File, Long> {
	 @Transactional
	File findByItem(Item item);
    
}