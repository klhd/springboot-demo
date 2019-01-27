package com.klhd.psi.dao;

import com.klhd.psi.vo.user.UserRoleVO;
import com.klhd.psi.vo.user.UserRoleVOQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleVODao {
    long countByExample(UserRoleVOQuery example);

    int deleteByExample(UserRoleVOQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleVO record);

    int insertSelective(UserRoleVO record);

    List<UserRoleVO> selectByExample(UserRoleVOQuery example);

    UserRoleVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleVO record, @Param("example") UserRoleVOQuery example);

    int updateByExample(@Param("record") UserRoleVO record, @Param("example") UserRoleVOQuery example);

    int updateByPrimaryKeySelective(UserRoleVO record);

    int updateByPrimaryKey(UserRoleVO record);
}