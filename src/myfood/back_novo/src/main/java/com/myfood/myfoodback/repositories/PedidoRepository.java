package com.myfood.myfoodback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.myfoodback.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}