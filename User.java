public abstract class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User(int id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public boolean authenticateUser(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public Role getRole() {
        return role;
    }
}