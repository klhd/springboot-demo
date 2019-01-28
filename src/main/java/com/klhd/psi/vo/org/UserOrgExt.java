package com.klhd.psi.vo.org;

import java.util.List;

public class UserOrgExt extends UserOrg {
    private List<UserOrg> children;

    public List<UserOrg> getChildren() {
        return children;
    }

    public void setChildren(List<UserOrg> children) {
        this.children = children;
    }
}
