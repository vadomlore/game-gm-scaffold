package com.sygamer.starreduxgm.endpoint;


import com.sygamer.starreduxgm.business.model.UserProfile;
import com.sygamer.starreduxgm.security.auth.JwtAuthenticationToken;
import com.sygamer.starreduxgm.security.model.UserContext;
import com.sygamer.starreduxgm.user.model.User;
import com.sygamer.starreduxgm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * End-point for retrieving logged-in user details.
 *
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
@RestController
public class ProfileEndpoint {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/api/me", method=RequestMethod.GET)
    public @ResponseBody
    UserProfile get(JwtAuthenticationToken token) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserContext((UserContext) token.getPrincipal());
        Optional<User> user = userService.getByUsername(userProfile.getUserContext().getUsername());
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + userProfile.getUserContext().getUsername()));
        user.ifPresent((x)->{
            userProfile.setUser(x);
        });
        return userProfile;
    }

    @RequestMapping(value="/api/logout", method=RequestMethod.POST)
    public @ResponseBody
    HttpStatus logout(JwtAuthenticationToken token) {
        return HttpStatus.OK;
    }

    @RequestMapping(value="/api/routers", method=RequestMethod.GET)
    public @ResponseBody
    Map<String, List<String>> getRouters(JwtAuthenticationToken token) {
        UserContext content = (UserContext) token.getPrincipal();
        return userService.getAllRoleRouters().entrySet().stream().filter(x -> {
            return content.getAuthorities().stream().anyMatch(y -> y.getAuthority().equals(x.getKey()));
        }).collect(Collectors.toMap(f -> f.getKey(), f -> f.getValue()));
    }

    @RequestMapping(value="/api/changepassword", method=RequestMethod.POST)
    public @ResponseBody
    HttpStatus changePassword(JwtAuthenticationToken token, @RequestBody PasswordEntity passwordEntity) {
        UserContext userContext = (UserContext) token.getPrincipal();
        if (!userService.verifyPassword(userContext.getUsername(), passwordEntity.getOldPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        userService.changePassword(userContext.getUsername(), passwordEntity.getNewPassword());
        return HttpStatus.OK;
    }

    @RequestMapping(value="/api/user/exists", method=RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> isUserNameExists(@RequestParam String username) {
        Map<String, Object> response = new HashMap();
        if(userService.getByUsername(username).isPresent()){
            response.put("status", "exists");
        }else{
            response.put("status", "not_exists");
        }
        return response;
    }

    @RequestMapping(value="/api/user/add", method=RequestMethod.POST)
    public @ResponseBody
    UserAccount addUser(@RequestBody UserAccount account) {
        return userService.addUser(account);
    }

    @RequestMapping(value="/api/user/delete/{id}", method=RequestMethod.GET)
    public @ResponseBody
    void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return;
    }

    @RequestMapping(value="/api/user/list", method=RequestMethod.GET)
    public @ResponseBody
    List<UserAccount> getAllUsers(@RequestParam(value="name", defaultValue = "") String name) {
        return userService.findAllGrantedUsers(name);
    }
}