package com.yingxue.lesson.service;


import com.github.pagehelper.PageInfo;
import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.vo.req.LoginReqVO;


import com.yingxue.lesson.vo.resp.LoginRespVO;


/**
 * @ClassName: UserService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public interface UserService {

    LoginRespVO login(LoginReqVO vo);

}
