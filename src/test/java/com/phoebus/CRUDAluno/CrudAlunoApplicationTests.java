package com.phoebus.CRUDAluno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.phoebus.CRUDAluno.model.Aluno;
import com.phoebus.CRUDAluno.resource.AlunoResource;
import com.phoebus.CRUDAluno.service.AlunoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudAlunoApplicationTests {

	@Autowired
	private AlunoService service;
	
	@Autowired
	private AlunoResource resource;
	
	@Test
	public void testSalvar() {
		
		Aluno aluno = new Aluno();
		aluno.setNome("Aluno1");
		aluno.setIdade(10);
		aluno.setMatricula("789456");
		
		Aluno alunoBD = service.criar(aluno);
		
		assertNotNull(aluno);
		assertEquals("Aluno1", alunoBD.getNome());
		assertTrue(alunoBD.getIdade() == 10);
		
		Aluno aluno2 = new Aluno();
		aluno2.setNome("Aluno2");
		aluno2.setIdade(12);
		aluno2.setMatricula("56789");
		
		Aluno alunoBD2 = service.criar(aluno2);
		
		assertNotNull(alunoBD2);
		assertEquals("Aluno2",alunoBD2.getNome());
		assertNotNull(alunoBD2.getIdade());
		
	}
	
	@Test
	public void testAtualizar() {
		Aluno aluno = new Aluno("Aluno Teste 1", 12, "08886187432", "123");
		service.criar(aluno);
		
		Aluno alunoAtualizado = new Aluno("Aluno Teste 1 Atualizado", 14, null, null);
		Aluno alunoDB = service.atualizar(alunoAtualizado, 1L);
		
		assertEquals("Aluno Teste 1 Atualizado", alunoDB.getNome());
		assertEquals(14, alunoDB.getIdade());
	}
	
	@Test
	public void testDeletar() {
		Aluno aluno = new Aluno("Aluno Teste 3", 12, "33790030007", null);
		Long idAlunoCriado = service.criar(aluno).getId();
		int countBefore = service.listar().size();
		service.deletar(idAlunoCriado);
		int countAfter = service.listar().size();
		assertEquals(countBefore - 1, countAfter);
	}
	
	@Test
	public void testCpfJaCadastrado() {
		Aluno aluno = new Aluno("Aluno Teste 1", 12, "08886187432", "123");
		ResponseEntity<Aluno> response = resource.criar(aluno);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		Aluno alunoCpfRepetido = new Aluno("Aluno Teste 2", 12, "08886187432", null);
		ResponseEntity<Aluno> responseComErro = null;
		try {
			 responseComErro = resource.criar(alunoCpfRepetido);
		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
			assertTrue(e.getMessage().contains("CPF_UK"));
			assertNull(responseComErro);
		}
	}
	
	@Test
	public void testMatriculaJaCadastrada() {
		Aluno aluno = new Aluno("Aluno 1", 15, null, "123456");
		service.criar(aluno);
		Aluno alunoMatriculaRepetida = new Aluno("Aluno matricula repetida", 15, null, "123456");
		try {
			service.criar(alunoMatriculaRepetida);
		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
			assertTrue(e.getMessage().contains("MATRICULA_UK"));
		}
	}
	
}
