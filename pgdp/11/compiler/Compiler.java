public class Compiler {
	public static int[] compile(String code) {
		Parser p=new Parser(code);
		Program prog=Parser.parse();
		Visitor v=new Visitor();
		prog.accept(v);
		return v.getProgram();
	}
}
