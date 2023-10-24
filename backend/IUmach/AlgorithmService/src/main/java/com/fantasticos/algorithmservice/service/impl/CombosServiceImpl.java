package com.fantasticos.algorithmservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Departamento;
import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.repository.CarreraRepository;
import com.fantasticos.algorithmservice.repository.DepartamentoRepository;
import com.fantasticos.algorithmservice.repository.FacultadRepository;
import com.fantasticos.algorithmservice.repository.MateriaRepository;
import com.fantasticos.algorithmservice.repository.TagRepository;
import com.fantasticos.algorithmservice.service.CombosService;

@Service
public class CombosServiceImpl implements CombosService {

	@Autowired private FacultadRepository repoFacu;
	@Autowired private DepartamentoRepository repoDepto;
	@Autowired private CarreraRepository repoCarrera;
	@Autowired private MateriaRepository repoMateria;
	@Autowired private TagRepository repoTags;
	
	@Override
	public List<Facultad> getFacultades() {
		return repoFacu.findAll();
	}

	@Override
	public List<Departamento> getDepartamentos(String facultad) {
		return repoDepto.findByFacultad(facultad);
	}

	@Override
	public List<Carrera> getCarreras(String facultad, Long departamento) {
		return repoCarrera.getCarrerasPorDepartamento(departamento, facultad);
	}

	@Override
	public List<Materia> getMaterias(String facultad, Long carrera) {
		return repoMateria.getMateriasPorCarrera(carrera, facultad);
	}

	@Override
	public List<Tag> getTags() {
		return repoTags.findAll();
	}

	@Override
	public List<String> getTurnosDisp(String facultad, Integer materia) {
		return repoMateria.findTurnos(facultad, materia);
	}

}
