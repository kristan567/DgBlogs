package com.example.Blog_Application2.config.secuirty;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @Override
    public AuthenticatedUser getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser){
            return (AuthenticatedUser) authentication.getPrincipal();

        }
        throw new RuntimeException("No authenticated user found");
    }
}
