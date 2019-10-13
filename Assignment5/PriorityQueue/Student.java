package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
        this.name = trim;
        this.marks = parseInt;
    }


    @Override
    public int compareTo(Student student) {
        return Integer.compare(this.getMarks(), student.getMarks());
    }

    String getName() {
        return name;
    }

    private Integer getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }
}
