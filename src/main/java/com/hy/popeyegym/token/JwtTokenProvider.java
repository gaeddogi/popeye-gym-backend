package com.hy.popeyegym.token;

import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.JwtExceptionType;
import com.hy.popeyegym.security.PrincipalDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;

    public JwtTokenProvider(
            @Value("${spring.jwt.secret}") String secretKey,
            @Value("${spring.jwt.access_token_expire_time}") long accessTime,
            @Value("${spring.jwt.refresh_token_expire_time}") long refreshTime
    ) {
        ACCESS_TOKEN_EXPIRE_TIME = accessTime;
        REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * access 토큰 생성
     */
    public String createToken(Long userId, String role) {

        Claims claims = Jwts.claims().setSubject(Long.toString(userId));
        claims.put("role", role);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 토큰 발행 유저 id
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 키와 알고리즘
                .compact();
    }

    /**
     * refresh 토큰 생성
     */
    public String createToken() {
        Date now = new Date();
        return Jwts.builder()
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰 -> Authentication 가져오기
     */
    public Authentication getAuthentication(String token) {
        // 토큰 복호화해서 claims 꺼내기
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        // claims에서 권한 꺼냄
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return claims.get("role").toString();
            }
        });
//                Arrays.stream(claims.get("roles").toString().split(","))
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());

        // 위 정보를 가진 UserDetails 객체 생성
        User principal = new User(claims.getSubject(), "", authorities);

        // Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    /**
     * 토큰에서 id 추출
     */
    public Long getIdFromToken(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 토큰에서 role 추출
     */
    public String getRoleFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("role").toString();
    }

    /**
     * 토큰에서 Claims 추출
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) { // 만료된 토큰이 더라도 일단 파싱을 함
            return e.getClaims();
        } catch (Exception e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(JwtExceptionType.INVALID_TOKEN);
        }
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            log.info("사용자로부터 받은 토큰 검증시도");
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(JwtExceptionType.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new CustomException(JwtExceptionType.TOKEN_EXPIRED);
        } catch (JwtException e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(JwtExceptionType.INVALID_TOKEN);
        } catch (Exception e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(JwtExceptionType.INVALID_TOKEN);
        }
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
//        }

//        return false;
    }

    /**
     * token 남은 유효시간
     */
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }


//    private Claims parseClaims(String accessToken) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
//        } catch (ExpiredJwtException e) { // 만료된 토큰이 더라도 일단 파싱을 함
//            return e.getClaims();
//        }
//    }



}
