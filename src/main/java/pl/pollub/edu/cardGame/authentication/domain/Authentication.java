package pl.pollub.edu.cardGame.authentication.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import command.RegisterCommand;

import java.util.Objects;

@Document(collection = "Authentication")
@NoArgsConstructor
@AllArgsConstructor
public class Authentication extends AuthenticationDetails {

    @Id
    private ObjectId id;

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Authentication && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    public Authentication(RegisterCommand command, BCryptPasswordEncoder encoder) {
        this.login = command.getLogin();
        this.changePassword(encoder, command.getPassword());
    }
}
