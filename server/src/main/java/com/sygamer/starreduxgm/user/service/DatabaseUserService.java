package com.sygamer.starreduxgm.user.service;

import com.sygamer.starreduxgm.endpoint.UserAccount;
import com.sygamer.starreduxgm.endpoint.UserViewBean;
import com.sygamer.starreduxgm.user.model.User;
import com.sygamer.starreduxgm.user.repository.PermissionRepository;
import com.sygamer.starreduxgm.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DatabaseUserService implements UserService {
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public DatabaseUserService(UserRepository userRepository, PermissionRepository permissionRepository){
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }
    @Override
    public Optional<User> getByUsername(String username) {
        return this.userRepository.findUserByName(username);
    }

    @Override
    public List<String> getRolesByUserId(int id) {
        return permissionRepository.getRolessByUserId(id);
    }

    @Override
    public List<String> getGrantedAuthoritys(String resourceUrl) {
        return permissionRepository.getResourceGrantRoles(resourceUrl);
    }

    @Override
    public List<String> getAuthenticatedRouters(String userName) {
        return permissionRepository.getAuthenticatedRouters(userName);
    }

    @Override
    public Map<String, List<String>> getAllRoleRouters() {
        return permissionRepository.getAllRoleRouters();
    }

    @Override
    public boolean verifyPassword(String userName, String password) {
        return userRepository.verifyPassword(userName, password);
    }

    @Override
    public void changePassword(String userName, String newPassword) {
        userRepository.changePassword(userName, newPassword);
    }

    @Override
    public UserAccount addUser(UserAccount userAccount) {
        User user = userRepository.create(new User(0,
                userAccount.getName(),
                userAccount.getPassword(),
                "",
                "",
                Timestamp.from(Instant.now())));
        permissionRepository.addRoleForUser(user.getId(), userAccount.getRoles().get(0));
        userAccount.setId(user.getId());
        return userAccount;
    }
    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    public List<UserAccount> findAllGrantedUsers(String filter) {
        List<UserViewBean> users = userRepository.findAllGrantedUsers(filter);
        List<UserAccount> accounts = new ArrayList<>();
        users.forEach(u->{
            Optional<UserAccount> userAccount = accounts.stream().filter(x->x.getName().equals(u.getName())).findFirst();
            if(userAccount.isPresent()){
                userAccount.get().getNamedRoles().add(u.getRole());
            }else{
                UserAccount account = new UserAccount();
                account.setId(u.getId());
                account.setName(u.getName());
                List<String> roles = new ArrayList<>();
                roles.add(u.getRole());
                account.setNamedRoles(roles);
                accounts.add(account);
            }
        });
        return accounts;
    }


}
