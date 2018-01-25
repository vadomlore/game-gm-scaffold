package com.sygamer.starreduxgm.user.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolePermission {
    Map<String, List<String>> permissionPages = new HashMap<>();

    public List<String> getPermissionPages(String role){
        return permissionPages.get(role);
    }
}
