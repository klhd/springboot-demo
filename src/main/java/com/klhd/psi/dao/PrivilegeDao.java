package com.klhd.psi.dao;

import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.privilege.PrivilegeVOQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PrivilegeDao {
    long countByExample(PrivilegeVOQuery example);

    int deleteByExample(PrivilegeVOQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrivilegeVO record);

    int insertSelective(PrivilegeVO record);

    List<PrivilegeVO> selectByExample(PrivilegeVOQuery example);

    PrivilegeVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PrivilegeVO record, @Param("example") PrivilegeVOQuery example);

    int updateByExample(@Param("record") PrivilegeVO record, @Param("example") PrivilegeVOQuery example);

    int updateByPrimaryKeySelective(PrivilegeVO record);

    int updateByPrimaryKey(PrivilegeVO record);
}