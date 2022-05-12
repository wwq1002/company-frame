package com.yingxue.lesson.service.impl;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingxue.lesson.contants.Constant;
import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.exception.BusinessException;
import com.yingxue.lesson.exception.code.BaseResponseCode;
import com.yingxue.lesson.mapper.SysUserMapper;
import com.yingxue.lesson.service.UserService;
import com.yingxue.lesson.utils.JwtTokenUtil;
import com.yingxue.lesson.utils.PageUtil;
import com.yingxue.lesson.utils.PasswordUtils;
import com.yingxue.lesson.vo.req.LoginReqVO;
import com.yingxue.lesson.vo.req.UserPageReqVO;
import com.yingxue.lesson.vo.resp.LoginRespVO;
import com.yingxue.lesson.vo.resp.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceImpl
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        SysUser sysUser = sysUserMapper.selectByUsername(vo.getUsername());
        if(sysUser==null){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        if(sysUser.getStatus()==2){
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK_TIP);
        }
        if(!PasswordUtils.matches(sysUser.getSalt(),vo.getPassword(),sysUser.getPassword())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        Map<String,Object> claims=new HashMap<>();
        claims.put(Constant.JWT_USER_NAME,sysUser.getUsername());
        claims.put(Constant.ROLES_INFOS_KEY,getRoleByUserId(sysUser.getId()));
        claims.put(Constant.PERMISSIONS_INFOS_KEY,getPermissionByUserId(sysUser.getId()));
        String accessToken= JwtTokenUtil.getAccessToken(sysUser.getId(),claims);
        log.info("accessToken={}",accessToken);
        Map<String,Object> refreshTokenClaims=new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME,sysUser.getUsername());
        String refreshToken=null;
        if(vo.getType().equals("1")){
            refreshToken=JwtTokenUtil.getRefreshToken(sysUser.getId(),refreshTokenClaims);
        }else {
            refreshToken=JwtTokenUtil.getRefreshAppToken(sysUser.getId(),refreshTokenClaims);
        }
        log.info("refreshToken={}",refreshToken);
        LoginRespVO loginRespVO=new LoginRespVO();
        loginRespVO.setUserId(sysUser.getId());
        loginRespVO.setRefreshToken(refreshToken);
        loginRespVO.setAccessToken(accessToken);
        return loginRespVO;
    }

    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        return PageUtil.getPageVo(sysUsers);
    }

    /**
     * 用过用户id查询拥有的角色信息
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    private List<String> getRoleByUserId(String userId){
      List<String> list=new ArrayList<>();
      if(userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")){
          list.add("admin");
      }else {
          list.add("dev");
      }
      return list;
    }

    private List<String> getPermissionByUserId(String userId){
        List<String> list=new ArrayList<>();
        if(userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")){
            list.add("sys:user:add");
            list.add("sys:user:update");
            list.add("sys:user:delete");
            list.add("sys:user:list");
        }else {
            list.add("sys:user:add");
        }
        return list;
    }


}
