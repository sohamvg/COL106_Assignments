import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Assignment1 {
	
	private static List<Student> allStudents = new List<Student>();
	private static List<Hostel> allHostels = new List<Hostel>();
	private static List<Department> allDepartments = new List<Department>();
	private static List<Course> allCourses = new List<Course>();
	
	private static String[] createNewArray(String[] oldArray){
	    String[] newArray = new String[oldArray.length * 2];
	    for(int i = 0; i < oldArray.length; i++) {
		newArray[i] = oldArray[i];
	    }
	    return newArray;
	}
	
	private static String[] lexiSort(String[] words, int n) {
		for(int i = 0; i < n-1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (words[i].compareTo(words[j]) > 0) {
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
		return words;
	}

	private static void getData(String studentRecordFile, String courseFile) {
		
		BufferedReader reader1;
		try {
			reader1 = new BufferedReader(new FileReader(studentRecordFile));
			String line = reader1.readLine();
			
			while (line != null) {
				String[] words = line.split(" ");
				Student newStudent = new Student(words[1], words[0], words[3], words[2]);
				allStudents.add(newStudent);

				// Hostels
				boolean found1 = false;
				Iterator<Position_<Hostel>> hostelItr = allHostels.positions();
				while(hostelItr.hasNext()) {
					Node<Hostel> h = (Node<Hostel>) hostelItr.next();
					if(h.value().name().equals(words[3])) {
						h.value().addStudent(newStudent);
						found1 = true;
						break;
					}
				}
				if(!found1) {
					allHostels.add(new Hostel(words[3]));
					allHostels.tail().value().addStudent(newStudent);
				}
				
				// Departments
				boolean found2 = false;
				Iterator<Position_<Department>> departmentItr = allDepartments.positions();
				while(departmentItr.hasNext()) {
					Node<Department> d = (Node<Department>) departmentItr.next();
					if(d.value().name().equals(words[2])) {
						d.value().addStudent(newStudent);
						found2 = true;
						break;
					}
				}
				if(!found2) {
					allDepartments.add(new Department(words[2]));
					allDepartments.tail().value().addStudent(newStudent);
				}
				
				// read next line
				line = reader1.readLine();
			}
			reader1.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader2;
		try {
			reader2 = new BufferedReader(new FileReader(courseFile));
			String line = reader2.readLine();
			
			while (line != null) {
				String[] words = line.split(" ", 4);
				Student newStudentReference = new Student("ref ", " ref", "ref", "ref ");
				Iterator<Position_<Student>> studentItr = allStudents.positions();
				while(studentItr.hasNext()) {
					Node<Student> s = (Node<Student>) studentItr.next();
					if(s.value().entryNo().equals(words[0])) {
						newStudentReference = s.value();
						CourseGrade coursegrade = new CourseGrade(words[3], words[1], words[2]);
						newStudentReference.addCourse(coursegrade);
						break;
					}
				}
				
				// Courses
				boolean found = false;
				Iterator<Position_<Course>> courseItr = allCourses.positions();
				while(courseItr.hasNext()) {
					Node<Course> c = (Node<Course>) courseItr.next();
					if(c.value().name().equals(words[3])) {
						c.value().addStudent(newStudentReference);
						found = true;
						break;
					}
				}
				if(!found) {
					allCourses.add(new Course(words[3], words[1]));
					allCourses.tail().value().addStudent(newStudentReference);
				}
				
				// read next line
				line = reader2.readLine();
			}
			reader2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void answerQueries(String queryFile) {
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(queryFile));
			String line = reader.readLine();
			int count = 0;			
			String[] arr = new String[10];
			
			while (line != null) {
				if (arr.length == count) {
				      // expand list
				      arr = createNewArray(arr);
				 }
				 arr[count] = line;
				 count++;
				// read next line
				line = reader.readLine();
			}
			reader.close();
			
			for (int i = arr.length-1; i >= 0; i--) {
				if(arr[i] != null) {
					String[] words = arr[i].split(" ");
					if(words[0].equals("INFO")) {
						Iterator<Position_<Student>> studentItr1 = allStudents.positions();
						while(studentItr1.hasNext()) {
							Node<Student> s = (Node<Student>) studentItr1.next();
							if(s.value().entryNo().equals(words[1])) {
								System.out.print(s.value().entryNo() + " " + s.value().name() + " "+ s.value().department() + " " + s.value().hostel() + " " +s.value().cgpa() + " ");
								Iterator<CourseGrade_> studentCourseItr = s.value().courseList();
								int courseCount = 0;			
								String[] courseArr = new String[4];
								while(studentCourseItr.hasNext()) {
									CourseGrade studentCourse = (CourseGrade) studentCourseItr.next();
									if (courseArr.length == courseCount) {
									      // expand list
									      courseArr = createNewArray(courseArr);
									 }
									courseArr[courseCount] = studentCourse.coursenum();
									courseCount++;
									courseArr = lexiSort(courseArr, courseCount);
								}
								for(int i1 = 0; i1 < courseCount; i1++) {
									if(courseArr[i1] != null) {
										Iterator<CourseGrade_> studentCourseItr1 = s.value().courseList();
										while(studentCourseItr1.hasNext()) {
											CourseGrade studentCourse1 = (CourseGrade) studentCourseItr1.next();
											if(courseArr[i1].equals(studentCourse1.coursenum())) {
												System.out.print(studentCourse1.coursenum() + " " + ((GradeInfo) studentCourse1.grade()).letterGrade() + " ");
												break;
											}
										}
									}
						        }
								System.out.println();
								break;
							}
						}
					}
					else if(words[0].equals("COURSETITLE")) {
						Iterator<Position_<Course>> courseItr1 = allCourses.positions();
						while(courseItr1.hasNext()) {
							Node<Course> c = (Node<Course>) courseItr1.next();
							if(c.value().code().equals(words[1])) {
								System.out.println(c.value().name());
								break;
							}
						}
					}
					else if(words[0].equals("SHARE")) {
						Iterator<Position_<Student>> studentItr2 = allStudents.positions();
						while(studentItr2.hasNext()) {
							Node<Student> s = (Node<Student>) studentItr2.next();
							if(s.value().entryNo().equals(words[1])) {
								
								if(s.value().hostel().equals(words[2])) {// entity is hostel
									Iterator<Position_<Hostel>> hostelItr = allHostels.positions();
									while(hostelItr.hasNext()) {
										Node<Hostel> h = (Node<Hostel>) hostelItr.next();
										if(h.value().name().equals(words[2])) {
											Iterator<Student_> hostelStudentItr = h.value().studentList();
											int studentCount = 0;			
											String[] studentArr = new String[4];
											while(hostelStudentItr.hasNext()) {
												Student hostelStudent = (Student) hostelStudentItr.next();
												if(!hostelStudent.entryNo().equals(words[1])) {
													if (studentArr.length == studentCount) {
													      // expand list
													      studentArr = createNewArray(studentArr);
													 }
													studentArr[studentCount] = hostelStudent.entryNo();
													studentCount++;
													studentArr = lexiSort(studentArr, studentCount);
												}
											}
											for(int i1 = 0; i1 < studentCount; i1++) {
												if(studentArr[i1] != null) {
													System.out.print(studentArr[i1] + " ");
												}
									        }
											System.out.println();
											break;
										}
									}
								}
								else if(s.value().department().equals(words[2])) {// entity is dept
									Iterator<Position_<Department>> departmentItr = allDepartments.positions();
									while(departmentItr.hasNext()) {
										Node<Department> d = (Node<Department>) departmentItr.next();
										if(d.value().name().equals(words[2])) {
											Iterator<Student_> departmentStudentItr = d.value().studentList();
											int studentCount = 0;			
											String[] studentArr = new String[4];
											while(departmentStudentItr.hasNext()) {
												Student departmentStudent = (Student) departmentStudentItr.next();
												if(!departmentStudent.entryNo().equals(words[1])) {
													if (studentArr.length == studentCount) {
													      // expand list
													      studentArr = createNewArray(studentArr);
													 }
													studentArr[studentCount] = departmentStudent.entryNo();
													studentCount++;
													studentArr = lexiSort(studentArr, studentCount);
												}
											}
											for(int i1 = 0; i1 < studentCount; i1++) {
												if(studentArr[i1] != null) {
													System.out.print(studentArr[i1] + " ");
												}
									        }	
											System.out.println();
											break;
										}
									}
								}
								else { // entity is course
									Iterator<Position_<Course>> courseItr2 = allCourses.positions();
									while(courseItr2.hasNext()) {
										Node<Course> c = (Node<Course>) courseItr2.next();
										if(c.value().code().equals(words[2])) {
											Iterator<Student_> courseStudentItr = c.value().studentList();
											int studentCount = 0;			
											String[] studentArr = new String[4];
											while(courseStudentItr.hasNext()) {
												Student courseStudent = (Student) courseStudentItr.next();
												if(!courseStudent.entryNo().equals(words[1])) {
													if (studentArr.length == studentCount) {
													      // expand list
													      studentArr = createNewArray(studentArr);
													 }
													studentArr[studentCount] = courseStudent.entryNo();
													studentCount++;
													studentArr = lexiSort(studentArr, studentCount);
												}
											}
											for(int i1 = 0; i1 < studentCount; i1++) {
												if(studentArr[i1] != null) {
													System.out.print(studentArr[i1] + " ");
												}
									        }
											System.out.println();
											break;
										}
									}
								}
							}
						}
					}
				}   
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getData(args[0], args[1]);
		answerQueries(args[2]);
	}

}
