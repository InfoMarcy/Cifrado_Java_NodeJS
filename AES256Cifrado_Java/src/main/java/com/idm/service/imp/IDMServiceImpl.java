package com.idm.service.imp;

import java.io.IOException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.model.Usuario;
import com.idm.service.IDMService;


@Service("IDMServiceImpl")
public class IDMServiceImpl implements IDMService{
		
	private final  byte[] iv = "0000000000000000".getBytes();
	private final String seed = "U7teWmkj2Jv7AR5M@UbuTV7XagUw8mdMN.E5RsArNqACDuNn7w";

	@Override
	public  String decrypt(String encrypted) throws Exception {
		byte[] keyb = seed.getBytes("utf-8");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] thedigest = md.digest(keyb);
		SecretKeySpec skey = new SecretKeySpec(thedigest, "AES");
		Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		dcipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv));
 
		byte[] clearbyte = dcipher.doFinal(DatatypeConverter
				.parseHexBinary(encrypted));
		return new String(clearbyte);
	}

	@Override
	public  String encrypt(String content) throws Exception {
		 System.out.println("content => " + content); 
		
		byte[] input = content.getBytes("utf-8");

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] thedigest = md.digest(seed.getBytes("utf-8"));
		SecretKeySpec skc = new SecretKeySpec(thedigest, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skc, new IvParameterSpec(iv));

		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		
		 System.out.println("cifrado => " + DatatypeConverter.printHexBinary(cipherText));
		return DatatypeConverter.printHexBinary(cipherText);
	}

	
	@Override
	public String bloquearUsuario(Usuario usuario) throws Exception {		
		final String uri = "http://10.51.58.238:3000/banca_digital/gitlab/v1/bloquear/usuario";
		RestTemplate restTemplate = new RestTemplate();
	 
		
		// Creating Object of ObjectMapper define in Jakson Api 
        ObjectMapper Obj = new ObjectMapper(); 
        
        
        
        try { 
        	  
            // get object as a json string 
            String user = Obj.writeValueAsString(usuario); 
  
            // Displaying JSON String 
            System.out.println("User => " + user); 
            
        	
    		String cipher = encrypt(user.toString());
    		
    		 System.out.println("cifrado cipher => " + cipher);
    		
    		String decipher  = restTemplate.postForObject( uri, cipher.toString(), String.class);
    

    		System.out.println(decipher);
    		return decipher;
    		
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
            return null;
        } 
	   
	}

}
