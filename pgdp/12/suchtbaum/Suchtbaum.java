public class Suchtbaum<T extends Comparable<T>> {
	private class SuchtbaumElement {
		private T element;
		private SuchtbaumElement left = null;
		private SuchtbaumElement right = null;
	}

	private SuchtbaumElement top=null;
	private RW rw;

	public Suchtbaum() {
		rw=new RW();
	}

	public void insert(T element) throws InterruptedException {
		rw.startWrite();
		insertrec(element, top);
		rw.endWrite();
	}

	public boolean contains(T element) throws InterruptedException {
		rw.startRead();
		boolean b=containsrec(element, top);
		rw.endRead();
		return b;
	}

	public void remove(T element) throws InterruptedException {
		SuchtbaumElement se=find(element);
		if(se==null)
			return;
		SuchtbaumElement pt=find_parent(element);
		rw.startWrite();
		rearrange(se, pt);
		rw.endWrite();
	}

	public String toString() {
		return tswrap();
	}

	private String tswrap() {
		String res;
		try {
			rw.startRead();
			res="digraph G {\n";
			res+=toString_rec(top);
			res+="}\n";
			rw.endRead();
		} catch(InterruptedException ie) {
			res="";
		}

		return res;
	}

	private SuchtbaumElement find(T element) {
		return findrec(element, top);
	}

	private SuchtbaumElement findrec(T element, SuchtbaumElement from) {
		if(from==null)
			return null;
		if(element.compareTo(from.element)==0)
			return from;
		else if(element.compareTo(from.element)>0)
			return findrec(element, from.right);
		else
			return findrec(element, from.left);
	}

	private void insertrec(T element, SuchtbaumElement from) {
		if(from==null) {
			from=new SuchtbaumElement();
			from.element=element;
			top=from;
			return;
		}
		if(element.compareTo(from.element)==0)
			new RuntimeException("tried inserting already existing element in the list\n");
		if(element.compareTo(from.element)>0) {
			if(from.right==null) {
				from.right=new SuchtbaumElement();
				from.right.element=element;
				return;
			}
			else
				insertrec(element, from.right);
		} else {
			if(from.left==null) {
				from.left=new SuchtbaumElement();
				from.left.element=element;
				return;
			}
			else
				insertrec(element, from.left);
		}
	}

	private boolean containsrec(T element, SuchtbaumElement from) {
		if(from==null)
			return false;
		if(element.compareTo(from.element)==0)
			return true;
		else if(element.compareTo(from.element)>0)
			return containsrec(element, from.right);
		else
			return containsrec(element, from.left);
	}

	private SuchtbaumElement find_parent(T element) {
		return find_parent_rec(element, top);
	}

	private SuchtbaumElement find_parent_rec(T element, SuchtbaumElement from) {
		if(from==null)
			return null;
		if(from.left!=null&&element.compareTo(from.left.element)==0||
		   from.right!=null&&element.compareTo(from.right.element)==0)
			return from;
		else if(element.compareTo(from.element)>0)
			return find_parent_rec(element, from.right);
		else
			return find_parent_rec(element, from.left);
	}

	/* if pt==null, then se is the top */

	private void rearrange(SuchtbaumElement se, SuchtbaumElement pt) {
		if(se.left==null&&se.right==null) {
			if(pt==null) {
				top=null;
				return;
			}
			if(pt.right==se)
				pt.right=null;
			else
				pt.left=null;
		} else if(se.right==null) {
			if(pt==null) { top=se.left; return; }
			if(pt.right==se) { pt.right=se.left; return; }
			if(pt.left==se) { pt.left=se.left; return; }
		} else if(se.left==null) {
			if(pt==null) { top=se.right; return; }
			if(pt.right==se) { pt.right=se.right; return; }
			if(pt.left==se) { pt.left=se.right; return; }
		} else {
			SuchtbaumElement s, maxparent=null;
			for(s=se.left; s.right!=null; maxparent=s, s=s.right);
			if(maxparent==null)
				se.left=null;
			else
				maxparent.right=null;
			se.element=s.element;
			s.element=null;
		}
	}

	private String toString_rec(SuchtbaumElement se) {
		if(se==null)
			return "";
		String res="\t" + se.element + ";\n";
		if(se.right!=null) {
			res+="\t" + se.element + " -> " + se.right.element + " [label=right]\n";
			res+=toString_rec(se.right);
		}
		if(se.left!=null) {
			res+="\t" + se.element + " -> " + se.left.element + " [label=left];\n";
			res+=toString_rec(se.left);
		}
		return res;
	}
}
