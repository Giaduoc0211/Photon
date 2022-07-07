package Photon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class encodePhoton {

	public static final byte[][] RC = { { 1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10 },
			{ 0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11 }, { 2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9 },
			{ 7, 5, 1, 8, 11, 13, 0, 10, 15, 4, 3, 12 }, { 5, 7, 3, 10, 9, 15, 2, 8, 13, 6, 1, 14 }

	};

	public static final byte[][] MixColMatrix = {

			{ 1, 2, 9, 9, 2 }, { 2, 5, 3, 8, 13 }, { 13, 11, 10, 12, 1 }, { 1, 15, 2, 3, 14 }, { 14, 14, 8, 5, 12 }

	};

	public static byte WORDFILTER = ((byte) 1 << 4) - 1;
	public static byte ReductionPoly = 0x3;
	public static String choiseVer = null;
	public static int choise = 0;
	public static byte[][] meseg;
	public static int a = 0;
	public static int r1 = 0;
	public static int n;
	public static byte[][] IV = new byte[a][a];
	public final static int BLOCK_SIZE = (r1 / 4);

	public static final byte[] sbox = { (byte) 99, (byte) 124, (byte) 119, (byte) 123, (byte) 242, (byte) 107,
			(byte) 111, (byte) 197, (byte) 48, (byte) 1, (byte) 103, (byte) 43, (byte) 254, (byte) 215, (byte) 171,
			(byte) 118, (byte) 202, (byte) 130, (byte) 201, (byte) 125, (byte) 250, (byte) 89, (byte) 71, (byte) 240,
			(byte) 173, (byte) 212, (byte) 162, (byte) 175, (byte) 156, (byte) 164, (byte) 114, (byte) 192, (byte) 183,
			(byte) 253, (byte) 147, (byte) 38, (byte) 54, (byte) 63, (byte) 247, (byte) 204, (byte) 52, (byte) 165,
			(byte) 229, (byte) 241, (byte) 113, (byte) 216, (byte) 49, (byte) 21, (byte) 4, (byte) 199, (byte) 35,
			(byte) 195, (byte) 24, (byte) 150, (byte) 5, (byte) 154, (byte) 7, (byte) 18, (byte) 128, (byte) 226,
			(byte) 235, (byte) 39, (byte) 178, (byte) 117, (byte) 9, (byte) 131, (byte) 44, (byte) 26, (byte) 27,
			(byte) 110, (byte) 90, (byte) 160, (byte) 82, (byte) 59, (byte) 214, (byte) 179, (byte) 41, (byte) 227,
			(byte) 47, (byte) 132, (byte) 83, (byte) 209, (byte) 0, (byte) 237, (byte) 32, (byte) 252, (byte) 177,
			(byte) 91, (byte) 106, (byte) 203, (byte) 190, (byte) 57, (byte) 74, (byte) 76, (byte) 88, (byte) 207,
			(byte) 208, (byte) 239, (byte) 170, (byte) 251, (byte) 67, (byte) 77, (byte) 51, (byte) 133, (byte) 69,
			(byte) 249, (byte) 2, (byte) 127, (byte) 80, (byte) 60, (byte) 159, (byte) 168, (byte) 81, (byte) 163,
			(byte) 64, (byte) 143, (byte) 146, (byte) 157, (byte) 56, (byte) 245, (byte) 188, (byte) 182, (byte) 218,
			(byte) 33, (byte) 16, (byte) 255, (byte) 243, (byte) 210, (byte) 205, (byte) 12, (byte) 19, (byte) 236,
			(byte) 95, (byte) 151, (byte) 68, (byte) 23, (byte) 196, (byte) 167, (byte) 126, (byte) 61, (byte) 100,
			(byte) 93, (byte) 25, (byte) 115, (byte) 96, (byte) 129, (byte) 79, (byte) 220, (byte) 34, (byte) 42,
			(byte) 144, (byte) 136, (byte) 70, (byte) 238, (byte) 184, (byte) 20, (byte) 222, (byte) 94, (byte) 11,
			(byte) 219, (byte) 224, (byte) 50, (byte) 58, (byte) 10, (byte) 73, (byte) 6, (byte) 36, (byte) 92,
			(byte) 194, (byte) 211, (byte) 172, (byte) 98, (byte) 145, (byte) 149, (byte) 228, (byte) 121, (byte) 231,
			(byte) 200, (byte) 55, (byte) 109, (byte) 141, (byte) 213, (byte) 78, (byte) 169, (byte) 108, (byte) 86,
			(byte) 244, (byte) 234, (byte) 101, (byte) 122, (byte) 174, (byte) 8, (byte) 186, (byte) 120, (byte) 37,
			(byte) 46, (byte) 28, (byte) 166, (byte) 180, (byte) 198, (byte) 232, (byte) 221, (byte) 116, (byte) 31,
			(byte) 75, (byte) 189, (byte) 139, (byte) 138, (byte) 112, (byte) 62, (byte) 181, (byte) 102, (byte) 72,
			(byte) 3, (byte) 246, (byte) 14, (byte) 97, (byte) 53, (byte) 87, (byte) 185, (byte) 134, (byte) 193,
			(byte) 29, (byte) 158, (byte) 225, (byte) 248, (byte) 152, (byte) 17, (byte) 105, (byte) 217, (byte) 142,
			(byte) 148, (byte) 155, (byte) 30, (byte) 135, (byte) 233, (byte) 206, (byte) 85, (byte) 40, (byte) 223,
			(byte) 140, (byte) 161, (byte) 137, (byte) 13, (byte) 191, (byte) 230, (byte) 66, (byte) 104, (byte) 65,
			(byte) 153, (byte) 45, (byte) 15, (byte) 176, (byte) 84, (byte) 187, (byte) 22, };
	static byte sbox2[] = { 12, 5, 6, 11, 9, 0, 10, 13, 3, 14, 15, 8, 4, 7, 1, 2 };

	public static int S = 5;

	public static void AddKey(byte state[][], int round) {
		int i;
		for (i = 0; i < a; i++)
			state[i][0] ^= RC(choiseVer)[i][round];
	}

	public static void SubCell(byte state[][]) {
		int i, j;
		for (i = 0; i < a; i++)
			for (j = 0; j < a; j++)
				state[i][j] = sbox2[state[i][j]];
	}

	public static void ShiftRow(byte state[][]) {
		int i, j;
		byte tmp[] = new byte[a];
		for (i = 1; i < a; i++) {
			for (j = 0; j < a; j++)
				tmp[j] = state[i][j];
			for (j = 0; j < a; j++)
				state[i][j] = tmp[(j + i) % a];
		}
	}

	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static void menu() {
		System.out.println(" Versions of photons: ");
		System.out.println("1. PHOTON80 ");
		System.out.println("2. PHOTON128 ");
		System.out.println("3. PHOTON160 ");
		System.out.println("4. PHOTON224 ");
	}

	public static String yourChoise() {
		Scanner sc = new Scanner(System.in);
		System.out.print(" Choise version photon: ");
		while (true) {
			try {
				choise = Integer.parseInt(sc.nextLine());
				if (choise >= 1 && choise <= 4) {
					switch (choise) {
					case 1:
						choiseVer = "Photon80";
						a = 5;
						r1 = 16;
						n = 80;
						IV = new byte[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1 },
								{ 4, 1, 4, 1, 0 } };
						break;
					case 2:
						choiseVer = "Photon128";
						a = 6;
						r1 = 16;
						n = 128;
						IV = new byte[][] { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 2, 0, 1, 0, 1, 0 } };

						break;
					case 3:
						choiseVer = "Photon160";
						a = 7;
						r1 = 36;
						n = 160;
						IV = new byte[][] { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 2, 8, 2, 4, 2, 4 } };

						break;
					case 4:
						choiseVer = "Photon224";
						a = 8;
						r1 = 32;
						n = 224;
						IV = new byte[][] { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 8, 2, 0, 2, 0 } };

						break;
					}
					break;
				}

				else
					System.out.println("choise within 1-4, please try again");
			} catch (Exception e) {
				System.out.println(" Please try again");
			}
		}
		return choiseVer;

	}

	public static byte[][] MixColMatrix(String namePhoton) {

		byte[][] matrix = new byte[5][5];
		System.out.println();

		switch (choiseVer) {
		case "Photon80":
			matrix = new byte[][] { { 1, 2, 9, 9, 2 }, { 2, 5, 3, 8, 13 }, { 13, 11, 10, 12, 1 }, { 1, 15, 2, 3, 14 },
					{ 14, 14, 8, 5, 12 } };
			break;
		case "Photon128":
			matrix = new byte[][] { { 1, 2, 8, 5, 8, 2 }, { 2, 5, 1, 2, 6, 12 }, { 12, 9, 15, 8, 8, 13 },
					{ 13, 5, 11, 3, 10, 1 }, { 1, 15, 13, 14, 11, 8 }, { 8, 2, 3, 3, 2, 8 } };
			break;
		case "Photon160":
			matrix = new byte[][] { { 1, 4, 6, 1, 1, 6, 4 }, { 4, 2, 15, 2, 5, 10, 5 }, { 5, 3, 15, 10, 7, 8, 13 },
					{ 13, 4, 11, 2, 7, 15, 9 }, { 9, 15, 7, 2, 11, 4, 13 }, { 13, 8, 7, 10, 15, 3, 5 },
					{ 5, 10, 5, 2, 15, 2, 4 } };
			break;
		case "Photon224":
			matrix = new byte[][] { { 2, 4, 2, 11, 2, 8, 5, 6 }, { 12, 9, 8, 13, 7, 7, 5, 2 },
					{ 4, 4, 13, 13, 9, 4, 13, 9 }, { 1, 6, 5, 1, 12, 13, 15, 14 }, { 15, 12, 9, 13, 14, 5, 14, 13 },
					{ 9, 14, 5, 15, 4, 12, 9, 6 }, { 12, 2, 2, 10, 3, 1, 1, 14 }, { 15, 1, 13, 10, 5, 10, 2, 3 } };
			break;

		}
		return matrix;
	}

	public static byte[][] RC(String namePhoton) {

		byte[][] RC = new byte[a][12];
		System.out.println();

		switch (choiseVer) {
		case "Photon80":
			RC = new byte[][] { { 1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10 },
					{ 0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11 }, { 2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9 },
					{ 7, 5, 1, 8, 11, 13, 0, 10, 15, 4, 3, 12 }, { 5, 7, 3, 10, 9, 15, 2, 8, 13, 6, 1, 14 } };
			break;
		case "Photon128":
			RC = new byte[][] { { 1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10 },
					{ 0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11 }, { 2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9 },
					{ 6, 4, 0, 9, 10, 12, 1, 11, 14, 5, 2, 13 }, { 7, 5, 1, 8, 11, 13, 0, 10, 15, 4, 3, 12 },
					{ 5, 7, 3, 10, 9, 15, 2, 8, 13, 6, 1, 14 } };
			break;
		case "Photon160":
			RC = new byte[][] { { 1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10 },
					{ 0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11 }, { 3, 1, 5, 12, 15, 9, 4, 14, 11, 0, 7, 8 },
					{ 4, 6, 2, 11, 8, 14, 3, 9, 12, 7, 0, 15 }, { 2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9 },
					{ 7, 5, 1, 8, 11, 13, 0, 10, 15, 4, 3, 12 }, { 5, 7, 3, 10, 9, 15, 2, 8, 13, 6, 1, 14 } };
			break;
		case "Photon224":
			RC = new byte[][] { { 1, 3, 7, 14, 13, 11, 6, 12, 9, 2, 5, 10 },
					{ 0, 2, 6, 15, 12, 10, 7, 13, 8, 3, 4, 11 }, { 2, 0, 4, 13, 14, 8, 5, 15, 10, 1, 6, 9 },
					{ 6, 4, 0, 9, 10, 12, 1, 11, 14, 5, 2, 13 }, { 14, 12, 8, 1, 2, 4, 9, 3, 6, 13, 10, 5 },
					{ 15, 13, 9, 0, 3, 5, 8, 2, 7, 12, 11, 4 }, { 13, 15, 11, 2, 1, 7, 10, 0, 5, 14, 9, 6 },
					{ 9, 11, 15, 6, 5, 3, 14, 4, 1, 10, 13, 2 } };
			break;

		}
		return RC;
	}

	public static byte FieldMult(byte a, byte b) {
		byte x = a, ret = 0;
		int i;
		for (i = 0; i < 4; i++) {
			if (((b >> i) & 1) == 1)
				ret ^= x;
			if (((x >> (4 - 1)) & 1) == 1) {
				x <<= 1;
				x ^= ReductionPoly;
			} else
				x <<= 1;
		}
		return (byte) (ret & WORDFILTER);
	}

	public void MixColumn(byte state[][]) {
		int i, j, k;
		byte tmp[] = new byte[a];
		for (j = 0; j < a; j++) {
			for (i = 0; i < a; i++) {
				byte sum = 0;
				for (k = 0; k < a; k++)

					sum ^= FieldMult(MixColMatrix(choiseVer)[i][k], state[k][j]);
				tmp[i] = sum;
			}
			for (i = 0; i < a; i++)
				state[i][j] = tmp[i];
		}
	}

	public static byte[][] xor(byte[][] data1, byte[][] data2) {
		// make data2 the largest...
		if (data1.length > data2.length) {
			byte[][] tmp = data2;
			data2 = data1;
			data1 = tmp;
		}
		for (int i = 0; i < data1.length; i++) {
			int p = i * 5;

		}
		return data2;
	}

	private static String toHex(byte data) {
		StringBuilder sb = new StringBuilder();
		byte b = data;
		sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}

	public void printMatrix2(byte matrix[][], int col, int row) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.println("Matrix[" + i + "][" + j + "] = " + toHex(matrix[i][j]));
			}
		}
	}

	public void printMatrix1(byte matrix[]) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.println("Matrix[" + i + "]= " + toHex(matrix[i]));
		}
	}

	public byte[] convert2ArrayTo1Array(byte matrix[][], int col, int row, int length) {

		byte array[] = new byte[length];

		int i, j, k = 0;
		for (i = 0; i < row; i++) {
			for (j = 0; j < col; j++) {
				k = i * col + j;
				array[k] = matrix[i][j];
				k++;
			}
		}
		return array;
	}

	public byte[][] convert1ArrayTo2Array(byte matrix[], int col, int row) {

		byte array[][] = new byte[a][a];

		int i, j, k = 0;
		for (i = 0; i < row; i++) {
			for (j = 0; j < col; j++) {
				k = i * col + j;
				array[i][j] = matrix[k];
				k++;
			}
		}
		return array;
	}

	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	public byte[][] permute(byte[][] meseg) {

		for (int i = 0; i < 12; i++) {
//			xorMeseg(meseg, IV);
			AddKey(meseg, i);
			SubCell(meseg);
			ShiftRow(meseg);
			MixColumn(meseg);
//			IV= meseg;
		}
		return meseg;

	}

	private static byte[] concatByteArrays(byte[] a, byte[] b) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(a);
			outputStream.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] concatResult = outputStream.toByteArray();
		return concatResult;
	}

	public void squee(byte[][] meseg, int col, int row) {
		byte newMeseg[][] = new byte[a][a];
		int z = n / r1;
		int k = 0;
		int j = 0;
		int x = r1 / 4;
		int c = 0, i = 0;
		byte[] zSquee = new byte[100];
		for (i = 0; i < z; i++) {
			for (k = 0; k < row; k++) {
				for (j = 0; j < col; j++) {
					newMeseg[k][j] = meseg[k][j];
					if (j == col - 1) {
						row = (x % col);
						c = row + col;
					} else
						col = x;
				}

			}

			byte[] meseg1 = convert2ArrayTo1Array(newMeseg, j, k, newMeseg.length);
//			System.out.println("BBUG: "+ toHex(meseg1));
			String sepSymb = "||";
			if (i == 0) {
				zSquee = concatByteArrays(meseg1, hexStringToByteArray((sepSymb)));
			}
//			System.out.println("BUG1: "+ toHex(zSquee));
			zSquee = concatByteArrays(zSquee, meseg1);
			zSquee = concatByteArrays(zSquee, hexStringToByteArray((sepSymb)));

			meseg = permute(meseg);
			row = a;
			col = a;

		}
		System.out.println("CHECK BUG: " + toHex(zSquee));

	}

	public byte[][] xorMeseg(byte[] mese, byte[] IV) {
//		byte[] d = new byte[mese.length + c.length/4];

		System.out.println("LENGTH Mese: " + mese.length);
		System.out.println("LENGTH IV: " + IV.length);
		if (IV.length == mese.length) {
			for (int i = 0; i < mese.length; i++) {
				mese[i] = (byte) (mese[i] ^ IV[i]);
			}
		}
		return convert1ArrayTo2Array(mese, a, a);
	}

	public void encodePhoton(byte[][] state, byte[][] hash) {

		encodePhoton p = new encodePhoton();

		byte mese[][] = { { 0, 0, 0, 0, 0 } };
		byte mese2[] = convert2ArrayTo1Array(mese, a, mese.length, mese.length * 5 + n / 4);
		byte IV2[] = convert2ArrayTo1Array(IV, a, a, IV.length * 5);

		state = p.permute(xorMeseg(mese2, IV2));
//		System.out.println("CHECKCEHCK: " + state.length);
//		p.squee((state), a, a);
//		p.printMatrix2((state), a, a);
	}

	public boolean hashPhoton(byte[][] plaintext, byte[][] hash) {
		System.out.println("\n\nprocess hashPhoton!");
		if (plaintext == null || hash == null || hash.length > plaintext.length) // invalid input(s)
			return false;
		int extrabytes = plaintext.length % BLOCK_SIZE;
		int pblock = plaintext.length / BLOCK_SIZE;
		byte[][] text = new byte[plaintext.length][BLOCK_SIZE];
		int p;

		for (int k = 0; k < pblock; ++k) { // Encrypt all possible blocks

			p = k * BLOCK_SIZE;
			System.arraycopy(plaintext, p, text, 0, BLOCK_SIZE);
			encodePhoton(text, text);
			System.out.println("text: "+toHex(convert2ArrayTo1Array(text, a, a, text.length)));
			System.arraycopy(text, 0, hash, p, BLOCK_SIZE);
		}

		if (extrabytes > 0) { // encrypt the left over
			p = pblock * BLOCK_SIZE;
			System.arraycopy(plaintext, p, text, 0, extrabytes);
			for (int z = 0; z < plaintext.length; z++) {
				for (int i = extrabytes; i < BLOCK_SIZE; ++i) {// TODO not sure if there is any faster way in C#
					text[z][i] = 0;
				}
			}
			encodePhoton(text, text);
			System.arraycopy(text, 0, hash, p, extrabytes);
		}
		return true;
	}

//
//	public final static byte BYTE_POLY_REDUCTION = 0x3; // 0x1b
//
//	private static byte gM2(byte val) { // used for GaloisMult with 2 equal shift left 1 bit and xor with
//										// condition
//		byte polyRed = BYTE_POLY_REDUCTION;
//		return ((val & 0xff) >= 128) ? (byte) ((val << 1) ^ (polyRed)) : (byte) (val << 1); // compare with 128 to check
//																							// MSB of value =1?
////		return (val >= (byte) 128 ? (byte) ((val << 1) ^ (polyRed)) : (byte) (val << 1));
//	}
//
//	private static byte gM4(byte val) { // used for GaloisMult with 2 equal shift left 1 bit and xor with
//		// condition
//		byte polyRed = BYTE_POLY_REDUCTION;
//		return ((val & 0xff) >= 128) ? (byte) ((val << 2) ^ (polyRed)) : (byte) (val << 2); // compare with 128 to check
//		// MSB of value =1?
////return (val >= (byte) 128 ? (byte) ((val << 1) ^ (polyRed)) : (byte) (val << 1));
//	}
//
//	private static byte gM8(byte val) { // used for GaloisMult with 2 equal shift left 1 bit and xor with
//		// condition
//		byte polyRed = BYTE_POLY_REDUCTION;
//		return ((val & 0xff) >= 128) ? (byte) ((val << 3) ^ (polyRed)) : (byte) (val << 3); // compare with 128 to check
//		// MSB of value =1?
////return (val >= (byte) 128 ? (byte) ((val << 1) ^ (polyRed)) : (byte) (val << 1));
//	}

//	private void MixColumnTest(byte[] t) {
//		byte[] temp = new byte[5];
//		int p;
//		for (int i = 0; i < 5; ++i) {
//			p = i * 5;
//			temp[0] = (byte) ((t[p]) ^ (gM2(t[p + 1])) ^ (gM8(t[p + 2]) ^ t[p + 2]) ^ (gM8(t[p + 3]) ^ t[p + 3])
//					^ (gM2(t[p + 4])));
//
//			temp[1] = (byte) ((gM2(t[p])) ^ (gM4(t[p + 1]) ^ t[p + 1]) ^ (gM2(t[p + 2]) ^ t[p + 2]) ^ (gM8(t[p + 3]))
//					^ (gM8(t[p + 4]) ^ gM4(t[p + 4]) ^ t[p + 4]));
//
//			temp[2] = (byte) ((gM8(t[p]) ^ gM4(t[p]) ^ t[p]) ^ (gM8(t[p + 1]) ^ gM2(t[p + 1]) ^ t[p + 1])
//					^ (gM8(t[p + 2]) ^ gM2(t[p + 2])) ^ (gM8(t[p + 3]) ^ gM2(t[p + 3])) ^ (t[p + 4]));
//
//			temp[3] = (byte) ((t[p]) ^ (gM8(t[p + 1]) ^ gM4(t[p + 1]) ^ gM2(t[p + 1]) ^ t[p + 1]) ^ (gM2(t[p + 2]))
//					^ (gM2(t[p + 3]) ^ t[p + 3]) ^ (gM8(t[p + 4]) ^ gM4(t[p + 4]) ^ gM2(t[p + 4])));
//
//			temp[4] = (byte) ((gM8(t[p]) ^ gM4(t[p]) ^ gM2(t[p])) ^ (gM8(t[p + 1]) ^ gM4(t[p + 1]) ^ gM2(t[p + 1]))
//					^ (gM8(t[p + 2])) ^ (gM4(t[p + 3]) ^ t[p + 3]) ^ (gM8(t[p + 4]) ^ gM4(t[p + 4])));
//			System.arraycopy(temp, 0, t, p, 5);
////			System.out.println(" temp: " + toHex(temp[0]));
//		}
//	}

}
