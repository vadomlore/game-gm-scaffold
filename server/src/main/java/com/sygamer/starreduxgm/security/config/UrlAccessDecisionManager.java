package com.sygamer.starreduxgm.security.config;

import com.sygamer.starreduxgm.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.sygamer.starreduxgm.security.config.WebSecurityConfig.FORM_BASED_LOGIN_ENTRY_POINT;
import static com.sygamer.starreduxgm.security.config.WebSecurityConfig.TOKEN_REFRESH_ENTRY_POINT;

/**
 * 验证资源跟角色之间的关系
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    /**
     * // authentication 为用户所被赋予的权限, configAttributes 为访问相应的资源应该具有的权限。
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<GrantedAuthority> userHasRoles =
                (Collection<GrantedAuthority>) authentication.getAuthorities();
        LOGGER.info("CurrentUser={} CurrentHasRoles = {}", authentication.getName(), Arrays.asList(userHasRoles));
        //hack
        if(((FilterInvocation) object).getRequestUrl().equals(TOKEN_REFRESH_ENTRY_POINT) ||
                ((FilterInvocation) object).getRequestUrl().equals(FORM_BASED_LOGIN_ENTRY_POINT)){
            return;
        }
        //放行[超级管理员]角色
        Iterator<GrantedAuthority> iterator = userHasRoles.iterator();
        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            if ("admin".equals(grantedAuthority.getAuthority())) {
                return;
            }
        }
        if (!authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access Denied.");
        }
        LOGGER.info("1 CurrentUser={} CurrentHasRoles = {}", authentication.getName(), Arrays.asList(userHasRoles));
        return;

//        List<String> uriRoles = userService.getGrantedAuthoritys(((FilterInvocation) object).getRequestUrl());
//        if (uriRoles == null || uriRoles.isEmpty()) {
//            throw new AccessDeniedException("Access Denied.");
//        }
//
//        List<GrantedAuthority> uriHasRoles = uriRoles.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
//
//        Optional<Collection<GrantedAuthority>> grantedAuthoritiesForOptional =
//                Optional.ofNullable(userHasRoles);
//
//        final boolean[] hasInsection = {false};
//        grantedAuthoritiesForOptional.ifPresent(userHasRolesNotNull -> {
//            userHasRolesNotNull.forEach(userHasRole -> {
//                if (uriHasRoles.stream().anyMatch(x -> x.getAuthority().equals(userHasRole.getAuthority()))) {
//                    hasInsection[0] = true;
//                }
//            });
//        });
//        if (!hasInsection[0]) {
//            throw new AccessDeniedException("Access Denied.");
//        }
//        return;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
