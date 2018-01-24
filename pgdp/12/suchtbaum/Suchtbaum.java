public class Suchtbaum<T extends Comparable<T>> {
	private class SuchtbaumElement {
		private T element;
		private SuchtbaumElement left = null;
		private SuchtbaumElement right = null;
		
		/*
		 * Todo
		 */
	}

	private SuchtbaumElement top=null;

	public void insert(T element) throws InterruptedException {
		insertrec(element, top);
	}

	private void insertrec(T element, SuchtbaumElement from) {
		if(element.compareTo(from.element)==0)
			new RuntimeException("tried inserting already existing element in the list\n");
		if(element.compareTo(from.element)>0) {
			if(from.right==null) {
				from.right=element;
				return;
			}
			else
				insertrec(element, from.right);
		} else {
			if(from.left==null) {
				from.left=element;
				return;
			}
			else
				insertrec(element, from.left);
		}
	}

	public boolean contains(T element) throws InterruptedException {
		return containsrec(element, top);
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

	public SuchtbaumElement find(T element) {
		return findrec(element, top);
	}

	public SuchtbaumElement findrec(T element, SuchtbaumElement from) {
		if(from==null)
			return null;
		if(element.compareTo(from.element)==0)
			return from;
		else if(element.compareTo(from.element)>0)
			return findrec(element, from.right);
		else
			return findrec(element, from.left);
	}

	public SuchtbaumElement find_parent(T element) {
		find_parent_rec(element, top);
	}

	public SuchtbaumElement find_parent_rec(T element, SuchtbaumElement from) {
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

	public void remove(T element) throws InterruptedException {
		SuchtbaumElement se=find(t);
		if(se==null)
			return;
		SuchtbaumElement pt=find_parent(element);
		se.element=null;
		pullup(se, pt);
	}

	public void pullup(SuchtbaumElement se, SuchtbaumElement pt) {
		if(se.left==null&se.right=null) {
			if(pt.right==se)
				pt.right=null;
			else
				pt.left=null;
		} else if(se.right==null) {
			se.element=se.left.element;
			if(se.left.right==null&&se.left.left==null) {
				se.left=null;
				return;
			} else {
				pullup(se.left);
			}
		} else if(se.left==null) {
			se.element=se.right.element;
			if(se.right.right==null&&se.right.left==null) {
				se.right=null;
				return;
			} else {
				pullup(se.right);
			}
		} else {
			SuchtbaumElement maxparent=null;
			for(SuchtbaumElement s=se.left; s.right!=null; maxparent=s, s=s.right);
			if(maxparent==null)
				se.left=null;
			else
				maxparent.right=null;
			se.element=s.element;
		}
	}

	@Override
	public String toString() {
		/*
		 * Todo
		 */
	}
}
