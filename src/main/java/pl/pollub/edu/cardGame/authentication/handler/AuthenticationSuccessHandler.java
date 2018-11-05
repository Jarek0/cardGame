package pl.pollub.edu.cardGame.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import response.AuthenticatedResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    public AuthenticationSuccessHandler(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) throws IOException {

        pl.pollub.edu.cardGame.authentication.domain.Authentication authentication = (pl.pollub.edu.cardGame.authentication.domain.Authentication) auth.getPrincipal();

        HttpSession session = request.getSession();

        session.setAttribute("login", authentication.getUsername());
        session.setAttribute("password", authentication.getPassword());

        AuthenticatedResponse res = new AuthenticatedResponse(authentication.getUsername());

        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer, res);
        response.setStatus(HttpServletResponse.SC_OK);

        writer.flush();
    }

}
