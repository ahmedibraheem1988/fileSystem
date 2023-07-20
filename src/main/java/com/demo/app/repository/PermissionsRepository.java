package com.demo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.app.entity.PermissionGroup;
import com.demo.app.entity.Permissions;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
	@Query("SELECT u FROM Permissions u WHERE u.email = :email and u.group = :group")
	List<Permissions> getP(@Param("email") String email,@Param("group")PermissionGroup group);
}