package com.xtu.graduate.project.configrations;

import com.xtu.graduate.project.realm.MyRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/18 0018.
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/index");
        bean.setUnauthorizedUrl("/unauthor");
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("anon", new AnonymousFilter());
        filters.put("authc", new FormAuthenticationFilter());
        filters.put("logout", new LogoutFilter());
        filters.put("roles", new RolesAuthorizationFilter());
        filters.put("user", new UserFilter());
        bean.setFilters(filters);

        Map<String, String> chains = new HashMap<String, String>();
        chains.put("/test", "anon");
        chains.put("/index", "anon");
        chains.put("/login", "anon");
        chains.put("/unauthor", "anon");
        chains.put("/css/**", "anon");
        chains.put("/img/**", "anon");
        chains.put("/fonts/**", "anon");
        chains.put("/js/**", "anon");
        chains.put("/logout", "logout");
        chains.put("/permissionTest", "authc,roles[admin]");
        chains.put("/department/**", "authc, roles[student]");
        chains.put("/siteManager/**", "authc, roles[siteManager]");
        chains.put("/**", "user");
        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return new DefaultShiroFilterChainDefinition();
    }



    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());
        return manager;
    }


    @Bean
    @DependsOn(value={"lifecycleBeanPostProcessor"})
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        myRealm.setAuthenticationCachingEnabled(true);
        myRealm.setAuthorizationCachingEnabled(true);
        return myRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
