package com.sojson.core.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sojson.common.model.UUser;
import com.sojson.core.shiro.token.manager.TokenManager;

public class LoginFilter  extends AccessControlFilter {
	
	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	final static Class<LoginFilter> CLASS = LoginFilter.class;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		logger.info("========进入LoginFilter ，isAccessAllowed，mappedValue={}", mappedValue );
		
		UUser token = TokenManager.getToken();
		
		if(null != token || isLoginRequest(request, response)){// && isEnabled()
            return Boolean.TRUE;
        } 
		if (ShiroFilterUtils.isAjax(request)) {// ajax请求
			Map<String,String> resultMap = new HashMap<String, String>();
			logger.info("当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE ;
            
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		logger.info("========进入LoginFilter ，onAccessDenied");
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		logger.info("========进入LoginFilter ，saveRequestAndRedirectToLogin 之后");
		return Boolean.FALSE ;
	}
	

}
