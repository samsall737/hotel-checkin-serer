package com.nec.hotels.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nec.hotels.enums.UserType;
import com.nec.hotels.utils.Constants;
import com.nec.oauth.gateway.AuthGateway;
import com.nec.oauth.model.Token;


@Component
@Order(1)
public class RoleFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleFilter.class);

	@Autowired
	private AuthGateway authGateway;

	private String excludeUrls = ".+login.*|.+register.*|.+booking.*";
	private String hotelFilterUrls = ".hotel.*";


	private Pattern excludeFilterUrlsPattern = null;
	private Pattern hotelFilterUrlsPattern = null;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		excludeFilterUrlsPattern = Pattern.compile(excludeUrls);
		hotelFilterUrlsPattern = Pattern.compile(hotelFilterUrls);
		
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		if ("OPTIONS".equals(req.getMethod())) {
			return;
		}

		final String path = req.getServletPath();

		final Matcher isExcludeMatch = excludeFilterUrlsPattern.matcher(path);
		final Matcher isHotelMatch =hotelFilterUrlsPattern.matcher(path);
		
		if (!isExcludeMatch.matches() && (isHotelMatch.matches() && !req.getMethod().equalsIgnoreCase("get"))) {
			final String authToken = req.getHeader(Constants.HEADER_AUTHORIZATION);
			final String userAgent = req.getHeader(Constants.HEADER_USER_AGENT);
			final String userId = req.getHeader(Constants.HEADER_USERID);
			if (StringUtils.isEmpty(authToken) || StringUtils.isEmpty(userAgent) || StringUtils.isEmpty(userId)) {
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}

			final Token token = authGateway
					.verifyAndDecodeAuthToken(new Token(userId, userAgent, authToken, null, null, null, null));
			if (token == null) {
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			} else {
				boolean allowFlag = false;
				String userRole = token.getUserType();
				if (userRole.equals(UserType.HOTEL_ADMIN)) {
					allowFlag = true;
				}
				//String accessToken = token.getAccessToken();
				
				if (!allowFlag) {
					res.setStatus(HttpStatus.FORBIDDEN.value());
					return;
				}
			}
			LOGGER.info("embedded role adminFilter:: {}", token.getUserType());
		}
		
		if(path.endsWith("booking") && !path.contains("sync") && req.getMethod().equalsIgnoreCase("get") ) {
			final String authToken = req.getHeader(Constants.HEADER_AUTHORIZATION);
			final String userAgent = req.getHeader(Constants.HEADER_USER_AGENT);
			final String userId = req.getHeader(Constants.HEADER_USERID);
			if (StringUtils.isEmpty(authToken) || StringUtils.isEmpty(userAgent) || StringUtils.isEmpty(userId)) {
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}

			final Token token = authGateway
					.verifyAndDecodeAuthToken(new Token(userId, userAgent, authToken, null, null, null, null));
			if (token == null) {
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			} 
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}