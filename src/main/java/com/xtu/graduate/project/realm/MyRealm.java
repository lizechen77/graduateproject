package com.xtu.graduate.project.realm;

import com.xtu.graduate.project.dao.CommonDao;
import com.xtu.graduate.project.dao.SiteManagerDao;
import com.xtu.graduate.project.dao.UserDao;
import com.xtu.graduate.project.domains.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/3/18 0018.
 */

@Component
public class MyRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    CommonDao commonDao;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException{
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userID = (String)upToken.getPrincipal();
        String password = new String((char[])upToken.getCredentials());
        LOGGER.info("Authenticating...userID = {}, password = {}", userID, password);
        User user = this.commonDao.findUserByUserID(userID);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return new SimpleAuthenticationInfo(userID, password, this.getName());
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        String userID = (String)getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = this.commonDao.findUserByUserID(userID);
        String role = user.getRoleID();
        LOGGER.info("Authorizating...userID = {}, roleID = {}", user.getUserID(), role);
        info.addRole(role);
        return info;
    }


}