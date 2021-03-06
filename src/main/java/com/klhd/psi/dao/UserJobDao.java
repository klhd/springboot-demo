package com.klhd.psi.dao;

import com.klhd.psi.vo.job.UserJob;
import com.klhd.psi.vo.job.UserJobQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserJobDao {
    long countByExample(UserJobQuery example);

    int deleteByExample(UserJobQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserJob record);

    int insertSelective(UserJob record);

    List<UserJob> selectByExample(UserJobQuery example);

    UserJob selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserJob record, @Param("example") UserJobQuery example);

    int updateByExample(@Param("record") UserJob record, @Param("example") UserJobQuery example);

    int updateByPrimaryKeySelective(UserJob record);

    int updateByPrimaryKey(UserJob record);
}