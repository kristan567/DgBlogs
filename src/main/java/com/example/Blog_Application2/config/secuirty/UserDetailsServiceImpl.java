package com.example.Blog_Application2.config.secuirty;

import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {



    @Autowired
    private final UserRepository userRepo;      //uses the value from the userrepo

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);       //   Fetches the user from the database using findByUsername.
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");
        User userDetails = user.get();     //Extracts the user entity from the Optional<User> if present.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userDetails.getRole().name()));     //Converts the user's role into a GrantedAuthority object.

        return new AuthenticatedUser(userDetails.getId(), userDetails.getEmail(), userDetails.getPassword(), authorities);     //

    }
}
