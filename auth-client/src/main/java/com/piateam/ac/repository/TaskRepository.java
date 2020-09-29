package com.piateam.ac.repository;

import org.springframework.data.repository.CrudRepository;

import com.piateam.ac.bean.Task;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
