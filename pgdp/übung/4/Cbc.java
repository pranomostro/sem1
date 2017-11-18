public class Cbc extends MiniJava {
	private static int[] dectable={ 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107,
		108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 65,
		66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85,
		86, 87, 88, 89, 90, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 32, 46 };

	public static void main(String args[]) {
		int i, key, iv;
		int[] clear_encoded, cipher_encoded;
		String clear;

		key=readInt();

		while(key<0||key>63)
			key=readInt("Bitte Zahl zwischen 0 und 63 eingeben.");

		iv=readInt();

		while(iv<0||iv>63)
			iv=readInt("Bitte Zahl zwischen 0 und 63 eingeben.");

		clear=readString();

		if(!isvalid(clear)) {
			write("Ungültiger Eingabe-String, nur alphabetische und numerische Zeichen sowie '.' und ' ' erlaubt.");
			System.exit(1);
		}

		clear_encoded=encode(clear);
		cipher_encoded=cbc_encrypt(key, iv, clear.length(), clear_encoded);
		writeConsole(decode(cipher_encoded, clear.length()) + "\n");
		writeConsole(decode(cbc_decrypt(key, iv, clear.length(), cipher_encoded), clear.length()) + "\n");
	}

	private static boolean isvalid(String s){
		int i;

		for(i=0; i<s.length(); i++)
			if(!((s.charAt(i)>='a'&&s.charAt(i)<='z')||
                                (s.charAt(i)>='A'&&s.charAt(i)<='Z')||
				(s.charAt(i)>='0'&&s.charAt(i)<='9')||
				s.charAt(i)==' '||s.charAt(i)=='.'))
				return false;
		return true;
	}

	private static int[] encode(String s) {
		int i;
		int[] encoded=new int[s.length()];

		for(i=0; i<s.length(); i++) {
			if(s.charAt(i)>='a'&&s.charAt(i)<='z')
				encoded[i]=(int)(s.charAt(i)-97);
			else if(s.charAt(i)>='A'&&s.charAt(i)<='Z')
				encoded[i]=(int)(s.charAt(i)-39);
			else if(s.charAt(i)>='0'&&s.charAt(i)<='9')
				encoded[i]=(int)(s.charAt(i)+4);
			else if(s.charAt(i)==' ')
				encoded[i]=62;
			else if(s.charAt(i)=='.')
				encoded[i]=63;
		}

		return encoded;
	}

	private static String decode(int[] encoded, int len) {
		int i;
		String s="";

		for(i=0; i<len; i++)
			s=s + (char)(dectable[encoded[i]]);

		return s;
	}

	private static int[] cbc_encrypt(int key, int iv, int len, int[] clear) {
		int i, lastval;

		int[] encrypted=new int[len];
		lastval=iv;

		for(i=0; i<len; i++) {
			encrypted[i]=clear[i]^lastval;
			encrypted[i]=encrypted[i]^key;
			lastval=encrypted[i];
		}

		return encrypted;
	}

	private static int[] cbc_decrypt(int key, int iv, int len, int[] cipher) {
		int i, lastval;

		int[] decrypted=new int[len];
		lastval=iv;

		for(i=0; i<len; i++) {
			decrypted[i]=cipher[i]^key;
			decrypted[i]=decrypted[i]^lastval;
			lastval=cipher[i];
		}

		return decrypted;
	}
}
