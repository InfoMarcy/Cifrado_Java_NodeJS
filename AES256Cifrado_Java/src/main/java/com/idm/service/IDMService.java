package com.idm.service;

import com.idm.model.Usuario;

public interface IDMService {

	String bloquearUsuario(Usuario usuario) throws Exception;

	String encrypt(String content) throws Exception;

	String decrypt(String encrypted) throws Exception;

}
