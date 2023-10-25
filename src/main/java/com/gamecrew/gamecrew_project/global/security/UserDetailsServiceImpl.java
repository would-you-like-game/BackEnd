package com.gamecrew.gamecrew_project.global.security;

import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + nickname));

        return new UserDetailsImpl(user);
    }
}