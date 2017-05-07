package com.xtu.graduate.project.rest;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xtu.graduate.project.dao.CommonDao;
import com.xtu.graduate.project.domains.ActivityInfo;
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
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

        if (currentUser.hasRole("student")) {
            session.setAttribute("roleID", "student");
        }
        if (currentUser.hasRole("teacher")) {
            session.setAttribute("roleID", "teacher");
        }
        mav.setViewName("redirect:/department/findSiteApplicationInfo");
        return mav;
    }

    @RequestMapping("/logout")
    public ModelAndView loginOut() {
        return new ModelAndView("/index");
    }

    //游客
    @RequestMapping("findActivityInfo")
    public ModelAndView findActivityInfo(HttpServletRequest request){
        String activityName = request.getParameter("activityName");
        String locale = request.getParameter("locale");
        Date begainTime1;
        Date begainTime2;
        try {
            begainTime1 = sdf.parse(request.getParameter("begainTime1"));
            begainTime2 = sdf.parse(request.getParameter("begainTime2"));
        } catch (ParseException e) {
            LOGGER.info("time format error......");
            return null;
        }
        List<Map<String, Object>> list = this.userService.findActivityInfo(activityName, locale, begainTime1, begainTime2);
        ModelAndView mav = new ModelAndView("activityInfo");
        mav.addObject("activityList", list);
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
        Date begainTime;
        Date endTime;
        try {
            begainTime = sdf.parse(request.getParameter("begainTime"));
            endTime = sdf.parse(request.getParameter("endTime"));
        } catch (ParseException e) {
            LOGGER.info("time format error......");
            return null;
        }
        siteApplication.setSiteManagerID(siteManagerID);
        siteApplication.setSiteID(request.getParameter("siteID"));
        siteApplication.setDetails(request.getParameter("details"));
        siteApplication.setBegainTime(begainTime);
        siteApplication.setEndTime(endTime);
        siteApplication.setDepartmentID(request.getParameter("departmentID"));
        int row = 0;
        try {
            row = this.departmentService.createSiteApplication(siteApplication);
        }   catch (EmptyResultDataAccessException e) {
            LOGGER.info("Create site application error");
        }
        mav.addObject("row", row);
        return mav;
    }


    @RequestMapping("department/findSiteApplicationInfo")
    public ModelAndView findSiteApplicationInfo(HttpServletRequest request) {
        String departmentID = request.getParameter("departmentID");
        String locale = request.getParameter("locale");
        String tempBegainTime1 = request.getParameter("begainTime1");
        String tempBegainTime2 = request.getParameter("begainTime2");
        Date begainTime1 = null;
        Date begainTime2 = null;
        if (StringUtils.isNotBlank(tempBegainTime1) && StringUtils.isNotBlank(tempBegainTime2)) {
            try {
                begainTime1 = sdf.parse(tempBegainTime1);
                begainTime2 = sdf.parse(tempBegainTime2);
            } catch (ParseException e) {
                LOGGER.info("time format error......");
                return null;
            }
        }
        List<Map<String, Object>> list = this.departmentService.findSiteApplicationInfo(departmentID, locale, begainTime1, begainTime2);
        ModelAndView mav;
        if (StringUtils.isBlank(departmentID)) {
            mav = new ModelAndView("department/findSiteApplicationInfo");
        } else {
            mav = new ModelAndView("department/mySiteApplicationInfo");
        }
        mav.addObject("siteApplicationInfoList", list);
        return mav;
    }

    @RequestMapping("department/siteApplicationInstruction")
    public ModelAndView siteApplicationInstruction() {
        return new ModelAndView("department/siteApplicationInstruction");
    }

    //场地管理员
    @RequestMapping("createUser")
    public ModelAndView createUser(HttpServletRequest request) {
        User user = new User();
        user.setUserID(request.getParameter("userID"));
        user.setPassword(request.getParameter("password"));
        user.setRoleID(request.getParameter("roleID"));
        user.setUserName(request.getParameter("userName"));
        int row = this.siteManagerService.createUser(user);
        return null;
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

    @RequestMapping("findUnapproveSiteApplication")
    public ModelAndView findUnapproveSiteApplication() {
        List<Map<String, Object>> list = this.siteManagerService.findUnapproveSiteApplication();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        return mav;
    }

    @RequestMapping("approve")
    public ModelAndView approve(String applicationID, String advice) {
        int row = this.siteManagerService.approve(applicationID, advice);
        return new ModelAndView();
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
