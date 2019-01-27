package com.klhd.psi.dao;

import com.klhd.psi.vo.user.UserVO;
import com.klhd.psi.vo.user.UserVOQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {
    long countByExample(UserVOQuery example);

    int deleteByExample(UserVOQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserVO record);

    int insertSelective(UserVO record);

    List<UserVO> selectByExample(UserVOQuery example);

    UserVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserVO record, @Param("example") UserVOQuery example);

    int updateByExample(@Param("record") UserVO record, @Param("example") UserVOQuery example);

    int updateByPrimaryKeySelective(UserVO record);

    int updateByPrimaryKey(UserVO record);
}