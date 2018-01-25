package com.sygamer.starreduxgm.security.model;

public enum Scopes {
    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }

    public static void main(String[] args) {
        System.out.println(Scopes.REFRESH_TOKEN.authority());
    }
}

