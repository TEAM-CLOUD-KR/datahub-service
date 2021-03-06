/*
    Copyright (c) 2021 Aaron(JIN, Taeyang).
    All rights reserved. This program and the accompanying materials
    are made available under the terms of the GNU Lesser General Public License, version 3
    which accompanies this distribution, and is available at
    https://www.gnu.org/licenses/lgpl-3.0.html
    
    Contributors:
        Aaron(JIN, Taeyang) - 
*/

package kr.dataportal.datahubservice.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();

        Object user = httpSession.getAttribute("user");

        if (user == null) {
            if (request.getRequestURI() == null || request.getRequestURI().isEmpty()) {
                response.sendRedirect("/user");
            } else {
                if (request.getQueryString() == null || request.getQueryString().isEmpty()) {
                    response.sendRedirect("/user?ref=" + request.getRequestURI());
                } else {
                    response.sendRedirect("/user?ref=" + request.getRequestURI() + "?" + request.getQueryString());
                }
            }
            return false;
        }
        return true;
    }
}
