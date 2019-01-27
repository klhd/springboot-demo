package com.klhd.psi.vo.privilege;

import java.io.Serializable;
import java.util.Date;

public class PrivilegeVO implements Serializable {
    private Integer id;

    private String typeCode;

    private String typeDesc;

    private String methodCode;

    private String methodDesc;

    private Date lastUpdateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc == null ? null : typeDesc.trim();
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode == null ? null : methodCode.trim();
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc == null ? null : methodDesc.trim();
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", typeDesc=").append(typeDesc);
        sb.append(", methodCode=").append(methodCode);
        sb.append(", methodDesc=").append(methodDesc);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}