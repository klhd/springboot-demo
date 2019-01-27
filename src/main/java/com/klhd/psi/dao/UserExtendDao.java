package com.klhd.psi.dao;

import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.user.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserExtendDao {
    List<UserRoleVO> getUserRoleList(Integer id);
    List<PrivilegeVO> getUserPrivList(Integer id);
    List<Menu> getUserMenuList(Integer id);
}
