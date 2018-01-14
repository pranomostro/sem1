public class Test {
	public static void main(String[] args) {
		int[] code=Compiler.compile("int fak(int n, int acc) {\n" +
      "  if(n == 0)\n" +
      "    return acc;\n" +
      "  return fak(n - 1, acc*n);\n" +
      "}\n" +
      "\n" +
      "int main() {\n" +
      "  return fak(12, 1);\n" +
      "}\n");
		MiniJava.writeConsole(Interpreter.programToString(code));
		MiniJava.writeConsole("----------------------------\n");
		TailCallOptimization.optimize(code);
		MiniJava.writeConsole(Interpreter.programToString(code));
		MiniJava.writeConsole("----------------------------\n");
		MiniJava.writeConsole(Interpreter.execute(code) + "\n");
	}
}
