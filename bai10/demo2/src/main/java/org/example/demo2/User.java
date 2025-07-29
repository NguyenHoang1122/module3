package org.example.demo2;

public class User {
    private int id;
    private String username;
    private String email;

    public User(int id, String username, String emai) {
        this.id = id;
        this.username = username;
        this.email = emai;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmai(String emai) {
        this.email = emai;
    }


}
