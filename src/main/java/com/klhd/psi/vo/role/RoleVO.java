package com.klhd.psi.vo.role;

import com.klhd.psi.vo.privilege.PrivilegeVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleVO implements Serializable {
    private Integer id;

    private String roleName;

    private List<PrivilegeVO> privilegeList;

    private Date lastUpdateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<PrivilegeVO> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<PrivilegeVO> privilegeList) {
        this.privilegeList = privilegeList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleName=").append(roleName);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}