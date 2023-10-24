package com.fantasticos.algorithmservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fantasticos.algorithmservice.dto.GrupoDTO;
import com.fantasticos.algorithmservice.model.Estudiante;
import com.fantasticos.algorithmservice.model.Grupo;
import com.fantasticos.algorithmservice.model.Materia;
import com.fantasticos.algorithmservice.model.Tag;
import com.fantasticos.algorithmservice.repository.EstudianteRepository;
import com.fantasticos.algorithmservice.repository.GrupoRepository;
import com.fantasticos.algorithmservice.repository.MateriaRepository;
import com.fantasticos.algorithmservice.repository.TagRepository;
import com.fantasticos.algorithmservice.service.GrupoService;
import com.fantasticos.algorithmservice.util.exceptions.EstudianteInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.EstudiantePersistidoException;
import com.fantasticos.algorithmservice.util.exceptions.GrupoInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.GrupoYaExisteException;
import com.fantasticos.algorithmservice.util.exceptions.InteresYaCargadoException;
import com.fantasticos.algorithmservice.util.exceptions.MateriaInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.SinPermisosException;
import com.fantasticos.algorithmservice.util.exceptions.TagInexistenteException;
import com.fantasticos.algorithmservice.util.exceptions.UltimoMiembroException;

@Service
@Transactional
public class GrupoServiceImpl implements GrupoService {

	@Autowired private MateriaRepository repoMateria;
	@Autowired private GrupoRepository repoGrupo;
	@Autowired private EstudianteRepository repoEst;
	@Autowired private TagRepository repoTags;

	@Override
	public Grupo cargarGrupo(GrupoDTO nuevo) {
		Materia materia = null;
		Estudiante administrador = repoEst.findByParKey(nuevo.getIdAdmin()).orElseThrow(EstudianteInexistenteException::new);

		if (nuevo.getIdMateria() != null) {
			materia = repoMateria.findByCodigo(nuevo.getIdMateria()).orElseThrow(MateriaInexistenteException::new);
			if(!repoEst.isCursandoMateria(nuevo.getIdAdmin(), nuevo.getIdMateria()))
				throw new MateriaInexistenteException("No esta cursando la materia para poder armar un grupo");
		}

		Grupo cargado = nuevo.build();
		cargado.setAdministrador(administrador);
		cargado.addMiembro(administrador);
		cargado.setMateria(materia);
		cargado.setIntereses(administrador.getIntereses());

		try {
			return repoGrupo.save(cargado);
		}catch(Exception e) {
			throw new GrupoYaExisteException();
		}
	}

	@Override
	public Grupo cargarInteres(GrupoDTO objetivo) {
		Grupo encontrado = repoGrupo.findByParKey(objetivo.getIdGrupo()).orElseThrow(GrupoInexistenteException::new);
		Tag interes = repoTags.findById(objetivo.getIdInteres()).orElseThrow(TagInexistenteException::new);
		
		if(!encontrado.getAdministrador().getParKey().equals(objetivo.getIdAdmin()))
			throw new SinPermisosException("No tienes permisos para agregar un tag");
		if(encontrado.getIntereses().contains(interes))
			throw new InteresYaCargadoException("El grupo ya tiene cargado el interes");
		
		encontrado.getIntereses().add(interes);
		
		return repoGrupo.save(encontrado);
	}

	@Override
	public Grupo borrarInteres(GrupoDTO objetivo) {
		Grupo encontrado = repoGrupo.findByParKey(objetivo.getIdGrupo()).orElseThrow(GrupoInexistenteException::new);
		Tag interes = repoTags.findById(objetivo.getIdInteres()).orElseThrow(TagInexistenteException::new);
		
		System.out.println(encontrado.getAdministrador().getParKey());
		System.out.println(objetivo.getIdAdmin());
		
		if(!encontrado.getAdministrador().getParKey().equals(objetivo.getIdAdmin()))
			throw new SinPermisosException("No tienes permisos para quitar un tag");
		if(!encontrado.getIntereses().contains(interes))
			throw new TagInexistenteException("El grupo no tiene el tag solicitado");
		
		encontrado.getIntereses().remove(interes);
		
		return repoGrupo.save(encontrado);
	}

	@Override
	public Grupo buscarGrupo(Long idGrupo) {
		return repoGrupo.findByParKey(idGrupo).orElseThrow(GrupoInexistenteException::new);
	}

	@Override
	public Grupo agregarMiembro(GrupoDTO objetivo) {
		Grupo encontrado = repoGrupo.findByParKey(objetivo.getIdGrupo()).orElseThrow(GrupoInexistenteException::new);
		Estudiante miembro = repoEst.findByParKey(objetivo.getIdMiembro()).orElseThrow(EstudianteInexistenteException::new);
		
		if(encontrado.getMiembros().contains(miembro))
			throw new EstudiantePersistidoException("El estudiante ya es miembro del grupo");
		
		encontrado.addMiembro(miembro);
		
		return repoGrupo.save(encontrado);
	}

	@Override
	public void borrarGrupo(GrupoDTO objetivo) {
		Grupo encontrado = repoGrupo.findByParKey(objetivo.getIdGrupo()).orElseThrow(GrupoInexistenteException::new);
		
		if(!encontrado.getAdministrador().getParKey().equals(objetivo.getIdAdmin()))
			throw new SinPermisosException("No tienes permisos para eliminar el grupo");
		
		repoGrupo.delete(encontrado);
	}

	@Override
	public Grupo removerMiembro(GrupoDTO objetivo) {
		Grupo encontrado = repoGrupo.findByParKey(objetivo.getIdGrupo()).orElseThrow(GrupoInexistenteException::new);
		
		if(encontrado.getMiembros().size() == 1)
			throw new UltimoMiembroException();

		Estudiante buscado = encontrado.getMiembros().stream()
			.filter(e -> e.getParKey().equals(objetivo.getIdAdmin()))
			.findAny()
			.orElseThrow(EstudianteInexistenteException::new)
			;
		
		encontrado.getMiembros().remove(buscado);

		if(buscado.equals(encontrado.getAdministrador()))
			encontrado.setAdministrador(encontrado.getMiembros().get(0));
		
		return repoGrupo.save(encontrado);
	}

}
