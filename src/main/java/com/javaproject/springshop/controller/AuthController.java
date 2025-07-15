package com.javaproject.springshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaproject.springshop.requests.LoginRequest;
import com.javaproject.springshop.response.ApiResponse;
import com.javaproject.springshop.response.JwtResponse;
import com.javaproject.springshop.security.jwt.JwtUtils;
import com.javaproject.springshop.security.user.ShopUserDetails;
import com.javaproject.springshop.service.blacklist.ITokenBlackListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    private ITokenBlackListService tokenBlacklist;

    @GetMapping("/ping")
    public ResponseEntity<ApiResponse> ping() {
        return ResponseEntity.ok(new ApiResponse("pong", null));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            System.out.println("print login request : " + request.getEmail());
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getEmail());
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse("Login successful", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));

        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        tokenBlacklist.addToBlackList(token);
        return ResponseEntity.ok(new ApiResponse("Logged out succesfully",null));
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }


}
