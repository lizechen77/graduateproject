package com.xtu.graduate.project.rest;

import com.xtu.graduate.project.domains.CurrentPage;
import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;
import com.xtu.graduate.project.service.CommonService;
import com.xtu.graduate.project.service.DepartmentService;
import com.xtu.graduate.project.service.SiteManagerService;
import com.xtu.graduate.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
@RestController
public class GraduateProjectREST {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GraduateProjectREST.class);

    @Autowired
    UserService userService;
    @Autowired
    SiteManagerService siteManagerService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    CommonService commonService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //@RequestMapping("/login")
    //public ModelAndView login() {
    //    return null;
    //}

    @RequestMapping("/unauthor")
    public ModelAndView unauthor() {
        return new ModelAndView("unauthor");
    }

    @RequestMapping("/index")
    public ModelAndView index() { return new ModelAndView("index"); }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(userID,password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession(true);
        Session session = currentUser.getSession();
        try {
            currentUser.login(token);
            session.setAttribute("userID", userID);
        } catch (AuthenticationException e) {
            LOGGER.info("authenticate default......");
            mav.addObject("errorInfo", "authenticationException");
            mav.setViewName("/loginDefault");
            return mav;
        }
        LOGGER.info("authenticate success......");
        //mav.addObject("userID", userID);
        if (currentUser.hasRole("部门用户")) {
            LOGGER.info("Department login");
            session.setAttribute("roleID", "部门用户");
            mav.setViewName("redirect:/department/findSiteApplicationInfo");
        }
        if (currentUser.hasRole("场地管理员")) {
            LOGGER.info("Site Manager login");
            session.setAttribute("roleID", "场地管理员");
            mav.setViewName("redirect:/siteManager/findApprovedSiteApplication");
        }
        return mav;
    }

    @RequestMapping("/logout")
    public ModelAndView loginOut() {
        return new ModelAndView("/index");
    }

    //游客
    @RequestMapping(value = "findActivityInfo")
    public ModelAndView findActivityInfo(HttpServletRequest request){
        String activityName = request.getParameter("activityName");
        String locale = request.getParameter("locale");
        String tempBeginTime1 = request.getParameter("beginTime1");
        String tempBeginTime2 = request.getParameter("beginTime2");
        int pageNumber = 1;
        LOGGER.info("activityName = {}", activityName);
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        Date beginTime1 = null;
        Date beginTime2 = null;
        if (StringUtils.isNotBlank(tempBeginTime1) && StringUtils.isNotBlank(tempBeginTime2)) {
            try {
                beginTime1 = sdf.parse(tempBeginTime1);
                beginTime2 = sdf.parse(tempBeginTime2);
            } catch (ParseException e) {
                LOGGER.info("time format error......");
                return null;
            }
        }
        CurrentPage page = this.userService.findActivityInfo(activityName, locale, beginTime1, beginTime2, pageNumber);
        ModelAndView mav = new ModelAndView("findActivityInfo");
        mav.addObject("page", page);
        return mav;
    }






    //部门用户
    @RequestMapping(value = "department/createSiteApplication",method = RequestMethod.GET)
    public ModelAndView createSiteApplication() {
        return new ModelAndView("department/createSiteApplication");
    }

    @RequestMapping(value = "department/createSiteApplication",method = RequestMethod.POST)
    public ModelAndView createSiteApplication(HttpServletRequest request) {
        LOGGER.info("正在创建场地申请表");
        SiteInfo siteInfo = new SiteInfo();
        siteInfo = this.commonService.findSiteInfoBySiteID(request.getParameter("siteID"));
        ModelAndView mav = new ModelAndView();
        if (siteInfo == null) {
            mav.addObject("errorMessage", "该场地不存在");
            return mav;
        }
        String siteManagerID = siteInfo.getSiteManagerID();
        SiteApplication siteApplication = new SiteApplication();
        Date beginTime;
        Date endTime;
        try {
            beginTime = sdf.parse(request.getParameter("beginTime"));
            endTime = sdf.parse(request.getParameter("endTime"));
        } catch (ParseException e) {
            LOGGER.info("time format error......");
            return null;
        }
        siteApplication.setSiteManagerID(siteManagerID);
        siteApplication.setSiteID(request.getParameter("siteID"));
        siteApplication.setDetails(request.getParameter("details"));
        siteApplication.setActivityName(request.getParameter("activityName"));
        LOGGER.info("activityName = {}", siteApplication.getActivityName());
        siteApplication.setBeginTime(beginTime);
        siteApplication.setEndTime(endTime);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession(true);
        Session session = currentUser.getSession();
        String departmentID = (String)session.getAttribute("userID");
        siteApplication.setDepartmentID(departmentID);
        int row = 0;
        try {
            row = this.departmentService.createSiteApplication(siteApplication);
        }   catch (EmptyResultDataAccessException e) {
            LOGGER.info("Create site application error");
        }
        mav.setViewName("redirect:/department/mySiteApplicationInfo");
        mav.addObject("row", row);
        LOGGER.info("场地申请表创建成功");
        return mav;
    }


    @RequestMapping("department/findSiteApplicationInfo")
    public ModelAndView findSiteApplicationInfo(HttpServletRequest request) {
        String locale = request.getParameter("locale");
        String tempBeginTime1 = request.getParameter("beginTime1");
        String tempBeginTime2 = request.getParameter("beginTime2");
        Date beginTime1 = null;
        Date beginTime2 = null;
        if (StringUtils.isNotBlank(tempBeginTime1) && StringUtils.isNotBlank(tempBeginTime2)) {
            try {
                beginTime1 = sdf.parse(tempBeginTime1);
                beginTime2 = sdf.parse(tempBeginTime2);
            } catch (ParseException e) {
                LOGGER.info("time format error......");
                return null;
            }
        }
        int pageNumber = 1;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        CurrentPage page = this.departmentService.findSiteApplicationInfo(null, locale, beginTime1, beginTime2, pageNumber);
        ModelAndView mav = new ModelAndView("department/findSiteApplicationInfo");
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping("department/mySiteApplicationInfo")
    public ModelAndView mySiteApplication(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession(true);
        Session session = currentUser.getSession();
        String departmentID = (String)session.getAttribute("userID");
        int pageNumber = 1;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        CurrentPage page = this.departmentService.findSiteApplicationInfo(departmentID, null, null, null, pageNumber);
        ModelAndView mav = new ModelAndView("department/mySiteApplicationInfo");
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping("department/siteApplicationInstruction")
    public ModelAndView siteApplicationInstruction() {
        return new ModelAndView("department/siteApplicationInstruction");
    }








    //场地管理员

    @RequestMapping("siteManager/findUnapproveSiteApplication")
    public ModelAndView siteManager(HttpServletRequest request) {
        int pageNumber = 1;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        CurrentPage page = this.siteManagerService.findUnapproveSiteApplication(pageNumber);
        ModelAndView mav = new ModelAndView("siteManager/findUnapproveSiteApplication");
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping(value = "siteManager/createUser", method = RequestMethod.GET)
    public ModelAndView createUser() {
        return new ModelAndView("siteManager/createUser");
    }

    @RequestMapping(value = "siteManager/createUser", method=RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request) {
        User user = new User();
        user.setUserID(request.getParameter("userID"));
        User tempUser = this.commonService.findUserByUserID(request.getParameter("userID"));
        String info = "";
        if (tempUser != null) {
            info = "创建user失败，userID已经存在";
            return new ModelAndView("siteManager/createUserResult");
        }
        user.setPassword(request.getParameter("password"));
        user.setRoleID(request.getParameter("roleID"));
        user.setUserName(request.getParameter("userName"));
        int row = this.siteManagerService.createUser(user);
        info = "创建user成功";
        return new ModelAndView("siteManager/createUserResult");
    }

    @RequestMapping("findUser")
    public ModelAndView findUser(String userID) {
        User user = this.commonService.findUserByUserID(userID);
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping("deleteUser")
    public ModelAndView deleteUser(String userID) {
        int row = this.siteManagerService.deleteUserByUserID(userID);
        return new ModelAndView();
    }

    @RequestMapping("siteManager/findApprovedSiteApplication")
    public ModelAndView findUnapproveSiteApplication(HttpServletRequest request) {
        int pageNumber = 1;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        CurrentPage page = this.siteManagerService.findApprovedSiteApplication(pageNumber);
        ModelAndView mav = new ModelAndView("siteManager/findApprovedSiteApplication");
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping("siteManager/approve")
    public ModelAndView approve(HttpServletRequest request) {
        int applicationID = Integer.valueOf(request.getParameter("applicationID"));
        String status = request.getParameter("status");
        int row = this.siteManagerService.approve(applicationID, status);
        return new ModelAndView("redirect:/siteManager/findUnapproveSiteApplication");
    }

















    @RequiresRoles("admin")
    @RequestMapping("permissionTest")
    public ModelAndView permissionTest() {
        ModelAndView mav = new ModelAndView("permissionTest");
        mav.addObject("role", "admin");
        return mav;
    }

    @RequestMapping("test")
    public String test() {
        String sql = "delete from user where userID = 'student'";
        int rows = this.jdbcTemplate.update(sql);
        return ""+rows;
    }
}
