package com.redpois0n;

public class Hex {

	public static String hex(String s) {
		byte[] ba = s.getBytes();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < ba.length; i++) {
			sb.append(Integer.toHexString((ba[i] >> 4) & 0x0F));
			sb.append(Integer.toHexString(ba[i] & 0x0F));
		}
		return sb.toString();
	}

	public static String dehex(String s) throws Exception {
		int n = s.length() / 2;
		byte[] res = new byte[n];
		for (int i = 0; i < n; i++) {
			res[i] = (byte) (Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16));
		}
		return new String(res);
	}

}
