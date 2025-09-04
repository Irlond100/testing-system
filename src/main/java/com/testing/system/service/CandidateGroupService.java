package com.testing.system.service;

import com.testing.system.model.CandidateGroup;
import com.testing.system.repository.CandidateGroupRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateGroupService {
	private final CandidateGroupRepository groupRepository;
	
	public CandidateGroupService(CandidateGroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}
	
	public List<CandidateGroup> findAll() {
		return groupRepository.findAll();
	}
	
	public CandidateGroup save(CandidateGroup group) {
		return groupRepository.save(group);
	}
}
