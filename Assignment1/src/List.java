import java.util.Iterator;

public class List<T> implements LinkedList_<T> {

	Node<T> head;
	Node<T> tail;
	private int nodeCount;
	
	public List(){
		head = null;
		tail = null;
		nodeCount = 0;
	}
	
	@Override
	public Position_<T> add(T e) {
		Node<T> newNode = new Node<T>(e);
		if (head == null) {
			head = newNode;
		}
		else {
			tail.next = newNode;
		}
		tail = newNode;
		nodeCount++;
		return newNode;
	}

	@Override
	public Iterator<Position_<T>> positions() {
		return new positionItr(this);
	}

	@Override
	public int count() {
		return nodeCount;
	}

	public Node<T> head() {
		return head;
	}

	public Node<T> tail() {
		return tail;
	}
	
	public class positionItr implements Iterator<Position_<T>> {

		Node<T> curr;
		
		public positionItr(List<T> list) {
			curr = list.head;
		}

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Position_<T> next() {
			Node<T> data = curr;
			curr = (Node<T>) curr.after();
			return data;
		}
		
	}

}
