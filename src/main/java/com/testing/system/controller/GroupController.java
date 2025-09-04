package com.testing.system.controller;

import com.testing.system.model.CandidateGroup;
import com.testing.system.service.CandidateGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/instructor/groups")
public class GroupController {
	
	private final CandidateGroupService groupService;
	
	public GroupController(CandidateGroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping
	public String listGroups(Model model) {
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("newGroup", new CandidateGroup());
		return "instructor/group_list"; // Новая страница
	}
	
	@PostMapping("/create")
	public String createGroup(@ModelAttribute CandidateGroup newGroup) {
		groupService.save(newGroup);
		return "redirect:/instructor/groups";
	}
}
