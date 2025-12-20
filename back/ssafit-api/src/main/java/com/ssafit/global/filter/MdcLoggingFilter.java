package com.ssafit.global.filter;

import static com.ssafit.global.filter.MdcKey.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcLoggingFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request
			, final ServletResponse response
			, final FilterChain chain)
			throws IOException, ServletException {
		setMdc((HttpServletRequest) request);
		chain.doFilter(request, response);
		MDC.clear();
	}
	
	private void setMdc(final HttpServletRequest request) {
		MDC.put(REQUEST_ID.name(), UUID.randomUUID().toString());
		MDC.put(REQUEST_METHOD.name(), request.getMethod());
		MDC.put(REQUEST_URI.name(), request.getRequestURI());
		MDC.put(REQUEST_TIME.name(), LocalDateTime.now().toString());
		MDC.put(REQUEST_IP.name(), request.getRemoteAddr());
	}
	
}
