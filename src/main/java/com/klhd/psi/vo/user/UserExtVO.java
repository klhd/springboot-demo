package com.klhd.psi.vo.user;

import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.role.RoleVO;

import java.util.List;

public class UserExtVO extends UserVO {
    private String newPwd;
    private String oldPwd;
    private List<Menu> menuList;
    private List<PrivilegeVO> permissionList;
    private List<RoleVO> roleList;

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
}
