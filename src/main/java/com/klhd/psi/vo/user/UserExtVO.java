package com.klhd.psi.vo.user;

import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.org.UserOrg;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.role.RoleVO;

import java.util.List;

public class UserExtVO extends UserVO {
    private String newPwd;
    private String oldPwd;
    private List<Menu> menuList;
    private List<PrivilegeVO> permissionList;
    private List<RoleVO> roleList;
    private UserOrg userOrg;

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<PrivilegeVO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PrivilegeVO> permissionList) {
        this.permissionList = permissionList;
    }

    public List<RoleVO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleVO> roleList) {
        this.roleList = roleList;
    }

    public UserOrg getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(UserOrg userOrg) {
        this.userOrg = userOrg;
    }
}
