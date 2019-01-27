package com.klhd.psi.services;

import com.google.common.collect.Lists;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.dao.MenuDao;
import com.klhd.psi.vo.PageResultVO;
import com.klhd.psi.vo.PageVO;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.menu.MenuQuery;
import io.swagger.annotations.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/menu")
@Api(description = "菜单管理")
@ControllerPermission(code = "menu", desc = "菜单管理")
public class MenuService {
    @Autowired
    private MenuDao menuDao;

    @ApiOperation("创建")
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功"),
    })
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Permission(code = "create", desc = "创建")
    public ResultVO create(
            @RequestBody @ApiParam(name = "菜单对象", value = "传入:name、openType、parentId、url、privilegeCode", required = true) Menu menu
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        menuDao.insert(menu);
        return resultVO;
    }

    @ApiOperation("删除")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Permission(code = "delete", desc = "删除")
    public ResultVO delete(
            @RequestBody @ApiParam(name = "菜单对象", value = "传入:id", required = true) List<Menu> list
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        List<Integer> idList = Lists.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            Menu menu = list.get(i);
            idList.add(menu.getId());
        }
        MenuQuery menuQuery = new MenuQuery();
        menuQuery.createCriteria().andIdIn(idList);
        menuDao.deleteByExample(menuQuery);
        return resultVO;
    }

    @ApiOperation("修改")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(code = "update", desc = "修改")
    public ResultVO update(
            @RequestBody @ApiParam(name = "菜单对象", value = "必传:id，选填：name、openType、parentId、url、privilegeCode", required = true) Menu menu
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        menuDao.updateByPrimaryKey(menu);
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
            @RequestBody @ApiParam(name = "菜单对象", value = "必传:id，选填：name、openType、parentId、url、privilegeCode", defaultValue = "{}", required = true) Menu menu,
            @PathParam("") @ApiParam(name="菜单对象",value="传入:name、openType、parentId、url、privilegeCode",required=true) PageVO pageVO
    ) throws Exception {
        PageResultVO resultVO = new PageResultVO();
        MenuQuery menuQuery = new MenuQuery();
        menuQuery.setStartRow(pageVO.getStartRow());
        menuQuery.setPageSize(pageVO.getPageSize());
        //根据名称模糊查询
        if (Strings.isNotEmpty(menu.getName())) {
            menuQuery.createCriteria().andNameLike("%" + menu.getName() + "%");
        }
        //根据父菜单ID查询
        if (menu.getParentId() != null) {
            menuQuery.createCriteria().andParentIdEqualTo(menu.getParentId());
        }
        List<Menu> menus = menuDao.selectByExample(menuQuery);
        long count = menuDao.countByExample(menuQuery);
        resultVO.setPageVO(pageVO);
        resultVO.setResult(menus);
        return resultVO;
    }
}
