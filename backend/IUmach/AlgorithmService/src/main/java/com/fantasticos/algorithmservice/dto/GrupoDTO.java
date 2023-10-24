package com.fantasticos.algorithmservice.dto;

import com.fantasticos.algorithmservice.model.Grupo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GrupoDTO {

	private Long idGrupo;
	private String idAdmin;
	private String idMiembro;
	private Integer idMateria;
	private Long idInteres;
	
	public Grupo build() {
		return new Grupo(idGrupo);
	}
	
	public GrupoDTO changeId(String id) {
		this.idAdmin = id;
		return this;
	}
	
}
