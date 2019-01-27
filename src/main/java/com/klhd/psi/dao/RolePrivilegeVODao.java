package com.klhd.psi.dao;

import com.klhd.psi.vo.role.RolePrivilegeVO;
import com.klhd.psi.vo.role.RolePrivilegeVOQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePrivilegeVODao {
    long countByExample(RolePrivilegeVOQuery example);

    int deleteByExample(RolePrivilegeVOQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePrivilegeVO record);

    int insertSelective(RolePrivilegeVO record);

    List<RolePrivilegeVO> selectByExample(RolePrivilegeVOQuery example);

    RolePrivilegeVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePrivilegeVO record, @Param("example") RolePrivilegeVOQuery example);

    int updateByExample(@Param("record") RolePrivilegeVO record, @Param("example") RolePrivilegeVOQuery example);

    int updateByPrimaryKeySelective(RolePrivilegeVO record);

    int updateByPrimaryKey(RolePrivilegeVO record);
}