package com.phoebus.CRUDAluno.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.phoebus.CRUDAluno.model.Aluno;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long>, AlunoRepositoryQuery {

}
