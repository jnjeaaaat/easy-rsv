package shop.jnjeaaaat.easyrsv.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value(value = "${jwt.token.key}")
    private String secretKey = "secretKey";

    private long expireTimeMs = 1000L * 60 * 60 * 24 * 365;

    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Base64
                .getEncoder()
                .encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));

        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    /*
    userId 값으로 token 발행
    @return String
     */
    public String createToken(String userEmail, Long userId,  List<String> roles) {
        log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims()
                // email 암호화해서 저장
                .setSubject(Aes256Util.encrypt(userEmail)); // 일종의 map
        claims.put("userId", userId);
        claims.put("roles", roles);

        Date now = new Date(System.currentTimeMillis());

        String token = Jwts.builder()       // 토큰 생성
                .setClaims(claims)
                .setIssuedAt(now)      //  시작 시간 : 현재 시간기준으로 만들어짐
                .setExpiration(new Date(now.getTime() + expireTimeMs))     // 끝나는 시간 : 지금 시간 + 유지할 시간(입력받아옴)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    /*
    토큰 인증 정보 조회
     */
    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        // claim 의 subject 에 저장되어있는 email 복호화
                        Aes256Util.decrypt(getUsername(token))
                );
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails Username : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities()
        );
    }

    /*
    token 으로 email 추출
     */
    private String getUsername(String token) {
        log.info("[getUsername] 토큰 기반 회원 email 추출");
        String info = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token) // Jws<Claims>
                .getBody() // Claims
                .getSubject(); // 암호화 되어있는 email 값
        log.info("[getUsername] 토큰 기반 회원 email 추출 완료, info : {}", info);
        return info;
    }

    /*
    헤더로부터 token 추출
     */
    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    /*
    토큰 유효성 체크
     */
    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }


    /*
    token 으로 부터 userId 값 가져오기
     */
    public Long getUserIdFromToken() {
        log.info("[getUserIdFromToken] userId 값 가져오기");
        HttpServletRequest request =
                ((ServletRequestAttributes)
                        RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        String token = resolveToken(request);

        log.info("token : {}", token);

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token) // Jws<Claims>
                .getBody() // Claims
                .get("userId", Long.class); // 암호화 되어있는 email 값

    }


}
