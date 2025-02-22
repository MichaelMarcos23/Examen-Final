package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.UsuarioEntity;
import com.example.demo.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
@Controller
public class UsuarioController {
	
	@Autowired UsuarioService usuarioService;
	
	@GetMapping("/registrar_usuario")
	public String mostrarRegistrarUsuario(Model model) {
		model.addAttribute("usuario", new UsuarioEntity());
		return "registrar_usuario";
	}
	
	@PostMapping("/registrar_usuario")
	public String registrarUsuario(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
			Model model, @RequestParam("foto") MultipartFile foto) {
		
		usuarioService.crearUsuario(usuarioFormulario, foto);
		return "login";
	}
	
	@GetMapping("/")
	public String mostrarLogin(Model model) {
		model.addAttribute("usuario", new UsuarioEntity());
		return "login";	
	}
	
	
	@PostMapping("/login")
	public String login(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
			Model model, HttpSession session) {
		boolean validarUsuario = usuarioService.validarUsuario(usuarioFormulario);
		if(validarUsuario) {
			session.setAttribute("usuario", usuarioFormulario.getCorreo());
			return "redirect:/menu/listado";
		}
		model.addAttribute("loginInvalido", "No existe el usuario");
		model.addAttribute("usuario", new UsuarioEntity());
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
