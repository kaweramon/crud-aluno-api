package com.phoebus.CRUDAluno.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.phoebus.CRUDAluno.model.Aluno;
import com.phoebus.CRUDAluno.repository.filter.AlunoFilter;

public class AlunoRepositoryImpl implements AlunoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Aluno> filter(AlunoFilter filter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteriaQuery = builder.createQuery(Aluno.class);
		Root<Aluno> root = criteriaQuery.from(Aluno.class);
		
		criteriaQuery.where(createRestrictions(filter, builder, root));
		
		TypedQuery<Aluno> query = manager.createQuery(criteriaQuery);
		
		if (pageable != null)
			addPageableRestrictions(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, getTotal(filter));
	}

	private Predicate[] createRestrictions(AlunoFilter filter, CriteriaBuilder builder,
			Root<Aluno> root) {
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (filter.getId() != null) 
			predicates.add(builder.equal(root.get("id"), filter.getId()));
		
		if (!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" + 
					filter.getNome().toLowerCase() + "%"));
		}
		
		if (!StringUtils.isEmpty(filter.getCpf()))
			predicates.add(builder.equal(root.get("cpf"), filter.getCpf()));
		
		if (filter.getIdade() > 0)
			predicates.add(builder.equal(root.get("idade"), filter.getIdade()));
		
		if (!StringUtils.isEmpty(filter.getMatricula()))
			predicates.add(builder.equal(root.get("matricula"), filter.getMatricula()));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPageableRestrictions(TypedQuery<?> query, Pageable pageable) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

	private Long getTotal(AlunoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Aluno> root = criteria.from(Aluno.class);
		
		Predicate[] predicates = createRestrictions(filter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
}
