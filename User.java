public abstract class User {
    int id;
    String name;
    String email;
    String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String password) {

    }

    public void logout() {

    }
}