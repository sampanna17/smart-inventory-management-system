package com.smartinventorysystem.utils;

import com.smartinventorysystem.exceptions.UnauthorizedException;
import com.smartinventorysystem.modules.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new UnauthorizedException("Unauthorized access");
        }

        return user;
    }

    public Integer getCurrentUserId() {
        return getCurrentUser().getUserID();
    }
}