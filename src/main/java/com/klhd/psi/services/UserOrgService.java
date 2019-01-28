package com.klhd.psi.services;

import com.google.common.collect.Lists;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.dao.UserOrgDao;
import com.klhd.psi.vo.PageResultVO;
import com.klhd.psi.vo.PageVO;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.org.UserOrg;
import com.klhd.psi.vo.org.UserOrgExt;
import com.klhd.psi.vo.org.UserOrgQuery;
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
@RequestMapping("/org")
@Api(description = "组织管理")
@ControllerPermission(code = "userOrg", desc = "组织管理")
public class UserOrgService {
    @Autowired
    private UserOrgDao userOrgDao;

    @ApiOperation("创建")
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功"),
    })
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Permission(code = "create", desc = "创建")
    public ResultVO create(
            @RequestBody @ApiParam(name = "组织对象", value = "传入:name", required = true) UserOrg record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        userOrgDao.insert(record);
        return resultVO;
    }

    @ApiOperation("删除")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 500, message = "有子组织，不可以删除"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Permission(code = "delete", desc = "删除")
    public ResultVO delete(
            @RequestBody @ApiParam(name = "组织对象", value = "传入:id", required = true) List<UserOrg> list
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        List<Integer> idList = list.stream().map(t -> t.getId()).collect(Collectors.toList());

        UserOrgQuery query1 = new UserOrgQuery();
        query1.createCriteria().andParentIdIn(idList);
        long count = userOrgDao.countByExample(query1);
        if(count > 0) {
            resultVO.setCode(500);
            resultVO.setMessage("有子组织，不可以删除");
            return resultVO;
        }

        UserOrgQuery query = new UserOrgQuery();
        query.createCriteria().andIdIn(idList);
        userOrgDao.deleteByExample(query);
        return resultVO;
    }

    @ApiOperation("修改组织")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(code = "update", desc = "修改组织")
    public ResultVO update(
            @RequestBody @ApiParam(name = "组织对象", value = "必传:id", required = true) UserOrg record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        userOrgDao.updateByPrimaryKeySelective(record);
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
            @RequestBody @ApiParam(name = "组织对象", value = "必传:id，选填：orgName、parentId", defaultValue = "{}", required = true) UserOrg record,
            @PathParam("") PageVO pageVO
    ) throws Exception {
        PageResultVO resultVO = new PageResultVO();
        UserOrgQuery query = new UserOrgQuery();
        query.setStartRow(pageVO.getStartRow());
        query.setPageSize(pageVO.getPageSize());
        //根据名称模糊查询
        if (Strings.isNotEmpty(record.getOrgName())) {
            query.createCriteria().andOrgNameLike("%" + record.getOrgName() + "%");
        }
        List<UserOrg> menus = userOrgDao.selectByExample(query);
        long count = userOrgDao.countByExample(query);
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
            @RequestBody @ApiParam(name = "组织对象", value = "必传:id", required = true) UserOrg record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        UserOrg userOrg = userOrgDao.selectByPrimaryKey(record.getId());

        if(userOrg == null){
            return resultVO;
        }
        UserOrgQuery query = new UserOrgQuery();
        query.createCriteria().andParentIdEqualTo(userOrg.getId());
        List<UserOrg> userOrgs = userOrgDao.selectByExample(query);
        UserOrgExt userOrgExt = (UserOrgExt) userOrg;
        if(userOrgs.size() > 0) {
            userOrgExt.setChildren(userOrgs);
        }else{
            userOrgExt.setChildren(Lists.newArrayList());
        }
        resultVO.setResult(userOrgExt);
        return resultVO;
    }
}
