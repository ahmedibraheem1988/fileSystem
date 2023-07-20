package com.demo.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.app.entity.PermissionGroup;


public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
    
}