package com.company.project.common.shiro;

import com.alibaba.fastjson.JSON;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.Constant;
import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


/**
 * 自定义过滤器
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {

    final static List<String> allow_urls = Arrays.asList("/", "/cook", "/cook/home", "/cook/", "/cook/login", "/cook/register"
    , "/sys/dishes/pageList", "/sys/order/pageList", "/user/api/logout");

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        log.info("isAccessAllowed getRequestURI={}", requestURI);

        if (allow_urls.contains(requestURI)) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if (allow_urls.contains(requestURI)) {
            return true;
        }


        boolean ajaxRequest = HttpContextUtils.isAjaxRequest(request);
        try {
            // Subject subject = getSubject(servletRequest, servletResponse);
            log.info("onAccessDenied getRequestURI={} getRequestURL={}, method={} ajaxRequest={}", requestURI
                    , request.getRequestURL().toString(), request.getMethod(), ajaxRequest);

            // 从header中获取token
            String token = request.getHeader(Constant.ACCESS_TOKEN);
            // 如果header中不存在token，则从参数中获取token
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter(Constant.ACCESS_TOKEN);
            }

            if (StringUtils.isEmpty(token)) {
                throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
            }
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(token, token);
            getSubject(servletRequest, servletResponse).login(usernamePasswordToken);
        } catch (BusinessException exception) {
            exception.printStackTrace();
            log.error("用户登录异常:{}", exception.getMessage());

            if (ajaxRequest) {
                customResponse(exception.getMessageCode(), exception.getDetailMessage(), servletResponse);
            } else if (exception.getMessageCode() == BaseResponseCode.TOKEN_ERROR.getCode()) {
                servletRequest.getRequestDispatcher("/index/login").forward(servletRequest, servletResponse);
            } else if (exception.getMessageCode() == BaseResponseCode.UNAUTHORIZED_ERROR.getCode()) {
                servletRequest.getRequestDispatcher("/index/403").forward(servletRequest, servletResponse);
            } else {
                servletRequest.getRequestDispatcher("/index/500").forward(servletRequest, servletResponse);
            }


            return false;
        } catch (AuthenticationException e) {
            if (ajaxRequest) {
                if (e.getCause() instanceof BusinessException) {
                    BusinessException exception = (BusinessException) e.getCause();
                    customResponse(exception.getMessageCode(), exception.getDetailMessage(), servletResponse);
                } else {
                    customResponse(BaseResponseCode.SYSTEM_BUSY.getCode(), BaseResponseCode.SYSTEM_BUSY.getMsg(), servletResponse);
                }
            } else {
                servletRequest.getRequestDispatcher("/index/403").forward(servletRequest, servletResponse);
            }
            return false;
        } catch (Exception e) {
            if (ajaxRequest) {
                if (e.getCause() instanceof BusinessException) {
                    BusinessException exception = (BusinessException) e.getCause();
                    customResponse(exception.getMessageCode(), exception.getDetailMessage(), servletResponse);
                } else {
                    customResponse(BaseResponseCode.SYSTEM_BUSY.getCode(), BaseResponseCode.SYSTEM_BUSY.getMsg(), servletResponse);
                }
            } else {
                servletRequest.getRequestDispatcher("/index/500").forward(servletRequest, servletResponse);
            }
            return false;
        }

        return true;
    }

    private void customResponse(int code, String msg, ServletResponse response) {
        try {
            DataResult result = DataResult.getResult(code, msg);

            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            log.error("error={}", e, e);
        }
    }

}
