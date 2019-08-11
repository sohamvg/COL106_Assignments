public class Node<T> implements Position_<T> {

	T value;
	Node<T> next;
	
	public Node(T v) {
		value = v;
		next = null;
	}
	@Override
	public T value() {
		return value;
	}

	@Override
	public Position_<T> after() {
		return next;
	}

}
