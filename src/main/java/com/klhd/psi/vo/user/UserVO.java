package com.klhd.psi.vo.user;

import java.io.Serializable;
import java.util.Date;

public class UserVO implements Serializable {
    private Integer id;

    /**
     * 登陆账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String password;

    private String userName;

    private String email;

    /**
     * 联系电话
     */
    private String telNum;

    /**
     * 部门
     */
    private Integer deptId;

    /**
     * 岗位
     */
    private String jobId;

    /**
     * 账号状态：启用、停用
     */
    private String accountState;

    /**
     * 用户状态：工作、休假、离职
     */
    private String userState;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idNum;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 头像
     */
    private String photo;

    private Date lastUpdateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum == null ? null : telNum.trim();
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState == null ? null : accountState.trim();
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState == null ? null : userState.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
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
        sb.append(", userAccount=").append(userAccount);
        sb.append(", password=").append(password);
        sb.append(", userName=").append(userName);
        sb.append(", email=").append(email);
        sb.append(", telNum=").append(telNum);
        sb.append(", deptId=").append(deptId);
        sb.append(", jobId=").append(jobId);
        sb.append(", accountState=").append(accountState);
        sb.append(", userState=").append(userState);
        sb.append(", address=").append(address);
        sb.append(", idNum=").append(idNum);
        sb.append(", birthday=").append(birthday);
        sb.append(", photo=").append(photo);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}