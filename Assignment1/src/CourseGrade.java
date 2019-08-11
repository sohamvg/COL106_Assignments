public class CourseGrade implements CourseGrade_ {

	String courseTitle;
	String courseNum; // course code
	GradeInfo grade;
	
	public CourseGrade(String courseTitle, String courseNum, String grade) {
		this.courseTitle = courseTitle;
		this.courseNum = courseNum;
		this.grade = new GradeInfo(grade);
	}
	
	@Override
	public String coursetitle() {
		return this.courseTitle;
	}

	@Override
	public String coursenum() {
		return this.courseNum;
	}

	@Override
	public GradeInfo_ grade() {
		return this.grade;
	}

}
