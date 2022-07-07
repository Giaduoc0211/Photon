package Photon;

import java.io.IOException;

public class photonV1 {
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}


	public static void main(String[] args) throws IOException {
		String b= "bc62dd68954e4bb60067";
	
		byte[] arr = hexStringToByteArray(b);
		
//		encodePhoton pt = new encodePhoton();
////		byte[] hash = new byte[pt.n];
////		pt.hashPhoton(null, null);
//		pt.menu();
//		pt.yourChoise();
//		pt.encodePhoton(pt.IV);
		System.out.println(" CHECK: "+ (arr[0]));

	}
}
