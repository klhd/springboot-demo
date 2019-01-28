package com.klhd.psi.vo.org;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.klhd.psi.vo.role.RoleExtVO;

import java.util.List;

public class UserOrgExt extends UserOrg {
    private List<UserOrg> children;

    public static UserOrgExt parse(UserOrg userOrg){
        return JSONObject.parseObject(JSON.toJSONString(userOrg), UserOrgExt.class);
    }

    public List<UserOrg> getChildren() {
        return children;
    }

    public void setChildren(List<UserOrg> children) {
        this.children = children;
    }
}
