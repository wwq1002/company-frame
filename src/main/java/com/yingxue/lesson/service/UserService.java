package com.yingxue.lesson.service;


import com.github.pagehelper.PageInfo;
import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.vo.req.LoginReqVO;


import com.yingxue.lesson.vo.req.UserAddReqVO;
import com.yingxue.lesson.vo.req.UserOwnRoleReqVO;
import com.yingxue.lesson.vo.req.UserPageReqVO;
import com.yingxue.lesson.vo.resp.LoginRespVO;
import com.yingxue.lesson.vo.resp.PageVO;
import com.yingxue.lesson.vo.resp.UserOwnRoleRespVO;


/**
 * @ClassName: UserService
 * TODO:类文件简单描述
 * @Author: 小霍
 */
public interface UserService {

    LoginRespVO login(LoginReqVO vo);
    PageVO<SysUser> pageInfo(UserPageReqVO vo);
    void addUser(UserAddReqVO vo);
    UserOwnRoleRespVO getUserOwnRole(String userId);
    void setUserOwnRole(UserOwnRoleReqVO vo);

}
