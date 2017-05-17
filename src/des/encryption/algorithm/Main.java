package des.encryption.algorithm;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String ciphertext = "", s1 = "", s2 = "", s;
		System.out
				.println("Please input the keyword(the keyword's size should be less than eight):");

		String keyWord = sc.nextLine();
		while (keyWord.length() > 8) {
			System.out
					.println("The keyword's size is more than nine,please input the keyword again:");
			keyWord = sc.nextLine();
		}
		System.out
				.println("Please input the message that will be encrypted(the message's size should be less than nine):");

		String message = sc.nextLine();
		while (message.length() > 8) {
			System.out
					.println("The message's size is more than nine,please input the message again:");
			message = sc.nextLine();
		}
		// Create round key
		String[] roundKey = new String[16];
		CreateRoundKey cr = new CreateRoundKey();
		roundKey = cr.checkParity(keyWord);
		EncryptionData ed = new EncryptionData(roundKey);
		

		// Encrypte
		s = ed.rangeMessageByInitialPermutation(message);
		ciphertext = getCiphertextOrMessage(s, s1, s2, ciphertext);
		System.out.println("    The Ciphertext:\n        " + ciphertext);
		// Decrypte
		DecryptionData dd = new DecryptionData(roundKey);
		s = dd.rangeCiphertextByInitialPermutation(s);
		ciphertext = getCiphertextOrMessage(s, s1, s2, ciphertext);

		System.out.println("    The Message's hesString:\n        "
				+ ciphertext);
		System.out.print("    The Message:\n        ");
		for (int i = 0; i < ciphertext.length(); i += 2) {
			if (i != ciphertext.length() - 3) {
				if (Integer.parseInt(ciphertext.substring(i, i + 2), 16) != 0) {
					System.out.print((char) Integer.parseInt(
							ciphertext.substring(i, i + 2), 16));
				}
			} else {
				if (Integer.parseInt(ciphertext.substring(i), 16) != 0) {
					System.out.print((char) Integer.parseInt(
							ciphertext.substring(i), 16));
				}
			}
		}

	}

	private static String getCiphertextOrMessage(String s, String s1,
			String s2, String ciphertext) {
		s2 = s.substring(32);
		s1 = s.substring(0, 32);
		s1 = Long.toHexString(Long.parseLong(s1, 2));
		while (s1.length() < 8) {
			s1 = '0' + s1;
		}
		s2 = Long.toHexString(Long.parseLong(s2, 2));
		while (s2.length() < 8) {
			s2 = '0' + s2;
		}
		ciphertext = s1 + s2;
		return ciphertext;
	}
}
