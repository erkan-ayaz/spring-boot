package com.piateam.ac.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.piateam.ac.bean.TaskDTO;
import com.piateam.ac.component.TaskManager;

@Controller
public class TaskController {
	private final HttpServletRequest	request;
	@Autowired
	private TaskManager					taskManager;

	@Autowired
	public TaskController(HttpServletRequest request) {
		this.request = request;
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/tasks")
	public String getTasks(Model model) {
		List<TaskDTO> tasks = taskManager.getTasks();
		model.addAttribute("tasks", tasks);
		model.addAttribute("", keycloakSecurityContext());
		return "tasks";
	}

	private KeycloakSecurityContext keycloakSecurityContext() {
		return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
	}
}
