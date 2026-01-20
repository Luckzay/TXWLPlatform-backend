package com.txwl.txwlplatform.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 开始计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 将StopWatch存储到request属性中，以便在postHandle中使用
        request.setAttribute("stopWatch", stopWatch);

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String params = extractParameters(request);

        logger.info("REQUEST: {} {}{} | Parameters: {}", method, uri, queryString != null ? "?" + queryString : "", params);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        StopWatch stopWatch = (StopWatch) request.getAttribute("stopWatch");
        if (stopWatch != null) {
            stopWatch.stop();
            long executionTime = stopWatch.getTotalTimeMillis();

            String method = request.getMethod();
            String uri = request.getRequestURI();
            int statusCode = response.getStatus();

            logger.info("RESPONSE: {} {} | Status: {} | Time: {}ms", method, uri, statusCode, executionTime);
        }
    }

    private String extractParameters(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        params.append("{");

        boolean first = true;
        for (String paramName : request.getParameterMap().keySet()) {
            if (!first) {
                params.append(", ");
            }
            String[] values = request.getParameterMap().get(paramName);
            params.append(paramName).append("=").append(Arrays.toString(values));
            first = false;
        }

        // 对于POST请求，尝试读取请求体
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            try {
                String contentType = request.getContentType();
                if (contentType != null && contentType.contains("application/json")) {
                    // 这里不能直接读取body，因为在过滤器链中可能会被消耗
                    params.append(" [JSON Body received]");
                } else if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {
                    // 参数已经在getParameterMap中了
                }
            } catch (Exception e) {
                logger.error("Error reading request body", e);
            }
        }

        params.append("}");
        return params.toString();
    }
}