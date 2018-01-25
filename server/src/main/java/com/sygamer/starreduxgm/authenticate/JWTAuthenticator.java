//package com.sygamer.starreduxgm.authenticate;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.sygamer.starreduxgm.user.repository.PermissionRepository;
//import com.sygamer.starreduxgm.user.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.time.Instant;
//import java.util.*;
//
//public class JWTAuthenticator {
//
//    @Autowired
//    PermissionRepository permissionRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    public final static String secretKey = "com.sygamer.starreduex.gmtools";
//
//    public static String generateToken(int userId, String username, List<String> roles){
//        String token="";
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secretKey);
//            token = JWT.create()
//                    .withIssuer("auth0")
//                    .withSubject(String.valueOf(userId))
//                    .withClaim("name", username)
//                    .withExpiresAt(Date.from(Instant.now().plusSeconds(86400)))
//                    .withArrayClaim("roles", roles.toArray(new String[]{})).sign(algorithm);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }catch (JWTCreationException exception){
//            //Invalid Signing configuration / Couldn't convert Claims.
//        }
//        return token;
//    }
//
//    public  void authenticateToken(String token, String routerUrl){
//        DecodedJWT jwt = null;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer("auth0")
//                    .build(); //Reusable verifier instance
//            jwt = verifier.verify(token);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }catch (JWTCreationException exception){
//            //Invalid Signing configuration / Couldn't convert Claims.
//        }
//        if(jwt == null) {
//            //return false;
//        }
//        int playerId = Integer.parseInt(jwt.getSubject());
//        Date expireDate = jwt.getExpiresAt();
//        //todo 重新计算发送token
//
//        Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
//        Claim claim = claims.get("roles");
//        String roles[] = claim.asArray(String.class);
//        String userName = claims.get("name").asString();
//        List<String> allowedRouters = permissionRepository.getUserPermissionsByName(userName);
//        if(!allowedRouters.contains(routerUrl)){
//           //do next
//        }
//        //return true;
//    }
//
//    public String createRefreshToken(String userName, String userId, String[] roles) {
//        if (StringUtils.isEmpty(userName)) {
//            throw new IllegalArgumentException("Cannot create JWT Token without username");
//        }
//        String token="";
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secretKey);
//            token = JWT.create()
//                    .withIssuer("auth0")
//                    .withSubject(String.valueOf(userId))
//                    .withClaim("name", userName)
//                    .withExpiresAt(Date.from(Instant.now().plusSeconds(86400)))
//                    .withArrayClaim("roles", roles).sign(algorithm);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }catch (JWTCreationException exception){
//            //Invalid Signing configuration / Couldn't convert Claims.
//        }
//        return token;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(Date.from(Instant.now().plusSeconds(86400)));
//    }
//}
