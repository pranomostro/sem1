public class MyTest {
	public static void main(String[] args) {
		String code="int isPalindrome(int n) {\n" +
        "  int[] digits;\n" +
        "  int numberOfDigits, t, i, notMatching, digit;\n" +
        "\n" +
        "  numberOfDigits = 0;\n" +
        "  t = n;\n" +
        "  while (t != 0) {\n" +
        "    numberOfDigits = numberOfDigits + 1;\n" +
        "    t = t / 10;\n" +
        "  }\n" +
        "\n" +
        "  digits = new int[numberOfDigits];\n" +
        "\n" +
        "  i = 0;\n" +
        "  while (n != 0) {\n" +
        "    digit = n % 10;\n" +
        "    digits[i] = digit;\n" +
        "    n = n / 10;\n" +
        "    i = i + 1;\n" +
        "  }\n" +
        "\n" +
        "  notMatching = 0;\n" +
        "  i = 0;\n" +
        "  while (i < numberOfDigits / 2) {\n" +
        "    if (digits[i] != digits[numberOfDigits - i - 1])\n" +
        "      notMatching = notMatching + 1;\n" +
        "    i = i + 1;\n" +
        "  }\n" +
        "\n" +
        "  if (notMatching == 0)\n" +
        "    return 1;\n" +
        "  else\n" +
        "    return 0;\n" +
        "}\n" +
        " \n" +
        "int main() {\n" +
        "  int n;\n" +
        "  n = 0;\n" +
        "  n = n + isPalindrome(4224);\n" +
        "  n = n + isPalindrome(10);\n" +
        "  n = n + isPalindrome(99);\n" +
        "  n = n + isPalindrome(123321);\n" +
        "  n = n + isPalindrome(19910);\n" +
        "  n = n + isPalindrome(0990);\n" +
        "  n = n + isPalindrome(111111);\n" +
        "  n = n + isPalindrome(1112111);\n" +
        "  return n;" +
        "}";

		int[] res=Compiler.compile(code);
		MiniJava.writeConsole(Interpreter.programToString(res));
		MiniJava.writeConsole(Interpreter.execute(res) + "\n");
	}
}
