public class GradeInfo implements GradeInfo_ {
	
	LetterGrade letterGrade;
	int gradePoint;
	
	public GradeInfo(String grade) {
		letterGrade = LetterGrade.valueOf(grade);
		gradePoint = GradeInfo_.gradepoint(LetterGrade.valueOf(grade));
	}
	
	public LetterGrade letterGrade() {
		return letterGrade;
	}
	
	public int gradePoint() {
		return gradePoint;
	}
}
