package com.klhd.psi.dao;

import com.klhd.psi.vo.dept.UserDept;
import com.klhd.psi.vo.dept.UserDeptQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDeptDao {
    long countByExample(UserDeptQuery example);

    int deleteByExample(UserDeptQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserDept record);

    int insertSelective(UserDept record);

    List<UserDept> selectByExample(UserDeptQuery example);

    UserDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserDept record, @Param("example") UserDeptQuery example);

    int updateByExample(@Param("record") UserDept record, @Param("example") UserDeptQuery example);

    int updateByPrimaryKeySelective(UserDept record);

    int updateByPrimaryKey(UserDept record);
}