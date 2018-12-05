package com.phoebus.CRUDAluno.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phoebus.CRUDAluno.model.Aluno;
import com.phoebus.CRUDAluno.repository.filter.AlunoFilter;

public interface AlunoRepositoryQuery {

	public Page<Aluno> filter(AlunoFilter filter, Pageable pageable);
	
}
