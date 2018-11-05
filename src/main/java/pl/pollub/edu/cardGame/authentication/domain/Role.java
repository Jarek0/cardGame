package pl.pollub.edu.cardGame.authentication.domain;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

    PLAYER;

    public SimpleGrantedAuthority authority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
