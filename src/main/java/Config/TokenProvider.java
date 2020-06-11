package Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenProvider {

    private static final String key = "ABCD";

    public static class TokenResult {
        private Long id;
        private String email;
        private String success;

        public TokenResult(Long id, String email, String success) {
            this.id = id;
            this.email = email;
            this.success = success;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        @Override
        public String toString() {
            return "TokenResult{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", success='" + success + '\'' +
                    '}';
        }
    }

    public static String createToken(String email, Long id) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        Long expiredTime = 1000 * 60l;
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime);
        payloads.put("exp", now);
        payloads.put("email", email);
        payloads.put("id", id);

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public static TokenResult validateToken(String token) {

        if(token.equals("")) return new TokenResult(null, null, "UNAUTHORIZED");
        Claims claims = Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.get("exp", Date.class);

        if(expiration.getTime() < new Date().getTime()) {
            return new TokenResult(null, null, "토큰 시간 만료");
        }

        String email = claims.get("email", String.class);
        Long id = claims.get("id", Long.class);

        TokenResult result = new TokenResult(id, email, "성공하였습니다");

        return result;

    }

}
