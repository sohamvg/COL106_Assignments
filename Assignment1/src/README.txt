COL106 Assignment-1 README
Soham Gaikwad, 2018cs10394

****************************************************************************************
No change in the interfaces is made. Each interface works as described in the assignment.
Each method defined in the classes complies. No work is remaining in my opinion.
*****************************************************************************************
Following is the implementation of the classes I made.


1) Node<T> implements Position<T>
        a) T value
                It is a variable of type T that is used to store the value to be entered in the linked list
        b) Node next
                It is an object of this class that stores the position of the next Node
        c) Constructors
                Initializes value and next.
        d) T value()
                Returns value
        e) Position_<T> after()
                Returns next;


2) positionItr<T> implements Iterator<Position_<T>> // subclass of List
        a) Node<T> curr
                variable of type Node<T> that points to the current element in the list.
        b) Constructor
                takes input as a List<T> list and assigns curr as the head of the list.
        c) T next()
                stores the value in the current node in data, takes curr to the next node in the list and then returns data.
        d) boolean hasNext()
                checks whether the end of the list is reached.


3) List<T> implements LinkedList_<T>
        a) Node head
                node called head that acts as the head of the linked list
        b) Node tail
                Node called tail that acts as tail of linked list
        c)Node<T> head()
                returns head
        d)Node<T> tail()
                returns tail
        e) Iterator<Position_<T>> positions()
                returns an iterator of type positionItr of this linked list
        g) Position_<T> add(T e)
                adds a new node to the linked list. If the list is empty then head and tail are assigned the value of newNode otherwise newNode is assigned to tail.next and then reference tail is moved to the newNode. nodeCount is increased by one.
        f) int count()
                returns the size / nodeCount of the linked list


4) class studentItr implements Iterator<Student_> // used in all classes implementing Entity_.
1. Node<Student> curr;
        variable of type Node<Student> that points to the current element in the list.
1. Constructor
        takes the current entity object and assigns curr as the head of the list which is part of the entity.
1. hasNext()
        checks whether the end of the list is reached.
1. next()
        stores the value in the current node in data, takes curr to the next node in the list and then returns data.


4) Hostel implements Entity_
        a) String name
                Used to store the name of the entity
        b) List<Student_> hostelStudents
                creates a linked list of students which have same entity ( for example students of one hostel)         
        c)String name()
                returns name
        d)Iterator<Student_> studentList()
                returns an iterator of the list of students in this entity which is object of the studentItr class.
        e) void addStudent
                Adds a student <Student_> to hostelStudents


4) Department implements Entity_
        a) String name
                Used to store the name of the entity
        b) List<Student_> departmentStudents
                creates a linked list of students which have same entity ( for example students of one hostel)         
        c)String name()
                returns name
        d)Iterator<Student_> studentList()
                returns an iterator of the list of students in this entity which is object of the studentItr class.
        e) void addStudent
                Adds a student <Student_> to departmentStudents


6) Course implements Entity_
        a) String name
                Used to store the name of the course
        b) String code
                Used to store the code of the course
        b) List<Student_> courseStudents
                creates a linked list of students which have same entity ( for example students of one hostel)         
        c)String name()
                returns name
        d)Iterator<Student_> studentList()
                returns an iterator of the list of students in this entity which is object of the studentItr class.
        e) void addStudent
                Adds a student <Student_> to courseStudents




7) GradeInfo implements GradeInfo_
        a) LetterGrade letterGrade
                Used to store the grade of a particular course of a student
        b) int gradePoint
                Stores the grade point of the grade
        b) Constructor
                It takes a string as an input( which is the grade of the student in a particular course), converts it to LetterGrade(enum) data type and stores it in letterGrade. Also converts it into grade point using gradepoint function of GradeInfo_ and stores in gradePoint
        c) LetterGrade letterGrade()
                Returns letterGrade
        d) int gradepoint()
                Returns gradepoint


8) CourseGrade implements CourseGrade_
        a) String courseTitle
                Used to store the course title of the course
        b) String courseNum
                Used to store the course code of the course
        c) GradeInfo grade
                Used to access the grade of a student in a particular course(for example- used in calculating cgpa)
        d) String coursetitle()
                returns coursetitle
        e) String coursenum()
                returns coursenum
        f) GradeInfo grade()
                returns grade


9) Student implements Student_
        a)String name, entryno, hostel, department
                Used to store name, entryno, hostel, department of the student respectively
        b) String name(), entryno(), hostel(), department()
                returns name, entryno, hostel, department of the student respectively
        c) String completedCredits()
                basically calculates the total credits that have been completed by the student. courseCount is initialized to 0. It uses courseItr to loop through all the courses of the student. courseCount is incremented if a student's grade is not E, F or I.
                Finally, multiply coursecount by 3 then convert it to string then return it.
        d) String cgpa
                basically returns the cgpa of the student
                As all courses have same credit, it simply calculates cgpa by given formula but not multiplying denominator or numerator by 3. courseCount = 0 and sum = 0 initially. Loop using courseItr, at each loop sum is increased by the gradepoint and courseCount is increased by 1 if the grade is not I. Then I use simple logic (as clear from the code) to approximate CG to 2 decimal places.
        e) Iterator<CourseGrade_> courseList()
                Returns an iterator to traverse the course list of the student.
        f) class courseItr implements Iterator<CourseGrade_>
                Similar to studentItr in Entity.


10)  Assignment1
        This is the final class that implements the main function.
        a) main(String args[])
                This is just a function that is used to call the functions getData() and answerQueries() and pass arguments to them.
        b) static List<Student> StudentList
                Creates a linked list which stores Student type of data in it. 
                This is the basic list of all students and their details.
        c) static List<Hostel> allHostels
                Creates a linked list which stores Hostel type of data in it.
                This is a list of all hostels and each node/hostel also contains the list of students belonging to that hostel.
        d) static List<Department> allDepartments
                Creates a linked list which stores Department type of data in it.
                This is a list of all department and each node/department also contains the list of students belonging to that department.
        e) static List<Course> allCourses
                Creates a linked list which stores Course type of data in it.
                This is a list of all courses and each node/courses also contains the list of students belonging to that course.
        f)  static String[] createNewArray(String[] oldArray)
                Used to implement dynamic array for storing queries. Returns a newArray twice the size of the oldArray whose first half contains oldArray and next half contains null.
g) static String[] lexiSort(String[] words, int n)
        Sorts the first n elements of words array lexicographically (elements after n are all null) using bubble sort and compareTo.
        h) private static void getData(String studentRecordFile, String courseFile)
Basically this is a function which takes two arguments as inputs(which are files with data about students and their courses)and fills the linked lists created above.
        Using Bufferedreader, corresponding to each line of the studentRecordFile, I add the student (iniitialized with name, hostel etc.) to allStudents list. Also I add the hostels and department to allHostels and allDepartments only if a new entity is encountered.
        For the courseFile, I add the courses to the particular student using studentIr and I add the courses to the allCourses list using courseItr. Both of this is done only if a new course is encountered.
i) private static void answerQueries(String c)
                Basically, this function is used to print the output of various types of queries asked in the file(which is taken as the argument)
        Using BufferedReader, I first create a new array of size 10 which stores each line of query file. The size of arr is dynamically increased if lines are more than 10. Now in the reverse order of the arr, I answer each query.
                1) SHARE
                        This processes all the queries which are of SHARE type.
                        First I find the student using studentItr2, then acc. to the query I create the respective iterator of the entity needed and print all students from that entity list except the current student.
                2) INFO
                        This processes all the queries which are of the INFO type.
                        First, I create an iterator to traverse in the StudentList. Next I find the student with entry number specified in the query.
                        Next, I create an iterator to traverse in this student's course list.
                        Finally, I print the required data in the mentioned order onto the screen.
                3) COURSETITLE
                        This processes all the queries which are of the COURSETITLE type.
                        First, I create an iterator to traverse in the allCourses linked list.
                        Next I find the course code which matches the one specified in the query.
                        Finally I print the name of the matching course onto the screen.
