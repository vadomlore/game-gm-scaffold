package com.sygamer.starreduxgm.endpoint;

import java.util.List;

public class UserAccount {
    private int id;
    private String name;
    private String password;
    private List<Integer> roles;

    private List<String> namedRoles;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getNamedRoles() {
        return namedRoles;
    }

    public void setNamedRoles(List<String> namedRoles) {
        this.namedRoles = namedRoles;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", namedRoles=" + namedRoles +
                '}';
    }
}
