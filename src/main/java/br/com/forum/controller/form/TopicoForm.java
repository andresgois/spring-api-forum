package br.com.forum.controller.form;

import br.com.forum.modelo.Curso;
import br.com.forum.modelo.Topico;
import br.com.forum.repository.CursoRepository;

public class TopicoForm {

	private String titulo;
	private String mensagem;
	private String nomeCurso;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public Topico converter(CursoRepository cursoRepository) {
		// TODO Auto-generated method stub
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
	
	
	
}
