package com.owner.service.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // In a real application, you would inject and use a repository to fetch user details
    // @Autowired
    // private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For demonstration purposes; in a real app, fetch from database
        // Replace this with actual user retrieval logic
        if ("admin".equals(username)) {
            return new User("admin", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", 
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        // Real implementation would be:
        // User user = userRepository.findByUsername(username)
        //     .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        // return new org.springframework.security.core.userdetails.User(
        //     user.getUsername(), user.getPassword(), user.getAuthorities());
    }
} 