package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CategoriaEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.CategoriaService;
@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired CategoriaRepository categoriaRepository;
	@Override
	public List<CategoriaEntity> listarCategoria() {
		// TODO Auto-generated method stub
		return categoriaRepository.findAll();
	}

}
