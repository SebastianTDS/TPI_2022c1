package com.fantasticos.algorithmservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fantasticos.algorithmservice.dto.EstudianteDTO;
import com.fantasticos.algorithmservice.model.Carrera;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.model.Facultad;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.repository.CarreraRepository;
import com.fantasticos.algorithmservice.repository.EstudianteRepository;
import com.fantasticos.algorithmservice.repository.FacultadRepository;
import com.fantasticos.algorithmservice.repository.MateriaRepository;
import com.fantasticos.algorithmservice.repository.TagRepository;
import com.fantasticos.algorithmservice.service.UserService;
import com.fantasticos.algorithmservice.util.exceptions.CarreraEnCursoException;
import com.fantasticos.algorithmservice.util.exceptions.CarreraInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.EstudianteInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.EstudiantePersistidoException;
import com.fantasticos.algorithmservice.util.exceptions.FacultadInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.InteresYaCargadoException;
import com.fantasticos.algorithmservice.util.exceptions.MateriaInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.MateriasEnCursoException;
import com.fantasticos.algorithmservice.util.exceptions.TagInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.TurnoException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired private FacultadRepository repoFacu;
	@Autowired private EstudianteRepository repoEst;
	@Autowired private CarreraRepository repoCarrera;
	@Autowired private MateriaRepository repoMateria;
	@Autowired private TagRepository repoTags;

	@Override
	public Estudiante verEstudiante(String idEstudiante) {
		return repoEst.findByParKey(idEstudiante).orElseThrow(EstudianteInexistenteException::new);
	}
	
	@Override
	public Estudiante cargarEstudiante(EstudianteDTO nuevoEstudiante) {
		Facultad buscada = findFacultadById(nuevoEstudiante);
		Estudiante nuevoNodo = nuevoEstudiante.build();
		
		nuevoNodo.setFacultad(buscada);

		try {
			return repoEst.save(nuevoNodo);
		} catch (Exception e) {
			throw new EstudiantePersistidoException();
		}

	}

	@Override
	public Estudiante anotarCarrera(EstudianteDTO buscado) {
		findFacultadById(buscado);		
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Carrera carreraBuscada = findCarreraByFacultad(buscado);
		
		if(estBuscado.getCarreras().contains(carreraBuscada))
			throw new CarreraEnCursoException();
		
		estBuscado.addCarrera(carreraBuscada);
		
		return repoEst.save(estBuscado); 
	}
	
	@Override
	public Estudiante abandonarCarrera(EstudianteDTO buscado) {
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Carrera carreraBuscada = findCarreraByAlumno(buscado);
		
		List<Materia> pendientes = repoMateria.materiasPend(buscado.getIdCarrera(), buscado.getId());
		
		pendientes.forEach(m -> {estBuscado.removeMateria(m);});
		estBuscado.removeCarrera(carreraBuscada);
		
		return repoEst.save(estBuscado);
	}
	
	@Override
	public Estudiante anotarMateria(EstudianteDTO buscado) {
		if (buscado.getTurno() == null)
			throw new TurnoException();
		
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Materia materiaBuscada = findMateriaByCarrera(buscado);
		
		if(!repoMateria.isTurnoDisponible(buscado.getIdMateria(), buscado.getTurno().getNodo()))
			throw new TurnoException("La materia no tiene disponibilidad en el turno");

		if (estBuscado.getCarreras().stream().filter(c -> c.getId() == buscado.getIdCarrera()).findAny().isEmpty()) 
			throw new CarreraInexistenteException();
		
		if (estBuscado.getMateriasDia().contains(materiaBuscada) ||
			estBuscado.getMateriasTarde().contains(materiaBuscada) ||
			estBuscado.getMateriasNoche().contains(materiaBuscada))
			throw new MateriasEnCursoException("Ya esta cursando la materia.");
		
		
		estBuscado.addMateria(materiaBuscada, buscado.getTurno());
			
		return repoEst.save(estBuscado);
	}

	@Override
	public Estudiante abandonarMateria(EstudianteDTO buscado) {
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Materia materia = repoMateria.findByCodigo(buscado.getIdMateria()).get();
		
		if(!repoEst.isCursandoMateria(buscado.getId(), buscado.getIdMateria()))
			throw new MateriaInexistenteException("El estudiante no esta cursando la materia");
		
		estBuscado.removeMateria(materia);
		
		return repoEst.save(estBuscado);
	}
	
	@Override
	public Estudiante cargarInteres(EstudianteDTO buscado) {
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Tag interes = repoTags.findById(buscado.getIdInteres()).orElseThrow(TagInexistenteException::new);
		
		if(estBuscado.getIntereses().contains(interes))
			throw new InteresYaCargadoException();
		
		estBuscado.addInteres(interes);
		
		return repoEst.save(estBuscado);
	}
	
	@Override
	public Estudiante borrarInteres(EstudianteDTO buscado) {
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Tag interes = repoTags.findById(buscado.getIdInteres()).orElseThrow(TagInexistenteException::new);
		
		if(!estBuscado.getIntereses().contains(interes))
			throw new TagInexistenteException("El Estudiante no tiene dicho interes.");
		
		estBuscado.removeInteres(interes);
		
		return repoEst.save(estBuscado);
	}
	
	@Override
	public Estudiante sumarPuntos(EstudianteDTO buscado) {
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		
		estBuscado.setPuntos(estBuscado.getPuntos() + buscado.getPuntos());

		return repoEst.save(estBuscado);
	}

	@Override
	public Estudiante cambiarFacultad(EstudianteDTO buscado) {
		Estudiante estBuscado = findEstudianteByParKey(buscado);
		Facultad buscada = findFacultadById(buscado);
		
		if(!estBuscado.getCarreras().isEmpty())
			throw new CarreraEnCursoException();
		
		estBuscado.setFacultad(buscada);
		
		return repoEst.save(estBuscado);
	}

	private Materia findMateriaByCarrera(EstudianteDTO buscado) {
		return repoMateria.findByCarrera(buscado.getIdMateria(), buscado.getIdCarrera()).orElseGet(() -> {
			throw new MateriaInexistenteException();
		});
	}

	private Carrera findCarreraByFacultad(EstudianteDTO buscado) {
		return repoCarrera.findByFacultad(buscado.getIdCarrera(), buscado.getIdFacultad()).orElseGet(() -> {
			throw new CarreraInexistenteException();
		});
	}
	
	private Estudiante findEstudianteByParKey(EstudianteDTO buscado) {
		return repoEst.findByParKey(buscado.getId()).orElseGet(() -> {
			throw new EstudianteInexistenteException();
		});
	}
	
	private Facultad findFacultadById(EstudianteDTO buscado) {
		return repoFacu.findById(buscado.getIdFacultad()).orElseGet(() -> {
			throw new FacultadInexistenteException();
		});
	}
	
	private Carrera findCarreraByAlumno(EstudianteDTO buscado) {
		return repoCarrera.findByAlumno(buscado.getIdCarrera(), buscado.getId()).orElseGet(() -> {
			throw new CarreraInexistenteException();
		});
	}

	

}
