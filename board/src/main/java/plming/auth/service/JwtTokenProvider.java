package plming.auth.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "sdfeonxcvkongsersdf";

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // jwt 토큰 생성
    public String createToken(Long userId){
        Claims claims = Jwts.claims().setSubject(userId.toString());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // jwt 토큰에서 user Id 추출
    // 인증되지 않은 토큰일 경우 null 반환
    public Long getUserId(String token){
        try{
            return Long.parseLong(Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody().getSubject());
        }catch (Exception e){
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    // Request Header에서 토큰 값 추출
    public String resolveToken(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,"token");
        if(cookie == null){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return cookie.getValue();
    }

    // jwt 토큰 유효성 검사
    public void validateToken(HttpServletRequest request){
        try{
            String jwtToken = resolveToken(request);
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        }catch(SignatureException e){
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

}
