package com.infy.gameszone.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component(value = "jwtAuthenticationFilter")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get Authorization header for http-request
        final String authHeader = request.getHeader("Authorization");
        // will used store jwt token that is extracted authHeader
        final String jwtToken;
        // will used to store userName extracted from jwtToken
        final String userName;
        //
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //
        jwtToken = authHeader.substring(7);
        //
        userName = jwtService.extractUsername(jwtToken);
        //
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                //
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userName,
                        null,
                        userDetails.getAuthorities());
                //
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                //
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //
            }
        }
        //
        filterChain.doFilter(request, response);
    }
}
