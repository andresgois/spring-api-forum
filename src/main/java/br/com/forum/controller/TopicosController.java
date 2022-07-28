package br.com.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.forum.controller.DTO.DetalhesDoTopicoDTO;
import br.com.forum.controller.DTO.TopicoDTO;
import br.com.forum.controller.form.AtualizacaoTopicoForm;
import br.com.forum.controller.form.TopicoForm;
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
	public TopicosController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
		this.topicoRepository = topicoRepository;
		this.cursoRepository = cursoRepository;
	}

	// @RequestMapping("/topicos")
	// @RequestMapping(value = "/topicos",method = RequestMethod.GET)
	// @ResponseBody
	@GetMapping
	@Transactional
	@Cacheable(value = "listaDeTopicos")	// habilita cache para esse erviço
	public Page<TopicoDTO> lista(
			@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination
			//@RequestParam int page,
			//@RequestParam int size,
			//@RequestParam String sort
	) {
		/**
		 * Vesão antiga Topico topico = new Topico("Dúvida", "Dúvida com Spring", new
		 * Curso("Spring", "Programação")); return
		 * TopicoDTO.converter(Arrays.asList(topico, topico, topico));
		 */
		//Pageable pagination = PageRequest.of(page, size, Direction.DESC, sort);
		
		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(pagination);
			return TopicoDTO.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, pagination);
			return TopicoDTO.converter(topicos);
		}
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);

		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}

	/**
	 * @param id
	 * @return Caso queira chamar a variavel de outro nome public TopicoDTO
	 *         detalhar(@PathVariable("id") Long codigo)
	 */
	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDTO(topico.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form,
			UriComponentsBuilder uriBuilder) {
		Optional<Topico> optional = topicoRepository.findById(id);

		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);

		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			// return ResponseEntity.ok().Build();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
