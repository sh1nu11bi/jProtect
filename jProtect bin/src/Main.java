import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		String input = JOptionPane.showInputDialog(null,
				"Enter the password of the protected file", "Protected file",
				JOptionPane.QUESTION_MESSAGE);
		if (input == null || input.length() == 0) {
			throw new Exception("Empty key");
		}
		
		byte[] keyBytes;
		
		if (input.length() == 16) {
			keyBytes = input.getBytes("UTF-8");
		} else {
			keyBytes = getMd5(input);
		}
		

		JFileChooser c = new JFileChooser();
		c.showSaveDialog(null);
		File output = c.getSelectedFile();
		if (output == null) {
			throw new Exception("No file selected");
		}

		InputStream in = Main.class.getResourceAsStream("/file.dat");

		Cipher dcipher = Cipher.getInstance("AES");
		Key key = new SecretKeySpec(keyBytes, "AES");
		dcipher.init(Cipher.DECRYPT_MODE, key);

		CipherInputStream cis = new CipherInputStream(in, dcipher);
		FileOutputStream out = new FileOutputStream(output);

		int i;
		byte[] array = new byte[1024];
		while ((i = cis.read(array)) != -1) {
			out.write(array, 0, i);
		}

		out.close();
		cis.close();
	}

	public static byte[] getMd5(String str) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(str.getBytes());
		return md5.digest();
	}

}
