package br.com.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import br.com.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	/**
	 * 
	 * @param nomeCurso
	 * @return
	 * Como dentro de Topico tem um relacionamento com curso e queremos buscar o nome desse curso, colocamos Entidade e campo da entidade
	 * caso existisse um atributo em Topico com esse nome daria comflito, nesse casso ele enxergaria o CursoNome como atributo de Topico
	 * para referênciar uma entidade e seu atributo dentro do topico, teriamos que colocar Curso_Nome
	 */
	Page<Topico> findByCursoNome(String nomeCurso, Pageable pagination);

	/**
	 * 
	 * @param nomeCurso
	 * @return
	 * Caso queira mudar o nome do método o JPA não mas vai reconhecer, então você tem que usar a query e associar os parâmetros.
	 * 
	 * Utilizar consultas:
	 * JPQL significa Java Persistence Query Language. 
	 * Ele é usado para criar consultas contra entidades para armazenar em um banco de dados relacional.
	 */
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
	
}
