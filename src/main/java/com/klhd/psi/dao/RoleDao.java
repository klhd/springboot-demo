package com.klhd.psi.dao;

import com.klhd.psi.vo.role.RoleVO;
import com.klhd.psi.vo.role.RoleVOQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface RoleDao {
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