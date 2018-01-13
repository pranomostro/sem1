public class Test {
	public static void main(String[] args) {
//		Parser p=new Parser("int ggt(int a, int b) {\n" +
//        "  return b;\n" +
//        "}\n" +
//        "\n" +
//        "int main() {\n" +
//        "  int a, b, r;\n" +
//        "  a = 3528;\n" +
//        "  b = 3780;\n" +
//        "  r = ggt(a, b, a);\n" +
//        "  return r;\n" +
//        "}");
//		Parser p=new Parser("int ggt(int a, int b) {\n" +
//        "  int a;\n" +
//        "  return b;\n" +
//        "}\n" +
//        "\n" +
//        "int main() {\n" +
//        "  int a, b, r;\n" +
//        "  a = 3528;\n" +
//        "  b = 3780;\n" +
//        "  r = ggt(a, b);\n" +
//        "  return r;\n" +
//        "}");

		Parser p=new Parser("int ggt(int a, int b) {\n" +
        "  int temp;\n" +
        "  if(b > a) {\n" +
        "    temp = a;\n" +
        "    a = b;\n" +
        "    b = temp;\n" +
        "  }\n" +
        "  while(a != 0) {\n" +
        "   temp = a;\n" +
        "   a = a % b;\n" +
        "   b = temp;\n" +
        "  }\n" +
        "  return b;\n" +
        "}\n" +
        "\n" +
        "int main() {\n" +
        "  int a, b, r;\n" +
        "  a = 3780;\n" +
        "  b = 3528;\n" +
        "  r = ggt(a, b);\n" +
        "  return r;\n" +
        "}");

		Program prog=Parser.parse();
		Visitor v=new Visitor();
		prog.accept(v);
		MiniJava.writeConsole("" + Interpreter.execute(v.getProgram()) + "\n");
	}
}
