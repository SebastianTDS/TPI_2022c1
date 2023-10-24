package com.fantasticos.studentservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasticos.studentservice.model.Kick;


public interface KickRepository extends JpaRepository<Kick, Long>{
	
	public Optional<Kick> findByIdGrupoAndIdVotado(Long idGrupo, String idVotado);

}
