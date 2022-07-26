package br.com.forum.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.forum.controller.DTO.TopicoDTO;
import br.com.forum.controller.form.TopicoForm;
import br.com.forum.modelo.Curso;
import br.com.forum.modelo.Topico;
import br.com.forum.repository.CursoRepository;
import br.com.forum.repository.TopicoRepository;

//@Controller
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	

	private TopicoRepository topicoRepository;

	private CursoRepository cursoRepository;
	
	@Autowired
	public TopicosController(
			TopicoRepository topicoRepository,
			CursoRepository cursoRepository
	) {
		this.topicoRepository = topicoRepository;
		this.cursoRepository = cursoRepository;
	}
	
	
	//@RequestMapping("/topicos")
	//@RequestMapping(value = "/topicos",method = RequestMethod.GET)
	//@ResponseBody
	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso){
		/**
		 * Vesão antiga
		 * Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		 * return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
		 */
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converter(topicos);
		}else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDTO.converter(topicos);
		}
	}
	
	@PostMapping
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository); 
				
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
}
