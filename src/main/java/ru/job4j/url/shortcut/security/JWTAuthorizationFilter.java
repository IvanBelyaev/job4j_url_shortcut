package ru.job4j.url.shortcut.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.service.SiteCredentialsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private SiteCredentialsService siteCredentialsService;

    public JWTAuthorizationFilter(
            AuthenticationManager authManager, SiteCredentialsService siteCredentialsService) {
        super(authManager);
        this.siteCredentialsService = siteCredentialsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(JWTAuthenticationFilter.HEADER_STRING);

        if (header == null || !header.startsWith(JWTAuthenticationFilter.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWTAuthenticationFilter.HEADER_STRING);
        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JWTAuthenticationFilter.TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                UserDetails userDetails = siteCredentialsService.loadUserByUsername(user);
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}