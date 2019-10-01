package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;
    private int time;

    public Student(String trim, int parseInt) {
        this.name = trim;
        this.marks = parseInt;
        this.time = MaxHeap.timeOfInsertion;
    }


    @Override
    public int compareTo(Student student) {
        int markCompare = Integer.compare(this.getMarks(), student.getMarks());
        if (markCompare == 0) {
           return Integer.compare(student.time, this.time);
        }
        return markCompare;
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
