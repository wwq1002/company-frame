package com.yingxue.lesson.service.impl;

import com.alibaba.fastjson.JSON;
import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.mapper.SysUserMapper;
import com.yingxue.lesson.service.HomeService;
import com.yingxue.lesson.service.PermissionService;
import com.yingxue.lesson.vo.resp.HomeRespVO;
import com.yingxue.lesson.vo.resp.PermissionRespNodeVO;
import com.yingxue.lesson.vo.resp.UserInfoRespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: HomeServiceImpl
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PermissionService permissionService;


    @Override
    public HomeRespVO getHome(String userId) {
        HomeRespVO homeRespVO=new HomeRespVO();
        List<PermissionRespNodeVO> list=permissionService.permissionTreeList(userId);
        homeRespVO.setMenus(list);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        UserInfoRespVO vo=new UserInfoRespVO();
        if(sysUser!=null){
            BeanUtils.copyProperties(sysUser,vo);
            vo.setDeptName("陕西铁路工程职业技术学院软件开发脚手架");
        }
        homeRespVO.setUserInfoVO(vo);
        return homeRespVO;
    }
}
