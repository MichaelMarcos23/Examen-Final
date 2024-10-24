package com.example.demo.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.demo.model.ProductoEntity;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PdfService {

	@Autowired
	private SpringTemplateEngine templateEngine;
	public ByteArrayInputStream generarPdf(String template, Map<String, Object> datos) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    try {
	        PdfWriter writer = new PdfWriter(outputStream);
	        PdfDocument pdf = new PdfDocument(writer);
	        Document document = new Document(pdf);

	        document.add(new Paragraph("Generado por:" ));

	        List<ProductoEntity> productos = (List<ProductoEntity>) datos.get("productos");
	        for (ProductoEntity producto : productos) {
	            document.add(new Paragraph("Nombre: " + producto.getNombreProducto() +
	                                       " - Precio: " + producto.getPrecio() +
	                                       " - Stock: " + producto.getStock()));
	        }

	        document.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ByteArrayInputStream(outputStream.toByteArray());
	}
}
