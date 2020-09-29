package com.piateam.ac.component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.piateam.ac.bean.Task;
import com.piateam.ac.bean.TaskDTO;
import com.piateam.ac.repository.TaskRepository;

@Component
public class TaskManager {
	@Autowired
	private TaskRepository taskRepository;

	public List<TaskDTO> getTasks() {
		List<Task> tasks = (List<Task>) taskRepository.findAll();
		return tasks.stream().map(TaskDTO::new).collect(Collectors.toList());
	}
}
