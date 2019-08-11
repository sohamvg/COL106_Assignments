import java.util.Iterator;

public class Department implements Entity_ {

	String name;
	List<Student> departmentStudents;
	
	public Department(String name) {
		this.name = name;
		departmentStudents = new List<Student>();
	}
	
	@Override
	public String name() {
		return this.name;
	}
	
	public void addStudent(Student student) {
		departmentStudents.add(student);
	}

	@Override
	public Iterator<Student_> studentList() {
		return new studentItr(this);
	}
	
	class studentItr implements Iterator<Student_> {

		Node<Student> curr;
		
		public studentItr(Department department) {
			curr = department.departmentStudents.head();
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
