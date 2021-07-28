package io.ryan.platform.demo.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ApiRequestInterceptor
 *
 * @author Ryan
 * @version 1.0.0
 * @date: 2021-07-29 00:02
 */
@Component
@Slf4j
public class ApiRequestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("User-Agent: {}", request.getHeader("User-Agent"));
        return true;
    }
}
