package data;

public class Account extends BaseItem {
    private String password;
    private Role role;

    // Constructor
    // Don't change the order of these two or else CSV will break
    // Yea it's kinda jank
    public Account(String password, Role role, String Id) {
        this.password = password;
        this.role = role;
        this.Id = Id;
    }

    public Account(String username, String password, Role role) {
        this.password = password;
        this.role = role;
        this.Id = username;
    }

    // Getters and Setters
    public String getUsername() {
        return Id;
    }

    public void setUsername(String username) {
        this.Id = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void printLog() {
        System.out.println("Account loaded.");
    }
}
