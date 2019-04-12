package com.idm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idm.model.Usuario;
import com.idm.service.IDMService;

@RestController
public class HomeController {


		@Autowired
		IDMService idmService;
		
		
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String home() throws Exception {
			
			String data = "hello";
			String cipher = idmService.encrypt(data);
			String decipher = idmService.decrypt(cipher);
			System.out.println(cipher);
			System.out.println(decipher);
			 
			return cipher; 
		};
		

		

		@RequestMapping(value = "/bloquear", method = RequestMethod.POST)
		public String bloquearUsuario(@RequestBody Usuario usuario) throws Exception {
			
			System.out.println("Usuario => " + usuario);
			System.out.println("Usuario noEmpleado => " + usuario.getNumEmpleado());
			System.out.println("Usuario  Authorization=> " + usuario.getAuthorization());
	
           String result =  idmService.bloquearUsuario(usuario);
           String decipher = idmService.decrypt(result);
			
			return decipher;
			
		};
		
		
		@RequestMapping(value = "/cifrar", method = RequestMethod.POST)
		public String cifrar(@RequestBody String request) throws Exception {
			// print the incoming request
			System.out.println("Request => " + request);
			// llama al servicio para el cifrado de la cadena
			String cipher = idmService.encrypt(request);
			//returna la cadena cifrada
			return cipher;
			
		};
		
		@RequestMapping(value = "/descifrar", method = RequestMethod.POST)
		public String descifrar(@RequestBody String request) throws Exception {
			// print the incoming request
			System.out.println("Request => " + request);
			// llama al servicio para el descifrar la cadena
			String decipher = idmService.decrypt(request);
			//returna la cadena cifrada
			return decipher;
			
		};
		
		
		

		
	

}
