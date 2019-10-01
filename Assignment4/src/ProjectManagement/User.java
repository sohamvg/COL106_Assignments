package ProjectManagement;

public class User implements Comparable<User> {

    private String name;

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(User user) {
        return 0;
    }
}
