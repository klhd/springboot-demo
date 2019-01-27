package com.klhd.psi.dao;

import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.menu.MenuQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDao {
    long countByExample(MenuQuery example);

    int deleteByExample(MenuQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuQuery example);

    Menu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuQuery example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuQuery example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}