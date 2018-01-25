package com.sygamer.starreduxgm.business.model;

import com.sygamer.starreduxgm.security.model.UserContext;
import com.sygamer.starreduxgm.user.model.User;

public class UserProfile {
    private User user;

    private UserContext userContext;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }
}
