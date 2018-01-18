public class Test {
	public static void main(String[] args) {
		FormatVisitor visitor=new FormatVisitor();

		Parser p=new Parser("int ggt(int a, int b) { int temp; if (b > a) {   temp = a; a = b; b = temp; }  while (b != 0) {  temp = b; b = a % b;  a = temp; } return a;}int main() { return ggt(56, 23);}");
		Program prog=p.parse();
		visitor.visit(prog);
		MiniJava.writeConsole(visitor.getResult());
	}
}
