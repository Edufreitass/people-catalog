package com.example.tdd.repository.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.tdd.modelo.Pessoa;
import com.example.tdd.repository.filtro.PessoaFiltro;

@Component
public class PessoaRepositoryImpl implements PessoaRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> filtrar(PessoaFiltro filtro) {
		final StringBuilder sb = new StringBuilder();
		final Map<String, Object> params = new HashMap<>();

		sb.append("SELECT p FROM Pessoa p JOIN p.telefones t WHERE 1=1");

		preencherNomeSeNecessario(filtro, sb, params);
		preencherCPFSeNecessario(filtro, sb, params);
		preencherDddSeNecessario(filtro, sb, params);
		preencherNumeroTelefoneSeNecessario(filtro, sb, params);

		Query query = manager.createQuery(sb.toString(), Pessoa.class);
		preencherParametroDaQuery(params, query);

		return query.getResultList();
	}

	private void preencherNumeroTelefoneSeNecessario(PessoaFiltro filtro, final StringBuilder sb,
			final Map<String, Object> params) {
		if (StringUtils.hasText(filtro.getTelefone())) {
			sb.append(" AND t.numero = :numero ");
			params.put("numero", filtro.getTelefone());
		}
	}

	private void preencherDddSeNecessario(PessoaFiltro filtro, final StringBuilder sb,
			final Map<String, Object> params) {
		if (StringUtils.hasText(filtro.getDdd())) {
			sb.append(" AND t.ddd = :ddd ");
			params.put("ddd", filtro.getDdd());
		}
	}

	private void preencherCPFSeNecessario(PessoaFiltro filtro, final StringBuilder sb,
			final Map<String, Object> params) {
		if (StringUtils.hasText(filtro.getCpf())) {
			sb.append(" AND p.cpf LIKE :cpf ");
			params.put("cpf", "%" + filtro.getCpf() + "%");
		}
	}

	private void preencherNomeSeNecessario(PessoaFiltro filtro, final StringBuilder sb,
			final Map<String, Object> params) {
		if (StringUtils.hasText(filtro.getNome())) {
			sb.append(" AND p.nome LIKE :nome ");
			params.put("nome", "%" + filtro.getNome() + "%");
		}
	}

	private void preencherParametroDaQuery(final Map<String, Object> params, Query query) {
		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
	}

}
