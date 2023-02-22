package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.Profile;
import com.company.quitter.model.User;
import com.company.quitter.util.request.AuthenticationRequest;
import com.company.quitter.util.request.AuthenticationResponse;
import com.company.quitter.util.request.RegisterRequest;
import com.company.quitter.model.enumiration.UserRole;
import com.company.quitter.repository.UserRepository;
import com.company.quitter.model.Token;
import com.company.quitter.repository.TokenRepository;
import com.company.quitter.model.enumiration.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var userProfile = Profile.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .dob(request.getDob())
                .profilePictureURL("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
                .aboutMe("Bio")
                .build();

        var user = User.builder()
                .userRole(UserRole.USER)
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .registrationDate(LocalDateTime.now().format(Main.dataFormatter))
                .password(passwordEncoder.encode(request.getPassword()))
                .userProfile(userProfile)
                .posts(new ArrayList<>())
                .following(new HashSet<>())
                .followers(new HashSet<>())
                .build();

        var jwtToken = tokenService.generateToken(user);

        var savedUser = userRepository.save(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty())
            return AuthenticationResponse.builder()
                    .token("")
                    .build();

        User user = optionalUser.get();
        String jwtToken = tokenService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
            .userId(user.getId())
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}
