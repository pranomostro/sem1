public class MyTest {
	public static void main(String[] args) {
		Visitor c=new Visitor();

		String code="int main() {\n" +
        "  int[] arr;\n" +
        "  int i, n, sum;\n" +
        "  n = 5;\n" +
        "  arr = new int[n];\n" +
        "  i = 0;\n" +
        "  while(i < n) {\n" +
        "    arr[i] = 2*i;\n" +
        "    i = i + 1;\n" +
        "  }\n" +
        "  sum = 0;\n" +
        "  i = 0;\n" +
        "  while(i < n) {\n" +
        "    sum = sum + arr[i];\n" +
        "    i = i + 1;\n" +
        "  }\n" +
        "  return sum;\n" +
        "}";

		Parser p=new Parser(code);
		Program prog=p.parse();

		c.visit(prog);
	}
}
