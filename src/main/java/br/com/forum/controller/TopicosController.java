package br.com.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.forum.controller.DTO.TopicoDTO;
import br.com.forum.modelo.Curso;
import br.com.forum.modelo.Topico;
import br.com.forum.repository.TopicoRepository;

//@Controller
@RestController
public class TopicosController {
	
	private TopicoRepository topicoRepository;
	
	@Autowired
	public TopicosController(TopicoRepository topicoRepository) {
		this.topicoRepository = topicoRepository;
	}
	
	@RequestMapping("/topicos")
	//@ResponseBody
	public List<TopicoDTO> lista(){
		/**
		 * Vesão antiga
		 * Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		 * return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
		 */
		List<Topico> topicos = topicoRepository.findAll();
		return TopicoDTO.converter(topicos);
		
	}
}
