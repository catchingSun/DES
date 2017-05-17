package des.encryption.algorithm;

public class DecryptionData {

	Variable va = new Variable();
	String[] roundKey = new String[16];
	int rounds = 1;
	String ciphertext = "";

	/*
	 * Get the rounded key
	 */
	public DecryptionData(String[] roundkey) {
		roundKey = roundkey;
		System.out.println("Decrypte the message:");
	}

	/*
	 * Range the ciphertext according to Initial Permutation (IPArray)
	 */
	public String rangeCiphertextByInitialPermutation(String ciphertext) {
		String s = "";
		s = ciphertext;
		System.out.println("    The ciphertext's BinaryString:\n" + "        "
				+ s);
		s = va.rangeTextByInitialPermutation(s);

		String[] l = new String[17];
		String[] r = new String[17];

		l[0] = s.toString().substring(0, 32);
		r[0] = s.toString().substring(32);
		System.out.println("    The ranged ciphertext:\n" + "        " + s);
		return makeExclusive_or(l, r);
	}

	/*
	 * Do arithmetic on L and R
	 */
	private String makeExclusive_or(String[] l, String[] r) {
		String ciphertext1 = "";
		String ciphertext2 = "";
		System.out.println("    The rounded message:");
		while (rounds < 17) {
			l[rounds] = r[rounds - 1];
			r[rounds] = Long.toBinaryString(Long.parseLong(l[rounds - 1], 2)
					^ Long.parseLong(va.fFunction(r[rounds - 1],
							roundKey[15 - (rounds - 1)]), 2));
			va.outputCiphertext(r, l, ciphertext, ciphertext1, ciphertext2,
					rounds);
			rounds++;
		}
		return va.rangeCiphertextOrMessageByFinalPermutation(r[16] + l[16]);
	}

}
