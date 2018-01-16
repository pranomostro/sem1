public class Visitor {
	String res;

	public Visitor() {
		res="";
	}

	public void reset() {
		res="";
	}

	public String getResult() {
		return res;
	}

	public void visit(Binary b) {
		if(b.firstLevelPriority()>b.getLhs().firstLevelPriority()) {
			res+="(";
			b.getLhs().accept(this);
			res+=")";
		} else {
			b.getLhs().accept(this);
		}
		switch(b.getOperator()) {
			case Minus: res+=" - "; break;
			case Plus: res+=" + "; break;
			case MultiplicationOperator: res+=" * "; break;
			case DivisionOperator: res+=" / "; break;
			case Modulo: res+=" % "; break;
			default: break;
		}
		if(b.firstLevelPriority()>b.getRhs().firstLevelPriority()||
		  (b.getOperator()==Binop.Minus&&(b.getRhs() instanceof Binary))||
		  (b.getOperator()==Binop.DivisionOperator&&(b.getRhs() instanceof Binary))) {
			res+="(";
			b.getRhs().accept(this);
			res+=")";
		} else {
			b.getRhs().accept(this);
		}
	}

	public void visit(Unary u) {
		switch(u.getOperator()) {
			case Minus: res+="-"; break;
			default: break;
		}
		if(u.firstLevelPriority()>u.getOperand().firstLevelPriority()) {
			res+="(";
			u.getOperand().accept(this);
			res+=")";
		} else {
			u.getOperand().accept(this);
		}
	}

	public void visit(Call c) {
		int i;
		res+=c.getFunctionName();
		res+="(";
		Expression[] e=c.getArguments();
		for(i=0; i<e.length-1; i++) {
			e[i].accept(this);
			res+=", ";
		}
		e[e.length-1].accept(this);
		res+=")";
	}

	public void visit(BinaryCondition bc) {
		if(bc.firstLevelPriority()>bc.getLhs().firstLevelPriority()) {
			res+="(";
			bc.getLhs().accept(this);
			res+=")";
		} else {
			bc.getLhs().accept(this);
		}
		switch(bc.getOperator()) {
			case And: res+=" && "; break;
			case Or: res+=" || "; break;
			default: break;
		}
		if(bc.firstLevelPriority()>bc.getRhs().firstLevelPriority()) {
			res+="(";
			bc.getRhs().accept(this);
			res+=")";
		} else {
			bc.getRhs().accept(this);
		}
	}

	public void visit(UnaryCondition uc) {
		switch(uc.getOperator()) {
			case Not: res+="!"; break;
			default: break;
		}
		if(uc.firstLevelPriority()>uc.getOperand().firstLevelPriority()) {
			res+="(";
			uc.getOperand().accept(this);
			res+=")";
		} else {
			uc.getOperand().accept(this);
		}
	}

	public void visit(Comparison c) {
		if(c.firstLevelPriority()>c.getLhs().firstLevelPriority()) {
			res+="(";
			c.getLhs().accept(this);
			res+=")";
		} else {
			c.getLhs().accept(this);
		}
		switch(c.getOperator()) {
			case Equals: res+=" == "; break;
			case NotEquals: res+=" != "; break;
			case LessEqual: res+=" <= "; break;
			case Less: res+=" < "; break;
			case GreaterEqual: res+=" >= "; break;
			case Greater: res+=" > "; break;
			default: break;
		}
		if(c.firstLevelPriority()>c.getRhs().firstLevelPriority()) {
			res+="(";
			c.getRhs().accept(this);
			res+=")";
		} else {
			c.getRhs().accept(this);
		}
	}

	public void visit(Number n) {
		res+=n;
	}

	public void visit(Variable v) {
		res+=v;
	}

	public void visit(True t) {
		res+=t;
	}

	public void visit(False f) {
		res+=f;
	}

	public void visit(Assignment a) {}
	public void visit(Composite c) {}
	public void visit(Condition c) {}
	public void visit(Declaration d) {}
	public void visit(Expression e) {}
	public void visit(Function f) {}
	public void visit(IfThen it) {}
	public void visit(IfThenElse ite) {}
	public void visit(Program p) {}
	public void visit(Read r) {}
	public void visit(Return r) {}
	public void visit(Statement s) {}
	public void visit(While w) {}
	public void visit(Write w) {}
}
