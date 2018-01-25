package com.sygamer.starreduxgm.user.service;

import com.sygamer.starreduxgm.endpoint.UserAccount;
import com.sygamer.starreduxgm.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    public Optional<User> getByUsername(String username);

    public List<String> getRolesByUserId(int id);

    public List<String> getGrantedAuthoritys(String resourceUrl);

    public List<String> getAuthenticatedRouters(final String userName);

    public Map<String, List<String>> getAllRoleRouters();

    public boolean verifyPassword(final String userName, String password);

    public void changePassword(final String userName, String newPassword);

    public UserAccount addUser(UserAccount userAccount);

    public void deleteUser(int id);

    public List<UserAccount> findAllGrantedUsers(String filter);


}