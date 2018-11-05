package pl.pollub.edu.cardGame.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.LoginCommand;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private String login;
    private String password;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password;
        String contentType = request.getHeader("Content-Type");
        if (isJson(contentType)) {
            password = this.password;
        }else{
            password = super.obtainPassword(request);
        }

        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request){
        String login;
        String contentType = request.getHeader("Content-Type");
        if (isJson(contentType)) {
            login = this.login;
        }else{
            login = super.obtainUsername(request);
        }

        return login;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getHeader("Content-Type");
        if (isJson(contentType)) {
            try {

                StringBuffer sb = new StringBuffer();
                String line;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                LoginCommand loginRequest = mapper.readValue(sb.toString(), LoginCommand.class);

                this.login = loginRequest.getLogin();
                this.password = loginRequest.getPassword();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }

    private boolean isJson(String contentType) {
        return "application/json".equals(contentType) || "application/json; charset=utf-8".equals(contentType);
    }

}
