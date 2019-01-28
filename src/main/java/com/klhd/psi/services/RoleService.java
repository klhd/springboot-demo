package com.klhd.psi.services;

import com.google.common.collect.Lists;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.dao.PrivilegeDao;
import com.klhd.psi.dao.RoleDao;
import com.klhd.psi.dao.RolePrivilegeDao;
import com.klhd.psi.dao.UserRoleDao;
import com.klhd.psi.vo.PageResultVO;
import com.klhd.psi.vo.PageVO;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.privilege.PrivilegeVOQuery;
import com.klhd.psi.vo.role.RolePrivilegeVO;
import com.klhd.psi.vo.role.RolePrivilegeVOQuery;
import com.klhd.psi.vo.role.RoleVO;
import com.klhd.psi.vo.role.RoleVOQuery;
import com.klhd.psi.vo.user.UserRoleVOQuery;
import io.swagger.annotations.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@Api(description = "角色管理")
@ControllerPermission(code="user", desc="角色管理")
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;
    @Autowired
    private PrivilegeDao privilegeDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @ApiOperation("创建")
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功"),
    })
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Permission(code = "create", desc = "创建")
    public ResultVO create(
            @RequestBody @ApiParam(name = "角色对象", value = "传入:name", required = true) RoleVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        roleDao.insert(record);
        return resultVO;
    }

    @ApiOperation("删除")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Permission(code = "delete", desc = "删除")
    public ResultVO delete(
            @RequestBody @ApiParam(name = "角色对象", value = "传入:id", required = true) List<RoleVO> list
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        List<Integer> idList = list.stream().map(t -> t.getId()).collect(Collectors.toList());

        //先删除角色
        RoleVOQuery query = new RoleVOQuery();
        query.createCriteria().andIdIn(idList);
        roleDao.deleteByExample(query);

        //删除角色和权限对应关系
        RolePrivilegeVOQuery rpq = new RolePrivilegeVOQuery();
        rpq.createCriteria().andRoleIdIn(idList);
        rolePrivilegeDao.deleteByExample(rpq);

        //删除角色和用户对应关系
        UserRoleVOQuery urq = new UserRoleVOQuery();
        urq.createCriteria().andRoleIdIn(idList);
        userRoleDao.deleteByExample(urq);

        return resultVO;
    }

    @ApiOperation("修改角色名称")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(code = "update", desc = "修改角色名称")
    public ResultVO update(
            @RequestBody @ApiParam(name = "菜单对象", value = "必传:id、name", required = true) RoleVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        roleDao.updateByPrimaryKeySelective(record);
        return resultVO;
    }

    @ApiOperation("调整角色对应的权限")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/saveRolePrivilege", method = RequestMethod.POST)
    @Permission(code = "saveRolePrivilege", desc = "调整角色对应的权限")
    public ResultVO saveRolePrivilege(
            @RequestBody @ApiParam(name = "菜单对象", value = "必传:roleId、privilegeCode(该字段来自权限点明细中）", required = true) List<RolePrivilegeVO> list
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        list.forEach(rp -> rolePrivilegeDao.insert(rp));
        return resultVO;
    }

    @ApiOperation("查找")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curPage", value = "当前页码", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页总条数", dataType = "int", paramType = "path", required = true)
    })
    @RequestMapping(value = "/query/{pageSize}/{curPage}", method = RequestMethod.POST)
    @Permission(code = "query", desc = "查找")
    public PageResultVO query(
            @RequestBody @ApiParam(name = "菜单对象", value = "必传:id，选填：name", defaultValue = "{}", required = true) RoleVO record,
            @PathParam("") PageVO pageVO
    ) throws Exception {
        PageResultVO resultVO = new PageResultVO();
        RoleVOQuery query = new RoleVOQuery();
        query.setStartRow(pageVO.getStartRow());
        query.setPageSize(pageVO.getPageSize());
        //根据名称模糊查询
        if (Strings.isNotEmpty(record.getRoleName())) {
            query.createCriteria().andRoleNameLike("%" + record.getRoleName() + "%");
        }
        List<RoleVO> menus = roleDao.selectByExample(query);
        long count = roleDao.countByExample(query);
        pageVO.setTotalRows(count);
        resultVO.setPageVO(pageVO);
        resultVO.setResult(menus);
        return resultVO;
    }

    @ApiOperation("查明明细")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
    })
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    @Permission(code = "getById", desc = "查明明细")
    public ResultVO getById(
            @RequestBody @ApiParam(name = "菜单对象", value = "必传:id", required = true) RoleVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        RoleVO roleVO = roleDao.selectByPrimaryKey(record.getId());

        RolePrivilegeVOQuery query = new RolePrivilegeVOQuery();
        query.createCriteria().andRoleIdEqualTo(roleVO.getId());
        List<RolePrivilegeVO> rolePrivilegeList = rolePrivilegeDao.selectByExample(query);

        List<Integer> idList = rolePrivilegeList.stream().map(a -> a.getId()).collect(Collectors.toList());
        if(idList.size() > 0) {
            PrivilegeVOQuery pQuery = new PrivilegeVOQuery();
            pQuery.createCriteria().andIdIn(idList);
            List<PrivilegeVO> priList = privilegeDao.selectByExample(pQuery);
            roleVO.setPrivilegeList(priList);
        }else{
            roleVO.setPrivilegeList(Lists.newArrayList());
        }

        resultVO.setResult(roleVO);
        return resultVO;
    }

    @ApiOperation("获取有效的权限点")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
    })
    @RequestMapping(value = "/getAllPrivilegeList", method = RequestMethod.POST)
    @Permission(code = "getAllPrivilegeList", desc = "获取有效的权限点")
    public ResultVO getAllPrivilegeList() throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        List<PrivilegeVO> privilegeVOS = privilegeDao.selectByExample(new PrivilegeVOQuery());
        resultVO.setResult(privilegeVOS);
        return resultVO;
    }
}
