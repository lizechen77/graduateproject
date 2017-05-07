package com.xtu.graduate.project.domains;

/**
 * Created by Administrator on 2017/4/30 0030.
 */
public class Role {
    private String roleID;

    private String roleName;

    private String permission1;

    private String permission2;

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermission1() {
        return permission1;
    }

    public void setPermission1(String permission1) {
        this.permission1 = permission1;
    }

    public String getPermission2() {
        return permission2;
    }

    public void setPermission2(String permission2) {
        this.permission2 = permission2;
    }
}
