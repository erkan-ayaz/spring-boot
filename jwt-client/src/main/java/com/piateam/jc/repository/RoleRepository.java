package com.piateam.jc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piateam.jc.bean.entity.Role;
import com.piateam.jc.bean.entity.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(RoleEnum name);
}
