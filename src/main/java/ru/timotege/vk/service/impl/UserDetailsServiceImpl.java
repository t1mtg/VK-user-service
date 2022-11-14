package ru.timotege.vk.service.impl;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.timotege.vk.dto.user.UserRequestDTO;
import ru.timotege.vk.dto.user.UserResponseDTO;
import ru.timotege.vk.entity.User;
import ru.timotege.vk.repository.UserRepository;

@Service("userDetailsServiceImpl")
@Getter
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist")
                );
    }

    public UserResponseDTO saveUser(UserRequestDTO requestDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        var password = passwordEncoder.encode(requestDTO.getPassword());

        User user = new User(requestDTO.getUsername(), password);
        var savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getUsername());
    }

}