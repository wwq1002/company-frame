package com.yingxue.lesson.controller;


import com.yingxue.lesson.contants.Constant;
import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.service.UserService;
import com.yingxue.lesson.utils.DataResult;
import com.yingxue.lesson.utils.JwtTokenUtil;
import com.yingxue.lesson.vo.req.*;

import com.yingxue.lesson.vo.resp.LoginRespVO;

import com.yingxue.lesson.vo.resp.PageVO;
import com.yingxue.lesson.vo.resp.UserOwnRoleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: UserController
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户模块相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    @ApiOperation(value = "用户登录接口")
    @PostMapping("/user/login")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页查询用户接口")
    public DataResult<PageVO<SysUser>> pageInfo(@RequestBody UserPageReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.pageInfo(vo));
        return result;
    }
    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo){
        DataResult result=DataResult.success();
        userService.addUser(vo);
        return result;
    }
    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "查询用户拥有的角色数据接口")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId") String userId){
        DataResult result=DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    @PutMapping("/user/roles")
    @ApiOperation(value = "保存用户拥有的角色信息接口")
    public DataResult saveUserOwnRole(@RequestBody @Valid UserOwnRoleReqVO vo){
        DataResult result=DataResult.success();
        userService.setUserOwnRole(vo);
        return result;
    }

    @PutMapping("/user")
    @ApiOperation(value ="列表修改用户信息接口")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult result=DataResult.success();
        userService.updateUserInfo(vo,userId);
        return result;
    }
    @DeleteMapping("/user")
    @ApiOperation(value = "批量/删除用户接口")
    public DataResult deletedUsers(@RequestBody @ApiParam(value = "用户id集合") List<String> list, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String operationId=JwtTokenUtil.getUserId(accessToken);
        userService.deletedUsers(list,operationId);
        DataResult result=DataResult.success();
        return result;
    }
}
