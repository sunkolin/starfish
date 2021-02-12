package com.starfish.module.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JsonWebToken
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-02-12
 */
@SuppressWarnings(value = "unused")
public class JsonWebToken {

    private static final String SECRET = "1234567890";

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    /**
     * 生成token
     *
     * @param claims claims
     * @return 结果
     */
    public static String createToken(Map<String, Object> claims) {
        JsonWebTokenHeader header = new JsonWebTokenHeader();
        header.setAlg("HS256");
        header.setTyp("jwt");

        JsonWebPayload payload = new JsonWebPayload();
        payload.setIss(JsonWebTokenConstant.ISS);
        Date now = new Date();
        payload.setIat(now);
        // 设置有效期一个月
        payload.setExp(DateUtils.addDays(now, 30));
        payload.setJti(UUID.randomUUID().toString().replaceAll("-", ""));

        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(payload.getIss());
        builder.withIssuedAt(payload.getIat());
        builder.withExpiresAt(payload.getExp());
        builder.withJWTId(payload.getJti());

        claims.forEach((k, v) -> {
            if (v instanceof Integer) {
                builder.withClaim(k, (Integer) v);
            } else if (v instanceof Long) {
                builder.withClaim(k, (Long) v);
            } else if (v instanceof Double) {
                builder.withClaim(k, (Double) v);
            } else if (v instanceof Date) {
                builder.withClaim(k, (Date) v);
            } else if (v instanceof Boolean) {
                builder.withClaim(k, (Boolean) v);
            } else {
                builder.withClaim(k, (String) v);
            }
        });

        return builder.sign(algorithm);
    }

    /**
     * 验证token
     *
     * @param token token
     * @return 结果
     */
    public static DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JsonWebTokenConstant.ISS)
                .build();
        return verifier.verify(token);
    }

}
