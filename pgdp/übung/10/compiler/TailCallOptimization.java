public class TailCallOptimization {
	public static void optimize(int[] program) {
		for(int i=1; i<program.length-1; i++)
			if(program[i-1]>>16==5&&program[i]>>16==14&&program[i+1]>>16==15) {
				int addr=program[i-1]&0xFFFF;
				program[i]=(8<<16|addr);
				program[i-1]=0;
			}
	}
}
