package com.sygamer.starreduxgm.endpoint;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sygamer.starreduxgm.security.auth.jwt.extractor.TokenExtractor;
import com.sygamer.starreduxgm.security.auth.jwt.extractor.verifier.TokenVerifier;
import com.sygamer.starreduxgm.security.config.JwtSettings;
import com.sygamer.starreduxgm.security.config.WebSecurityConfig;
import com.sygamer.starreduxgm.security.exceptions.InvalidJwtToken;
import com.sygamer.starreduxgm.security.model.UserContext;
import com.sygamer.starreduxgm.security.model.token.JwtToken;
import com.sygamer.starreduxgm.security.model.token.JwtTokenFactory;
import com.sygamer.starreduxgm.security.model.token.RawAccessJwtToken;
import com.sygamer.starreduxgm.security.model.token.RefreshToken;
import com.sygamer.starreduxgm.user.model.User;
import com.sygamer.starreduxgm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * RefreshTokenEndpoint
 *
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        List<String> roles = userService.getRolesByUserId(user.getId());
        if (roles == null || roles.isEmpty()) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority( authority))
                .collect(Collectors.toList());
        UserContext userContext = UserContext.create(user.getName(), authorities);
        return tokenFactory.createAccessJwtToken(userContext);
    }
}
