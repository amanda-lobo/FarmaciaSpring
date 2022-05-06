package com.generation.farmacia.controller;

import java.math.BigDecimal;
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

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll()
	{
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{idProduto}")
	public ResponseEntity<Produto> getById(@PathVariable Long idProduto)
	{
		return produtoRepository.findById(idProduto)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nomeProduto/{nomeProduto}")
	public ResponseEntity<List<Produto>> getBynomeProduto(@PathVariable String nomeProduto)
	{
		return ResponseEntity.ok(produtoRepository.findAllByNomeProdutoContainingIgnoreCase(nomeProduto));
	}
	
	@GetMapping("/menorPreco/{menorPreco}")
	public ResponseEntity<List<Produto>> getByMenorPreco(@PathVariable BigDecimal preco)
	{
		return ResponseEntity.ok(produtoRepository.findAllByPrecoLessThanOrderByPreco(preco));
	}
	
	@GetMapping("/maiorPreco/{maiorPreco}")
	public ResponseEntity<List<Produto>> getByMaiorPreco(@PathVariable BigDecimal preco)
	{
		return ResponseEntity.ok(produtoRepository.findAllByPrecoGreaterThanOrderByPreco(preco));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto (@Valid @RequestBody Produto produto)
	{
//		if(categoriaRepository.existsById(produto.getCategoria().getIdCategoria()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
	}

	@PutMapping 
	public ResponseEntity<Produto> putProduto (@Valid @RequestBody Produto produto)
	{
		if(produtoRepository.existsById(produto.getIdProduto()))
		{
			return categoriaRepository.findById(produto.getCategoria().getIdCategoria())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
					.orElse(ResponseEntity.badRequest().build());
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto (@PathVariable Long idProduto)
	{
		return produtoRepository.findById(idProduto)
				.map(resposta -> {
					produtoRepository.deleteById(idProduto);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
