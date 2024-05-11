package com.myfood.myfoodback.controllers;

import com.myfood.myfoodback.models.Produtos;
import com.myfood.myfoodback.repositories.ProdutosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://plf-es-2024-1-ti2-1372100-grupo-4-restaurante-deploy-my-food.vercel.app/")
@RestController
public class ProdutosController {

    private final ProdutosRepository produtosRepository;

    public ProdutosController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Produtos>> getAllProdutos() {
        List<Produtos> produtos = produtosRepository.findAll();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @PostMapping("/produtos")
    public ResponseEntity<Produtos> addProduto(@RequestBody Produtos produto) {
        Produtos savedProduto = produtosRepository.save(produto);
        return new ResponseEntity<>(savedProduto, HttpStatus.CREATED);
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produtos> getProdutoById(@PathVariable Long id) {
        Produtos produto = produtosRepository.findById(id).orElse(null);
        if (produto != null) {
            return new ResponseEntity<>(produto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/produtos/edit/{id}")
    public ResponseEntity<Produtos> updateProduto(@PathVariable Long id, @RequestBody Produtos updatedProduto) {
        Produtos existingProduto = produtosRepository.findById(id).orElse(null);
        if (existingProduto != null) {
            updatedProduto.setId(id);
            Produtos savedProduto = produtosRepository.save(updatedProduto);
            return new ResponseEntity<>(savedProduto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/produtos/delete/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        Produtos produto = produtosRepository.findById(id).orElse(null);
        if (produto != null) {
            produtosRepository.delete(produto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
