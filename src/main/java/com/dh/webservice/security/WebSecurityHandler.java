/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * @title Spring Security Handler 설정 파일
 * @author cdh
 * @FileName : WebSecurityHandler
 *
 */
@Component
public class WebSecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {


        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            handle(request, response, authentication);
            clearAuthenticationAttributes(request);
        }

        protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            String targetUrl = determineTargetUrl(authentication);

            if (response.isCommitted()) {
                System.out.println("Response has already been committed. Unable to redirect to : targetUrl -> " + targetUrl);
                return;
            }
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        protected void clearAuthenticationAttributes(HttpServletRequest request) {
            System.out.println("clearAuthenticationAttributes()");
            HttpSession session = request.getSession(false);
            if (session == null) {
                return;
            }
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }


        protected String determineTargetUrl(Authentication authentication) {
            boolean isUser = false;
            boolean isAdmin = false;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            System.out.println("determineTargetUrl()");
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("USER")) {
                    isUser = true;
                    break;
                } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
            if (isUser) {
                return "/main";
            } else if (isAdmin) {
                return "/admin/index";
            } else {
                throw new IllegalStateException();
            }

        }

        // 로그인 실패 Handler
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            // exception 에 따른 실패 메시지 분기처리 필요
            response.sendRedirect("/user/login?error=1");
        }

}