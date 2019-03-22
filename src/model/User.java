package model;

import java.io.Serializable;

public class User implements Serializable {
    public String email;
    public String password;
    public long code;
    public int requestCode;
    private static final long serialVersionUID = 4844442346911158547L;

    public User() {
    }

    public User(String email){
        this.email = email;
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

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

}
