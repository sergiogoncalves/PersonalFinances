package com.projeto.springboot;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.springboot.event.ResourceCreatedEvent;
import com.projeto.springboot.model.Pessoa;
import com.projeto.springboot.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		
		return ResponseEntity.ok(pessoas);
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
	 	
		publisher.publishEvent(new ResourceCreatedEvent(this, response, pessoaSalva.getCodigo()));
	 	
	 	return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
			
		Pessoa pessoaRetorno = pessoaRepository.findOne(codigo);
		
		if (pessoaRetorno != null) {
			return ResponseEntity.ok(pessoaRetorno);
		}
		return ResponseEntity.notFound().build();
		
	}
}
