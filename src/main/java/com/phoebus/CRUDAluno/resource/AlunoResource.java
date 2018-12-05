package com.phoebus.CRUDAluno.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.phoebus.CRUDAluno.model.Aluno;
import com.phoebus.CRUDAluno.repository.AlunoRepository;
import com.phoebus.CRUDAluno.repository.filter.AlunoFilter;
import com.phoebus.CRUDAluno.service.AlunoService;

@RestController
@RequestMapping(path = "/alunos")
public class AlunoResource {

	@Autowired
	private AlunoService service;
	
	@Autowired
	private AlunoRepository repository;
	
	@PostMapping
	public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
		return ResponseEntity.ok(service.criar(aluno));
	}
	
	@PutMapping(path = "/{alunoId}")
	public ResponseEntity<Aluno> atualizar(@PathVariable Long alunoId, 
			@RequestBody Aluno aluno) {
		return ResponseEntity.ok(service.atualizar(aluno, alunoId));
	}
	
	@GetMapping(path = "/{alunoId}")
	public ResponseEntity<Aluno> recuperar(@PathVariable Long alunoId) {
		return ResponseEntity.ok(service.recuperar(alunoId));
	}
	
	@GetMapping
	public Page<Aluno> listar(AlunoFilter filter, Pageable pageable) {
		return repository.filter(filter, pageable);
	}
	
	@DeleteMapping(path = "/{alunoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long alunoId) {
		service.deletar(alunoId);
	}
}
