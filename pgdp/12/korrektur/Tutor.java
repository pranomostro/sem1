public class Tutor extends Thread {
	private int task;
	private Buffer in, out;

	public Tutor(int taskn, Buffer bin, Buffer bout) {
		task=taskn;
		in=bin;
		out=bout;
		MiniJava.writeConsole("New tutor for task " + task + "\n");
	}

	public void run() {
		MiniJava.writeConsole("Started tutor for task " + task + "\n");
	}
}
