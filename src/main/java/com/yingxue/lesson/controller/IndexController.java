package com.yingxue.lesson.controller;

import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.utils.DataResult;
import com.yingxue.lesson.vo.req.UserPageReqVO;
import com.yingxue.lesson.vo.resp.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: IndexController
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Controller
@RequestMapping("/index")
@Api(tags = "试图",description = "跳转试图的控制器")
public class IndexController {

    @GetMapping("/404")
    @ApiOperation(value = "跳转404错误页面")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/login")
    @ApiOperation(value = "跳转登录界面")
    public String logout() {
        return "login";
    }

    @GetMapping("/home")
    @ApiOperation(value = "跳转首页页面")
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    @ApiOperation(value = "跳转主页页面")
    public String main() {
        return "main";
    }

    @GetMapping("/menus")
    @ApiOperation(value = "菜单权限管理界面")
    public String menu() {
        return "menus/menu";
    }

    @GetMapping("/roles")
    @ApiOperation(value = "跳转角色管理界面")
    public String roles() {
        return "roles/role";
    }

    @GetMapping("/depts")
    @ApiOperation(value = "跳转部门管理界面")
    public String depts() {
        return "depts/dept";
    }

    @GetMapping("/users")
    @ApiOperation(value = "跳转部门管理界面")
    public String users() {
        return "users/user";
    }


    @GetMapping("/logs")
    @ApiOperation(value = "跳转部门管理界面")
    public String logs() {
        return "logs/log";
    }

    @GetMapping("/users/info")
    @ApiOperation(value = "跳转部门管理界面")
    public String userinfo() {
        return "users/user_edit";
    }

    @GetMapping("/users/pwd")
    @ApiOperation(value = "跳转部门管理界面")
    public String userpwd() {
        return "users/user_pwd";
    }
}
