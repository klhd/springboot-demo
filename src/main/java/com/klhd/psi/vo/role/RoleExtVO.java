package com.klhd.psi.vo.role;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.klhd.psi.vo.privilege.PrivilegeVO;

import java.util.List;

public class RoleExtVO extends RoleVO {
    private List<PrivilegeVO> privilegeList;

    public static RoleExtVO parse(RoleVO roleVO){
        return JSONObject.parseObject(JSON.toJSONString(roleVO), RoleExtVO.class);
    }
    public List<PrivilegeVO> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<PrivilegeVO> privilegeList) {
        this.privilegeList = privilegeList;
    }
}
