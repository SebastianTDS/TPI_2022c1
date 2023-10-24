package com.fantasticos.studentservice.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasticos.studentservice.dto.HistDto;
import com.fantasticos.studentservice.exception.GroupNotFoundException;
import com.fantasticos.studentservice.exception.NotFounStudentException;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.HistGroup;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.GroupRepository;
import com.fantasticos.studentservice.repository.HistGroupRepository;
import com.fantasticos.studentservice.repository.StudentRepository;
import com.fantasticos.studentservice.service.HistService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistServiceImpl implements HistService {
	
	private final StudentRepository studentRepository;
	private final GroupRepository groupRepository;
	private final HistGroupRepository repository;

	@Override
	public List<HistGroup> findByUser(String idUser) {
		Student student = studentRepository.getById(idUser);
		
		if(student == null) throw new NotFounStudentException(idUser);
		
		return repository.findHistGroupByIdStudent(idUser);
	}

	@Override
	public HistGroup createHistory(HistDto newHist) {
		GroupEntity group = groupRepository.findById(newHist.getId()).orElseThrow(GroupNotFoundException::new);
		
		HistGroup newHistory = new HistGroup();
		System.out.println(group.getStudents());
		
		newHistory.setClosed(new Date());
		newHistory.setCreated(group.getCreated());
		newHistory.setId(group.getId());
		newHistory.setName(group.getName());
		newHistory.setSubject(group.getSubject());
		newHistory.setStudents(group.getStudents());
		
		return repository.save(newHistory);
	}

}
