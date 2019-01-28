package com.klhd.psi.dao;

import com.klhd.psi.vo.org.UserOrg;
import com.klhd.psi.vo.org.UserOrgQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserOrgDao {
    long countByExample(UserOrgQuery example);

    int deleteByExample(UserOrgQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserOrg record);

    int insertSelective(UserOrg record);

    List<UserOrg> selectByExample(UserOrgQuery example);

    UserOrg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserOrg record, @Param("example") UserOrgQuery example);

    int updateByExample(@Param("record") UserOrg record, @Param("example") UserOrgQuery example);

    int updateByPrimaryKeySelective(UserOrg record);

    int updateByPrimaryKey(UserOrg record);
}