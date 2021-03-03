package com.starfish.extension.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.starfish.context.User;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomJsonWebToken
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-02-12
 */
@SuppressWarnings(value = "unused")
public class CustomJsonWebToken {

    public static final String USER_ID_KEY = "userId";

    public static final String USER_NAME_KEY = "userName";

    public static final String NICK_NAME_KEY = "nickName";

    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID_KEY, userId);
//        Map<String, Object> claims =ImmutableMap.of(USER_ID_KEY,userId);
        return JsonWebTokenPlus.createToken(claims);
    }

    public static String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID_KEY, user.getUserId());
        claims.put(USER_NAME_KEY, user.getUserName());
        claims.put(NICK_NAME_KEY, user.getNickName());
        return JsonWebTokenPlus.createToken(claims);
    }

    public static User verifyToken(String token) {
        DecodedJWT decodedJwt = JsonWebTokenPlus.verifyToken(token);
        Map<String, Claim> claims = decodedJwt.getClaims();

        // 解析claims
        Claim userIdClaim = claims.get(USER_ID_KEY);
        Long userId = userIdClaim.asLong();
        Claim userNameClaim = claims.get(USER_NAME_KEY);
        String userName = userNameClaim.asString();
        Claim nickNameClaim = claims.get(NICK_NAME_KEY);
        String nickName = nickNameClaim.asString();

        // 封装user对象
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setNickName(nickName);
        return user;
    }

}
