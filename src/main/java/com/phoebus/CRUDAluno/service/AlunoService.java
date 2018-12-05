package com.phoebus.CRUDAluno.service;

import java.util.List;

import com.phoebus.CRUDAluno.model.Aluno;

public interface AlunoService {

	public Aluno criar(Aluno aluno);
	
	public Aluno atualizar(Aluno aluno, Long alunoId);
	
	public Aluno recuperar(Long alunoId);
	
	public List<Aluno> listar();
	
	public void deletar(Long alunoId);
	
}
