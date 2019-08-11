import java.util.Iterator;

public class Student implements Student_ {
	String name;
	String entryNo;
	String hostel;
	String department;
	String completedCredits;
	String cgpa;
	List<CourseGrade> studentCourse;
	
	public Student(String name, String entryNo, String hostel, String department) {
		this.name = name;
		this.entryNo = entryNo;
		this.hostel = hostel;
		this.department = department;
		studentCourse = new List<CourseGrade>();
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String entryNo() {
		return this.entryNo;
	}

	@Override
	public String hostel() {
		return this.hostel;
	}

	@Override
	public String department() {
		return this.department;
	}

	@Override
	public String completedCredits() {
		Iterator<CourseGrade_> studentCourseItr = new courseItr(this);
		int courseCount = 0;
		while(studentCourseItr.hasNext()) {
			CourseGrade cG = (CourseGrade) studentCourseItr.next();
			if((((GradeInfo) cG.grade()).letterGrade() != GradeInfo_.LetterGrade.I) && (((GradeInfo) cG.grade()).letterGrade() != GradeInfo_.LetterGrade.E) && (((GradeInfo) cG.grade()).letterGrade() != GradeInfo_.LetterGrade.F)) {
				courseCount++;
			}
		}
		int creditInt = courseCount*3;
		Integer integerInstance = new Integer(creditInt);
		completedCredits = integerInstance.toString();
		return completedCredits;
	}
	
	public void addCourse(CourseGrade course) {
		studentCourse.add(course);
	}

	@Override
	public String cgpa() {
		Iterator<CourseGrade_> studentCourseItr = new courseItr(this);
		int courseCount = 0;
		double sum =  0.0;
		while(studentCourseItr.hasNext()) {
			CourseGrade cG = (CourseGrade) studentCourseItr.next();
			if(((GradeInfo) cG.grade()).letterGrade() != GradeInfo_.LetterGrade.I) {
				sum = sum + ((GradeInfo) cG.grade()).gradePoint();
				courseCount++;
			}
		}
		double cgpaDouble = sum / courseCount;
		double res = ((int) ((cgpaDouble*100.0) + 0.5))/100.0;
		Double doubleInstance = new Double(res);      
		cgpa = doubleInstance.toString();
		return cgpa;
	}

	@Override
	public Iterator<CourseGrade_> courseList() {
		return new courseItr(this);
	}
	
	class courseItr implements Iterator<CourseGrade_> {

		Node<CourseGrade> curr;
		public courseItr(Student student) {
			curr = student.studentCourse.head();
		}

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public CourseGrade_ next() {
			CourseGrade data = curr.value();
			curr = (Node<CourseGrade>) curr.after();
			return data;
		}
		
	}

}
