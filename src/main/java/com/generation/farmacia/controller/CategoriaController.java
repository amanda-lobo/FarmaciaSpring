package com.generation.farmacia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	public CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll()
	{
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/{idCategoria}")
	public ResponseEntity<Categoria> getById(@PathVariable Long idCategoria)
	{
		return categoriaRepository.findById(idCategoria)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nomeCategoria/{nomeCategoria}")
	public ResponseEntity<List<Categoria>> getByNomeCategoria (@PathVariable String nomeCategoria)
	{
		return ResponseEntity.ok(categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria));
	}
	
	@PostMapping
	public ResponseEntity<Categoria> postCategoria (@Valid @RequestBody Categoria categoria)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}
	
	@PutMapping
	public ResponseEntity<Categoria> putCategoria (@Valid @RequestBody Categoria categoria)
	{
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
	}
	
	@DeleteMapping("/{idCategoria}")
	public ResponseEntity<?> deleteCategoria (@PathVariable Long idCategoria)
	{
		return categoriaRepository.findById(idCategoria)
		.map(resposta -> {
			categoriaRepository.deleteById(idCategoria);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		})
		.orElse(ResponseEntity.notFound().build());
	}
}
