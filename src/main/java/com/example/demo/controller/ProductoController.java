package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.CategoriaEntity;
import com.example.demo.model.ProductoEntity;
import com.example.demo.model.UsuarioEntity;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.impl.PdfService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/menu")
public class ProductoController {

	@Autowired private ProductoService productoService;
	@Autowired private CategoriaService categoriaService;
	@Autowired private UsuarioService usuarioService;
	@Autowired private PdfService pdfService; 
	
	@GetMapping("/listado")
	public String listarProducto(Model model) {
		List<ProductoEntity>listaProducto = productoService.listarProducto();
		model.addAttribute("listapro", listaProducto);
		return "lista_producto";
	}
	
	@GetMapping("/registrar_producto")
	public String mostrarRegistrarProducto(Model model) {
		List<CategoriaEntity>listaCate= categoriaService.listarCategoria();
		model.addAttribute("categorias",listaCate);
		model.addAttribute("producto", new ProductoEntity());
		return "registrar_producto";
	}
	
	@PostMapping("/registrar_producto")
	public String registrarProducto(@ModelAttribute("producto") ProductoEntity pro
			, Model model) {
		
		productoService.crearProducto(pro);
		return "redirect:/menu/listado";
		
	}
	
  	//DETALLE EMPLEADO///
	@GetMapping("/detalle_producto/{productoId}")
	public String verDetalle(Model model, @PathVariable("productoId") Integer productoId) {
		ProductoEntity producto = productoService.buscarProductoPorId(productoId);
			model.addAttribute("producto", producto);
			return "detalle_producto";
	}
	
	//Actualizar producto////
	
	@GetMapping("/editar_producto/{productoId}")
	public String mostrarActualizar(@PathVariable("productoId")Integer productoId, Model model) {
		ProductoEntity producto = productoService.buscarProductoPorId(productoId);
		List<CategoriaEntity>listaCate= categoriaService.listarCategoria();
		model.addAttribute("categorias",listaCate);
		model.addAttribute("producto", producto);
		return "editar_producto";
	}
	
	@PostMapping("/editar_producto/{productoId}")
	public String actualizarProducto(@PathVariable("productoId")Integer productoId, @ModelAttribute("producto") ProductoEntity producto, Model model) {
		productoService.actualizarProducto(productoId, producto);
		return "redirect:/menu/listado";
	}
	
	//Eliinar producto///
	
	@GetMapping("/delete/{productoId}")
	public String deleteProducto(Model model, @PathVariable("productoId") Integer productoId) {
		productoService.eliminarProducto(productoId);
		return "redirect:/menu/listado";
	}
	
	//Generar pdf///
	
	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException {
		String usuarioEmail = (String) session.getAttribute("usuario");
	    List<ProductoEntity> productos = productoService.listarProducto();

	    Map<String, Object> datosPdf = new HashMap<>();
	    datosPdf.put("productos", productos);
	    datosPdf.put("usuario", usuarioEmail);

	    ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=productos.pdf");

	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(pdfBytes));
	}
	
	
	
	
	
	
	
	
	
	
}
