package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ProductoEntity;

public interface ProductoService {

	List<ProductoEntity>listarProducto();
	void crearProducto(ProductoEntity productoEntity);
	ProductoEntity buscarProductoPorId(Integer productoId);
	void actualizarProducto(Integer productoId, ProductoEntity productoEntity);
	void eliminarProducto(Integer productoId);
}
