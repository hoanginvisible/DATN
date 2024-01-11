package com.portalevent.infrastructure.security;

import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.constant.SessionConstant;
import com.portalevent.util.AreRolesEqual;
import com.portalevent.util.CallApiIdentity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author thangncph26123
 */
@Component
public class JwtTokenProvider {

    @Value("${identity.secretKey}")
    private String secretKey;

    @Autowired
    private HttpSession session;

    @Autowired
    private CallApiIdentity callApiIdentity;

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        String idUser = claims.get("id", String.class);
        String name = claims.get("name", String.class);
        String userName = claims.get("userName", String.class);
        String email = claims.get("email", String.class);

        System.out.println(email);
        session.setAttribute(SessionConstant.IDUSER, idUser);
        session.setAttribute(SessionConstant.EMAIL, email);
        session.setAttribute(SessionConstant.USERNAME, userName);
        session.setAttribute(SessionConstant.NAME, name);

        Object roleClaim = claims.get("role");
		List<String> lstRole = new ArrayList<>();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roleClaim instanceof String) {
            lstRole.add((String) roleClaim);
            authorities.add(new SimpleGrantedAuthority((String) roleClaim));
        } else if (roleClaim instanceof List<?>) {
            lstRole = (List<String>) roleClaim;
            for (String role : lstRole) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
		session.setAttribute(SessionConstant.ROLE, lstRole);
        return new UsernamePasswordAuthenticationToken(idUser, token, authorities);
    }

    public String validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);

            Date expirationDate = claims.getBody().getExpiration();
            if (expirationDate.before(new Date())) {
                return Message.SESSION_EXPIRED.getMessage();
            }
            Object roleClaim = claims.getBody().get("role");
            String id = claims.getBody().get("id", String.class);
            Object response = callApiIdentity.handleCallApiGetRoleUserByIdUserAndModuleCode(id);
            if (response == null) {
                return null;
            }
            if (!AreRolesEqual.compareObjects(roleClaim, response)) {
                return Message.ROLE_HAS_CHANGE.getMessage();
            }
            return "";
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
