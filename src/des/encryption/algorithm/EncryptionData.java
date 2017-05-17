package des.encryption.algorithm;

public class EncryptionData {

	Variable va = new Variable();
	String[] roundKey = new String[16];
	int rounds = 1;
	String ciphertext = "";

	/*
	 * Get the rounded key
	 */
	public EncryptionData(String[] roundkey) {
		roundKey = roundkey;
		System.out.println("Encrypte the message:");
	}

	/*
	 * Range the message according to Initial Permutation (IPArray)
	 */
	public String rangeMessageByInitialPermutation(String message) {
		String s = "";
		for (int i = 0; i < message.length(); i++) {
			String s1 = Integer.toBinaryString((int) message.charAt(i));
			while (s1.length() < 8) {
				s1 = '0' + s1;
			}
			s = s + s1;
		}
		while (s.length() < 64) {
			s = '0' + s;
		}
		System.out
				.println("    The message's BinaryString:\n" + "        " + s);
		s = va.rangeTextByInitialPermutation(s);

		String[] l = new String[17];
		String[] r = new String[17];

		l[0] = s.toString().substring(0, 32);
		r[0] = s.toString().substring(32);
		System.out.println("    The ranged message:\n" + "        " + s);
		return makeExclusive_or(l, r);
	}

	/*
	 * Do arithmetic on L and R
	 */
	private String makeExclusive_or(String[] l, String[] r) {
		String ciphertext1 = "";
		String ciphertext2 = "";
		System.out.println("    The rounded ciphertext:");
		while (rounds < 17) {

			l[rounds] = r[rounds - 1];
			r[rounds] = Long.toBinaryString(Long.parseLong(l[rounds - 1], 2)
					^ Long.parseLong(
							va.fFunction(r[rounds - 1], roundKey[rounds - 1]),
							2));
			va.outputCiphertext(r, l, ciphertext, ciphertext1, ciphertext2,
					rounds);
			rounds++;
		}
		return va.rangeCiphertextOrMessageByFinalPermutation(r[16] + l[16]);
	}

}
