/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */

package com.gome.pricemonitor.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author niulu
 * @date 2013-11-22
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {


    protected boolean checkTicketFromRedis(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String cookieValue = this.cookieUtils.getCookieValue(request, Constants.COMMUNITY_SHOP_PIN_COOKIE);
//        if (StringUtils.isNotBlank(cookieValue)) {
//            UserInfo userInfo = BaseControllor.parseCookie(cookieValue);
//            String key = String.format(CommonCacheImpl.KEY, SysConstants.REDIS_PREFIX, "cookie_" + userInfo.getUserType(), userInfo.getUserId());
//            String cookieRedisValue = commonCache.get(key);
//            if (StringUtils.isBlank(cookieRedisValue)) {
//                ResponseUtils.renderJson(response, JsonUtils.toJSON(new ResponsesDTO(ReturnCode.ERROR_LOGIN_TIMEOUT)));
//                return false;
//            }
//            if (!cookieValue.equals(cookieRedisValue)) {
//                ResponseUtils.renderJson(response, JsonUtils.toJSON(new ResponsesDTO(ReturnCode.ERROR_USER_MORE)));
//                return false;
//            }
//        }

        return true;
    }

}
