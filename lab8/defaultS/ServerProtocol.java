package defaultS;

public class ServerProtocol {

	/*
		Protocol
		id message

	 */
	public String processRequest(String theInput) {
		System.out.println("Received message from client: " + theInput);
		String theOutput = "ERROR";


		String[] temp = theInput.split(" ");

		String id = temp[0];

		if (id.equals("1.1")) {
			String text = temp[1];
			theOutput = convertToLowerCase(text);
		} else if (id.equals("1.2")) {
			String text = temp[1];
			theOutput = convertToUpperCase(text);
		} else if (id.equals("1.3")) {
			String text = temp[1];
			int offset = Integer.parseInt(temp[2]);
			theOutput = encodeCaesarsCipher(text, offset);
		} else if (id.equals("1.4")) {
			String text = temp[1];
			int offset = Integer.parseInt(temp[2]);
			theOutput = decodeCaesarsCipher(text, offset);
		}

		//pcnNKkcnPkcn

		System.out.println("Send message to client: " + theOutput);
		return theOutput;
	}

	public static void main(String[] args) {
		String test ="fsdDAasdFasd";
		int k = 10;
		String str = encodeCaesarsCipher(test, k);
		System.out.println(str);
		System.out.println(decodeCaesarsCipher(str, k));
	}

	public static String convertToLowerCase(String message) {
		StringBuilder lowercaseText = new StringBuilder();

		for (int i = 0; i < message.length(); i++) {
			char currentChar = message.charAt(i);
			if (Character.isUpperCase(currentChar)) {
				lowercaseText.append(Character.toLowerCase(currentChar));
			} else {
				lowercaseText.append(currentChar);
			}
		}

		return lowercaseText.toString();
	}

	public static String convertToUpperCase(String message) {
		StringBuilder uppercaseText = new StringBuilder();

		for (int i = 0; i < message.length(); i++) {
			char currentChar = message.charAt(i);
			if (Character.isLowerCase(currentChar)) {
				uppercaseText.append(Character.toUpperCase(currentChar));
			} else {
				uppercaseText.append(currentChar);
			}
		}

		return uppercaseText.toString();
	}

	public static String encodeCaesarsCipher(String message, int offset) {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
			if (character != ' ') {
				int originalAlphabetPosition = character - 'a';
				int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
				char newCharacter = (char) ('a' + newAlphabetPosition);
				result.append(newCharacter);
			} else {
				result.append(character);
			}
		}

		return result.toString();
	}

	public static String decodeCaesarsCipher(String message, int offset) {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
			if (Character.isLetter(character)) {
				char base = Character.isLowerCase(character) ? 'a' : 'A';
				int originalAlphabetPosition = character - base;
				int newAlphabetPosition = (originalAlphabetPosition - offset + 26) % 26; // Βελτιωμένη εκδοχή
				char newCharacter = (char) (base + newAlphabetPosition);
				result.append(newCharacter);
			} else {
				result.append(character);
			}
		}
		return result.toString();
	}
}