package com.myfood.myfoodback.controllers;

import com.myfood.myfoodback.models.Pedido;
import com.myfood.myfoodback.repositories.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> addPedido(@RequestBody Pedido pedido) {
        Pedido savedPedido = pedidoRepository.save(pedido);
        return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(statusUpdate.get("status"));
            pedidoRepository.save(pedido);
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
