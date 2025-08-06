package dev.srello.jwt.core.security;

import dev.srello.jwt.core.exception.RequestException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static dev.srello.jwt.core.messages.Messages.Error.ORIGIN_OR_REFERER_HEADER_MISSING;
import static dev.srello.jwt.core.request.RequestConstants.ENDPOINT_ATTRIBUTE;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OriginFilter implements Filter {

    public static final String ALIVE_ENDPOINT = "/alive";
    private final HandlerExceptionResolver handlerExceptionResolver;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        String endpoint = httpRequest.getRequestURI().substring(7);

        if(ALIVE_ENDPOINT.equals(endpoint)) {
            chain.doFilter(request, response);
            return;
        }

        String origin = ((HttpServletRequest) request).getHeader("Origin");
        String referer = ((HttpServletRequest) request).getHeader("Referer");

        try {
            if (isNull(origin) || isNull(referer))
                throw new RequestException(BAD_REQUEST, ORIGIN_OR_REFERER_HEADER_MISSING);
        } catch (RequestException ex) {
            handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, ex);
            return;
        }
        request.setAttribute(ENDPOINT_ATTRIBUTE, endpoint);
        chain.doFilter(request, response);
    }
}
