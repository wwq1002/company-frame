package com.yingxue.lesson.service;


import com.github.pagehelper.PageInfo;
import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.vo.req.*;


import com.yingxue.lesson.vo.resp.LoginRespVO;
import com.yingxue.lesson.vo.resp.PageVO;
import com.yingxue.lesson.vo.resp.UserOwnRoleRespVO;

import java.util.List;


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
    String refreshToken(String refreshToken);
    void updateUserInfo(UserUpdateReqVO vo, String operationId);
    void deletedUsers(List<String> list, String operationId);
    List<SysUser> selectUserInfoByDeptIds(List<String> deptIds);
    void logout(String accessToken,String refreshToken);
    SysUser detailInfo(String userId);
    //个人用户编辑信息接口
    void userUpdateDetailInfo(UserUpdateDetailInfoReqVO vo,String userId);

    void userUpdatePwd(UserUpdatePwdReqVO vo,String accessToken,String refreshToken);
}
