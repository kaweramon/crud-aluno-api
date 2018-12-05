package com.phoebus.CRUDAluno.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "permissao")
public class Permissao {

	@Id
	private Long id;
	private String descricao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
