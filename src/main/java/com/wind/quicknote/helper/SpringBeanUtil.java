package com.wind.quicknote.helper;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.zk.ui.Sessions;

public class SpringBeanUtil {
	
	private static Properties props;

	public static Object getBean(String beanName) {
		
		HttpSession session = (HttpSession) Sessions.getCurrent().getNativeSession();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        // ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		return ctx.getBean(beanName);
	}
	
	public static Object getBeanInTest(String beanName) {
		
        @SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml", "dataAccessContext.xml");
		return ctx.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> className) {
		
		HttpSession session = (HttpSession) Sessions.getCurrent().getNativeSession();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		return (T) ctx.getBean(beanName);
	}
	
	public static String getProp(String key) {
		
		if (props == null) {
			try {
				props = PropertiesLoaderUtils.loadAllProperties("config.properties");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        return props.getProperty(key);
	}

}
