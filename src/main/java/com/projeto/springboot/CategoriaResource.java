package com.projeto.springboot;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.springboot.model.Categoria;
import com.projeto.springboot.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Categoria> categorias = categoriaRepository.findAll();
		
		//return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
		return ResponseEntity.ok(categorias);
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
	 	Categoria categoriaSalva = categoriaRepository.save(categoria);
	 	
	 	URI uri = ServletUriComponentsBuilder
	 				.fromCurrentRequestUri().path("/{codigo}")
	 					.buildAndExpand(categoriaSalva.getCodigo()).toUri();
	 	
	 	response.setHeader("Location", uri.toASCIIString());
	 	
	 	return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
			
		Categoria categoriaRetorno = categoriaRepository.findOne(codigo);
		
		if (categoriaRetorno != null) {
			return ResponseEntity.ok(categoriaRetorno);
		}
		return ResponseEntity.notFound().build();
		
	}

}
