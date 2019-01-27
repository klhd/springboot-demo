package com.klhd.psi.dao;

import com.klhd.psi.vo.role.RoleVO;
import com.klhd.psi.vo.role.RoleVOQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleVODao {
    long countByExample(RoleVOQuery example);

    int deleteByExample(RoleVOQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleVO record);

    int insertSelective(RoleVO record);

    List<RoleVO> selectByExample(RoleVOQuery example);

    RoleVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoleVO record, @Param("example") RoleVOQuery example);

    int updateByExample(@Param("record") RoleVO record, @Param("example") RoleVOQuery example);

    int updateByPrimaryKeySelective(RoleVO record);

    int updateByPrimaryKey(RoleVO record);
}