package well_tennis_club.projet.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class AuthenticationFilter extends GenericFilterBean {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private final Token token;

    public AuthenticationFilter(Token token) {
        this.token = token;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        if(!isPermitted(request)) {
            try {
                Authentication authentication = this.getAuthentication((HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exp) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = httpResponse.getWriter();
                writer.print(exp.getMessage());
                writer.flush();
                writer.close();
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPermitted(ServletRequest request) {
        AtomicBoolean isPermitted = new AtomicBoolean(false);
        SecurityConfig.PERMIT_ALL_MATCHERS.forEach(path -> {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(path);
            if (matcher.matches((HttpServletRequest) request)) {
                isPermitted.set(true);
            }
        });
        return isPermitted.get();
    }


    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(token.getToken())) {
            throw new BadCredentialsException("You cannot access this resource.");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

    static class ApiKeyAuthentication extends AbstractAuthenticationToken {
        private final String apiKey;

        public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.apiKey = apiKey;
            setAuthenticated(true);
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return apiKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ApiKeyAuthentication that = (ApiKeyAuthentication) o;
            return Objects.equals(apiKey, that.apiKey);
        }

        @Override
        public int hashCode() {
            return Objects.hash(apiKey);
        }
    }
}