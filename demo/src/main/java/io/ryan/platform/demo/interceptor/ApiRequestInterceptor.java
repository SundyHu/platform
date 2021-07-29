package io.ryan.platform.demo.interceptor;


import cn.hutool.cron.CronUtil;
import io.ryan.platform.demo.annotations.RequestLimit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ApiRequestInterceptor
 *
 * @author Ryan
 * @version 1.0.0
 * @date: 2021-07-29 00:02
 */
@Component
@Slf4j
public class ApiRequestInterceptor implements HandlerInterceptor {

    private static volatile ConcurrentMap<String, RequestInfo> REQUEST_URLS = new ConcurrentHashMap<>();

    private static volatile AtomicInteger requestTimes = new AtomicInteger(0);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("User-Agent: {}", request.getHeader("User-Agent"));
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            RequestLimit requestLimit = handlerMethod.getMethodAnnotation(RequestLimit.class);
            if (null != requestLimit) {
                String url = request.getRequestURI();
                log.info("url - {} - {}", url, requestLimit);

                if (REQUEST_URLS.containsKey(url)) {
                    RequestInfo requestInfo = REQUEST_URLS.get(url);
                    LocalDateTime now = LocalDateTime.now();
                    if (now.isAfter(requestInfo.expirationTime)) {
                        log.info("重新添加到缓存中");
                        REQUEST_URLS.remove(url);

                        //TODO 重新添加到缓存中
                        LocalDateTime startTime = LocalDateTime.now();
                        LocalDateTime expirationTime = startTime.plus(1, ChronoUnit.SECONDS);

                        requestInfo = new RequestInfo();
                        requestInfo.setUrl(url);
                        requestInfo.setRequestTimes(requestTimes.addAndGet(1));
                        requestInfo.setStartTime(startTime);
                        requestInfo.setExpirationTime(expirationTime);
                        REQUEST_URLS.put(url, requestInfo);
                    } else {
                        if (requestTimes.addAndGet(1) > requestLimit.value()) {
                            throw new IllegalArgumentException("超过请求次数");
                        } else {
                            //TODO 更新请求次数
                        }
                    }


                } else {
                    LocalDateTime startTime = LocalDateTime.now();
                    LocalDateTime expirationTime = startTime.plus(1, ChronoUnit.SECONDS);

                    RequestInfo requestInfo = new RequestInfo();
                    requestInfo.setUrl(url);
                    requestInfo.setRequestTimes(requestTimes.addAndGet(1));
                    requestInfo.setStartTime(startTime);
                    requestInfo.setExpirationTime(expirationTime);
                    REQUEST_URLS.put(url, requestInfo);
                }
            }


        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Data
    private static class RequestInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        private String url;

        private LocalDateTime startTime;

        private LocalDateTime expirationTime;

        private int requestTimes;
    }
}
