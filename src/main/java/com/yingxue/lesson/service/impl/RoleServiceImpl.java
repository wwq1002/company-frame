package com.yingxue.lesson.service.impl;

import com.github.pagehelper.PageHelper;
import com.yingxue.lesson.contants.Constant;
import com.yingxue.lesson.entity.SysRole;
import com.yingxue.lesson.exception.BusinessException;
import com.yingxue.lesson.exception.code.BaseResponseCode;
import com.yingxue.lesson.mapper.SysRoleMapper;
import com.yingxue.lesson.service.*;
import com.yingxue.lesson.utils.PageUtil;
import com.yingxue.lesson.vo.req.AddRoleReqVO;
import com.yingxue.lesson.vo.req.RolePageReqVO;

import com.yingxue.lesson.vo.req.RolePermissionOperationReqVO;
import com.yingxue.lesson.vo.resp.PageVO;
import com.yingxue.lesson.vo.resp.PermissionRespNodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RoleServiceImpl
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public PageVO<SysRole> pageInfo(RolePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysRole> sysRoles =sysRoleMapper.selectAll(vo);
        return PageUtil.getPageVO(sysRoles);
    }

    @Override
    @Transactional(rollbackFor =Exception.class)//两个表的操作，要保证事务的运行，避免回滚。
    public SysRole addRole(AddRoleReqVO vo) {
        SysRole sysRole=new SysRole();
        BeanUtils.copyProperties(vo,sysRole);
        sysRole.setId(UUID.randomUUID().toString());
        sysRole.setCreateTime(new Date());
        int i = sysRoleMapper.insertSelective(sysRole);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if(vo.getPermissions()!=null&&!vo.getPermissions().isEmpty()){
            RolePermissionOperationReqVO operationReqVO=new RolePermissionOperationReqVO();
            operationReqVO.setRoleId(sysRole.getId());
            operationReqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(operationReqVO);
        }
        return sysRole;
    }


}
