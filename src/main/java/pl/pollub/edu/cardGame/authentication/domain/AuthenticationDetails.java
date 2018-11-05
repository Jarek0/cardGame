package pl.pollub.edu.cardGame.authentication.domain;

import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

import static java.util.Collections.singleton;
import static pl.pollub.edu.cardGame.authentication.domain.Role.PLAYER;

@EqualsAndHashCode(of = {"login"})
public class AuthenticationDetails implements UserDetails {


    @Indexed(unique = true)
    protected String login;

    private String passwordHash;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singleton(PLAYER.authority());
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changePassword(BCryptPasswordEncoder encoder, String newPassword) {
        this.passwordHash = encoder.encode(newPassword);
    }

    public void changeLogin(String login) {
        this.login = login;
    }
}
