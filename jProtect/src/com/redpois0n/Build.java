package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Build {

	public static void build(File input, File output, String key) throws Exception {
		File temp = File.createTempFile("jprotect", ".jar");

		byte[] keyBytes;

		if (key.length() == 16) {
			keyBytes = key.getBytes("UTF-8");
		} else {
			keyBytes = getMd5(key);
		}

		FileCrypter.encrypt(keyBytes, input, temp);

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(output));

		ZipFile bin = new ZipFile("Bin.jar");
		Enumeration<? extends ZipEntry> e = bin.entries();

		while (e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();
			out.putNextEntry(entry);
			copy(bin.getInputStream(entry), out);
			out.closeEntry();
		}
		bin.close();

		out.putNextEntry(new ZipEntry("file.dat"));
		FileInputStream in = new FileInputStream(temp);
		copy(in, out);
		in.close();
		out.closeEntry();

		out.close();

		temp.delete();
	}

	public static void copy(InputStream in, OutputStream out) throws Exception {
		byte[] array = new byte[1024];
		int i;

		while ((i = in.read(array)) != -1) {
			out.write(array, 0, i);
		}

	}

	public static byte[] getMd5(String str) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(str.getBytes());
		return md5.digest();
	}

}
