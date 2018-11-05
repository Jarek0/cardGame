package pl.pollub.edu.cardGame.authentication.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static error.codes.ErrorCodes.BAD_CREDENTIALS;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Map<String,String> messagesMap = new HashMap<String,String>() {{
        put("Bad credentials", BAD_CREDENTIALS);
    }};

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        String exceptionMessage = exception.getMessage();
        writer.write(messagesMap.getOrDefault(exceptionMessage, exceptionMessage));
        writer.flush();
    }

}
