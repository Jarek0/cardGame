package pl.pollub.edu.cardGame.authentication.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.edu.cardGame.authentication.repository.AuthenticationRepository;

@Service
@RequiredArgsConstructor
public class Authenticator implements UserDetailsService {

    private final AuthenticationRepository authRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return authRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login: " + login + " is not found"));
    }

}
