import java.util.Iterator;

public class Course implements Entity_ {

	String name;
	String code;
	List<Student> courseStudents;
	
	public Course(String name, String code) {
		this.name = name;
		this.code = code;
		courseStudents = new List<Student>();
	}
	
	@Override
	public String name() {
		return this.name;
	}
	
	public String code() {
		return this.code;
	}
	
	public void addStudent(Student student) {
		courseStudents.add(student);
	}

	@Override
	public Iterator<Student_> studentList() {
		return new studentItr(this);
	}
	
	class studentItr implements Iterator<Student_> {

		Node<Student> curr;
		
		public studentItr(Course course) {
			curr = course.courseStudents.head();
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
