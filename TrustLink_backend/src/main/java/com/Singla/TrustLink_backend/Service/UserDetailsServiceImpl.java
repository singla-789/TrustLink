package com.Singla.TrustLink_backend.Service;

import com.Singla.TrustLink_backend.Repositary.UserRepository;
import com.Singla.TrustLink_backend.modles.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepositary;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositary.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this userName is not found !! :" + username));
        return UserDetailsImpl.build(user);
    }
}
