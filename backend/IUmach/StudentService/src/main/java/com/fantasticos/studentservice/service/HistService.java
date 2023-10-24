package com.fantasticos.studentservice.service;

import java.util.List;

import com.fantasticos.studentservice.dto.HistDto;
import com.fantasticos.studentservice.model.HistGroup;

public interface HistService {

	List<HistGroup> findByUser(String idUser);

	HistGroup createHistory(HistDto newHist);

}
