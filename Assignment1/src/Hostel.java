import java.util.Iterator;

public class Hostel implements Entity_ {

	String name;
	List<Student> hostelStudents;
	
	public Hostel(String name) {
		this.name = name;
		hostelStudents = new List<Student>();
	}
	
	@Override
	public String name() {
		return this.name;
	}
	
	public void addStudent(Student student) {
		hostelStudents.add(student);
	}

	@Override
	public Iterator<Student_> studentList() {
		return new studentItr(this);
	}
	
	class studentItr implements Iterator<Student_> {

		Node<Student> curr;
		
		public studentItr(Hostel hostel) {
			curr = hostel.hostelStudents.head();
		}

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Student next() {
			Student data = curr.value();
			curr = (Node<Student>) curr.after();
			return data;
		}
		
	}
		
}
