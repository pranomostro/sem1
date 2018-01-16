import java.util.ArrayList;

public class BinnenMajuskel extends MiniJava {
	public static void main(String args[]) {
		int j, k;
		String input;
		ArrayList<String> words=new ArrayList<>();

		for(input=readString(); !input.equals(""); input=readString())
			if(isalpha(input))
				words.add(input);

		writeConsole("Startcase: ");

		for(j=0; j<words.size(); j++)
			for(k=0; k<words.get(j).length(); k++) {
				if(j==0&&k==0)
					writeConsole("" + upper(words.get(j).charAt(k)));
				else
					writeConsole("" + lower(words.get(j).charAt(k)));
			}

		writeConsole("\n");
		writeConsole("UPPERCASE: ");

		for(j=0; j<words.size(); j++)
			for(k=0; k<words.get(j).length(); k++)
				writeConsole("" + upper(words.get(j).charAt(k)));

		writeConsole("\n");
		writeConsole("snake_case: ");

		for(j=0; j<words.size(); j++) {
			for(k=0; k<words.get(j).length(); k++)
				writeConsole("" + lower(words.get(j).charAt(k)));
			if(j<words.size()-1)
				writeConsole("_");
		}

		writeConsole("\n");
		writeConsole("PascalCase: ");

		for(j=0; j<words.size(); j++)
			for(k=0; k<words.get(j).length(); k++) {
				if(k==0)
					writeConsole("" + upper(words.get(j).charAt(k)));
				else
					writeConsole("" + lower(words.get(j).charAt(k)));
			}

		writeConsole("\n");
	}

	private static char lower(char c) {
		if(c>='A'&&c<='Z')
			return (char)((int)c+32);
		return c;
	}

	private static char upper(char c) {
		if(c>='a'&&c<='z')
			return (char)((int)c-32);
		return c;
	}

	private static boolean isalpha(String s) {
		int i;

		for(i=0; i<s.length(); i++)
			if(!((s.charAt(i)>='a'&&s.charAt(i)<='z')||
				(s.charAt(i)>='A'&&s.charAt(i)<='Z')))
				return false;
		return true;
	}
}
