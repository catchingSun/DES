package des.encryption.algorithm;

public class CreateRoundKey {

	Variable va = new Variable();
	int leftMoveTimesArrayLenght = va.LeftMoveTimesArray.length;
	int rounds = 0;
	String[] RoundKey = new String[16];

	/*
	 * check the keyword's parity
	 */
	public String[] checkParity(String keyWord) {
		System.out.println("Create the rounded key:");
		StringBuilder sc = new StringBuilder();
		int count = 0;
		String s = "";
		for (int i = 0; i < keyWord.length(); i++) {
			String s1 = Integer.toBinaryString((int) keyWord.charAt(i));
			while (s1.length() < 8) {
				s1 = '0' + s1;
			}
			s = s + s1;
		}

		while (s.length() < 56) {
			s = '0' + s;
		}
		System.out
				.println("    The keyWord's BinaryString:\n" + "        " + s);
		for (int i = 0; i < s.length(); i++) {
			sc.append(s.charAt(i));
			if (s.charAt(i) == '1') { // get the number of one
				count++;
			}
			if ((i + 1) % 7 == 0) {
				if (count % 2 == 1) {
					sc.append(0);
				} else {
					sc.append(1);
				}
				count = 0;
			}
		}
		System.out.println("    checkParity:\n" + "        " + sc);
		return rangeKeywordByKeyPermutation(sc.toString());
	}

	/*
	 * range the key according to the Key Permutation (KPArray)
	 */
	private String[] rangeKeywordByKeyPermutation(String keyWord) {
		int kpLength = va.KPArray.length;
		StringBuilder sc = new StringBuilder();
		for (int i = 0; i < kpLength; i++) {
			sc.append(keyWord.charAt(va.KPArray[i] - 1));
		}
		System.out.println("    The ranged keyWord:\n" + "        " + sc);
		String kl = sc.toString().substring(0, 28);
		String kr = sc.toString().substring(28);
		System.out.println("    Kl0:\n" + "        " + kl + "\n    Kr0:\n"
				+ "        " + kr);
		return rotateLeft(kl, kr);
	}

	/*
	 * made kl and kr rotate left according to the LeftMoveTimesArray
	 */
	private String[] rotateLeft(String kl, String kr) {
		StringBuilder sckl = new StringBuilder();
		StringBuilder sckr = new StringBuilder();
		System.out.println("    RoundKey:");
		for (int i = 0; i < leftMoveTimesArrayLenght; i++) {
			if (rounds == i) {
				sckl.append(kl.substring(0, va.LeftMoveTimesArray[i]));
				sckr.append(kr.substring(0, va.LeftMoveTimesArray[i]));
				kl = kl.substring(va.LeftMoveTimesArray[i]) + sckl.toString();
				sckl.delete(0, sckl.length());
				kr = kr.substring(va.LeftMoveTimesArray[i]) + sckr.toString();
				sckr.delete(0, sckr.length());
			}
			RoundKey[rounds] = rangeKeywordByCompressionPermutation(kl + kr);
			System.out.println("        "
					+ Long.toHexString(Long.parseLong(RoundKey[rounds], 2)));
			rounds++;
		}
		return RoundKey;
	}

	/*
	 * range keyword according to the Compression Permutation (CPArray)
	 */
	private String rangeKeywordByCompressionPermutation(String keyWord) {
		int cpLength = va.CPArray.length;
		StringBuilder sc = new StringBuilder();
		for (int i = 0; i < cpLength; i++) {
			sc.append(keyWord.charAt(va.CPArray[i] - 1));
		}
		return sc.toString();
	}

}
