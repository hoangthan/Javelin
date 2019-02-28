package Model;

public class User {
    public String email;
    public String password;
    public long code;

    public User() {
    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, long code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
