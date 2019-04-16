package com.cifrado.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;


public class CifradoAES256  {

	static final String UTF8 = "UTF-8";
	private final String key = "6921C8558A9C7DB26645F83A7898141972C8FCA40DF730F93C882221000000";
	private final String iv = "700000098251C7545AC5F66198A09EF9";


	public String encript(String request)
			throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException {

		try {

			byte[] keyInBytes = StringToByteArray(key);
			byte[] ivInBytes = StringToByteArray(iv);

			IvParameterSpec ips = new IvParameterSpec(ivInBytes);

			// Create a key specification first, based on our key input.
			SecretKey aesKey = new SecretKeySpec(keyInBytes, "AES");

			// Create a Cipher for encrypting the data using the key we created.
			Cipher encryptCipher;

			encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// Initialize the Cipher with key and parameters
			encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey, ips);

			// Our cleartext
			byte[] cleartext = request.getBytes();

			// Encrypt the cleartext
			byte[] ciphertext = encryptCipher.doFinal(cleartext);

			byte[] strObject64 = Base64.encodeBase64(ciphertext);

			return new String(strObject64, UTF8);

		} catch (Exception e) {

			System.out.println("Exception  => " + e);
			return null;

		}

	}

	// C# Method
	private byte[] StringToByteArray(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	};

}
