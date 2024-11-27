package com.example.chuyentrang.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;


public class JwtTokenUtil {

    private static final String SECRET_KEY = "UIY89JSPAdXTF7B8P4MQULxr28UEr4vKE7LDH5pmekBqimsQKHAt5Yf3Vo9U3BAmx9xRJ1AqiTetIjx1oUsErbbA3fGH4xTqxc4rVz7gxeR7h45Zj6mX"; // Secret key

    // Tạo token
    public static String createToken(String username, String role) throws JOSEException {
        // Tạo JWT claims
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("role", role) // Thêm vai trò
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 2 * 60 * 1000))  // 2 phút

                .build();

        // Tạo header và signer
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256); // Sử dụng HMAC với SHA-256
        MACSigner signer = new MACSigner(SECRET_KEY.getBytes());

        // Tạo JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }


    public static String getRoleFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getStringClaim("role");  // Lấy role từ token
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Xác minh token
    public static boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

            // Kiểm tra xem token có hợp lệ không
            return signedJWT.verify(verifier);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin người dùng từ token
    public static String getUsernameFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return claims.getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        String role = getRoleFromToken(token);

        if (username != null && role != null) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority( role))
            );

        }
        return null;
    }

}


