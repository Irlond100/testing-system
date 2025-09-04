package com.testing.system.repository;

import com.testing.system.model.CandidateGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateGroupRepository extends JpaRepository<CandidateGroup, Long> {}