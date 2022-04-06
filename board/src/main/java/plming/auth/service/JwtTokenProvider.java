package plming.auth.service;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import plming.auth.config.PrincipalDetailsService;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenProvider {

    // 임시 비밀키
    private String secretKey = "sdfeonxcvkongsersdf";

    private final PrincipalDetailsService principalDetailsService;

    public JwtTokenProvider( PrincipalDetailsService principalDetailsService) {
        this.principalDetailsService = principalDetailsService;
    }

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

    public boolean validateTokenAndUserId(HttpServletRequest request, Long userId){
        Long userIdFromToken = getUserId(resolveToken(request));
        if(userIdFromToken != userId){
            return false;
        }
        return true;
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token){
        UserDetails userDetails = principalDetailsService.loadUserByUsername(this.getUserId(token).toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    } // 난 UserDetails에 jpa entity를 연동하지 않았는데 괜찮은가?

    // Request Header에서 토큰 값 추출
    private String resolveToken(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,"token");
        if(cookie == null){
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        return cookie.getValue();
    }

    // jwt 토큰 유효성 검사
    public String validateToken(HttpServletRequest request){
        try{
            String jwtToken = resolveToken(request);
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return jwtToken;
        }catch(Exception e){
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

}