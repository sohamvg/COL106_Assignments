public class Student implements Student_ {

    String fname, lname, hostel, department, cgpa;

    public Student(String fname, String lname, String hostel, String department, String cgpa) {
        this.fname = fname;
        this.lname = lname;
        this.hostel = hostel;
        this.department = department;
        this.cgpa = cgpa;
    }

    @Override
    public String fname() {
        return fname;
    }

    @Override
    public String lname() {
        return lname;
    }

    @Override
    public String hostel() {
        return hostel;
    }

    @Override
    public String department() {
        return department;
    }

    @Override
    public String cgpa() {
        return cgpa;
    }

    @Override
    public String toString() {
        return fname;
    }

    /**
     * compares fname+lname
     * @param o o will be key
     * @return compare
     */
    @Override
    public boolean equals(Object o) {
        if ((this.fname()+this.lname()).equals(o.toString())) return true;
        if (this == null || o == null || getClass() != o.getClass()) return false;
        return false;
    }
}