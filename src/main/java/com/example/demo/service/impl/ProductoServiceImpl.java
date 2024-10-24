package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ProductoEntity;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired ProductoRepository productoRepository;
	@Override
	public List<ProductoEntity> listarProducto() {
		// TODO Auto-generated method stub
		return productoRepository.findAll();
	}

	@Override
	public void crearProducto(ProductoEntity productoEntity) {
		productoRepository.save(productoEntity);
		
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer productoId) {
		// TODO Auto-generated method stub
		return productoRepository.findById(productoId).get();
	}

	@Override
	public void actualizarProducto(Integer productoId, ProductoEntity productoActualizado) {
		ProductoEntity productoEncontrado = buscarProductoPorId(productoId);
		
		if(productoEncontrado == null) {
			throw new RuntimeException("producto no encontrado");
		}
		try {
			productoEncontrado.setNombreProducto(productoActualizado.getNombreProducto());
			productoEncontrado.setPrecio(productoActualizado.getPrecio());
			productoEncontrado.setStock(productoActualizado.getStock());
			productoEncontrado.setCategoriaEntity(productoActualizado.getCategoriaEntity());
			productoRepository.save(productoEncontrado);
		}catch (Exception e) {
			throw new RuntimeException("Error al actualizar");
		}
		
	}

	@Override
	public void eliminarProducto(Integer productoId) {
		ProductoEntity productoencontrado = buscarProductoPorId(productoId);
		if(productoencontrado == null) {
			throw new RuntimeException("Producto no encontrado");
		}
		productoRepository.delete(productoencontrado);
		
		
	}

}
