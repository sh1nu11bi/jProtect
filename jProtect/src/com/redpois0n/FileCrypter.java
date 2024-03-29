package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class FileCrypter {

	@SuppressWarnings("resource")
	public static void decrypt(byte[] key, File input, File output) throws Exception {
		byte[] buffer = new byte[1024];

		InputStream in = new FileInputStream(input);
		OutputStream out = new FileOutputStream(output);

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));

		in = new CipherInputStream(in, cipher);

		int numRead = 0;
		while ((numRead = in.read(buffer)) >= 0) {
			out.write(buffer, 0, numRead);
		}
		out.close();

	}

	@SuppressWarnings("resource")
	public static void encrypt(byte[] key, File input, File output) throws Exception {
		byte[] buffer = new byte[1024];

		InputStream in = new FileInputStream(input);
		OutputStream out = new FileOutputStream(output);

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));

		out = new CipherOutputStream(out, cipher);

		int numRead = 0;
		while ((numRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, numRead);
		}
		out.close();
	}
}
