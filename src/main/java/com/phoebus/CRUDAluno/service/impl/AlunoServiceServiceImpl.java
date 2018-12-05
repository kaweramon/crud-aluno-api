package com.phoebus.CRUDAluno.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phoebus.CRUDAluno.generic.ResourceNotFoundException;
import com.phoebus.CRUDAluno.model.Aluno;
import com.phoebus.CRUDAluno.repository.AlunoRepository;
import com.phoebus.CRUDAluno.service.AlunoService;

@Service
public class AlunoServiceServiceImpl implements AlunoService {

	@Autowired
	private AlunoRepository repository;
	
	@Override
	@Transactional(readOnly = false)
	public Aluno criar(Aluno aluno) {
		return repository.save(aluno);
	}

	@Override
	@Transactional(readOnly = false)
	public Aluno atualizar(Aluno aluno, Long alunoId) {
		Aluno alunoBD = recuperarPorId(alunoId);
		
		BeanUtils.copyProperties(aluno, alunoBD, "id");
		
		return repository.save(alunoBD);
	}

	@Override
	public Aluno recuperar(Long alunoId) {
		return recuperarPorId(alunoId);
	}

	@Override
	public List<Aluno> listar() {
		return (List<Aluno>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void deletar(Long alunoId) {
		repository.delete(recuperarPorId(alunoId));
	}

	private Aluno recuperarPorId(Long id) {
		Optional<Aluno> aluno = repository.findById(id);
		
		if(aluno == null || !aluno.isPresent())
			throw new ResourceNotFoundException("Aluno não encontrado!");
		else
			return aluno.get();
		
	}
		
	
}
