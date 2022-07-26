package br.com.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.forum.controller.DTO.TopicoDTO;
import br.com.forum.modelo.Curso;
import br.com.forum.modelo.Topico;

//@Controller
@RestController
public class TopicosController {
	@RequestMapping("/topicos")
	//@ResponseBody
	public List<TopicoDTO> lista(){
		Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		
		return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
	}
}
