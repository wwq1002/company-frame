package com.yingxue.lesson.service;

import com.yingxue.lesson.entity.SysRole;

import com.yingxue.lesson.vo.req.AddRoleReqVO;
import com.yingxue.lesson.vo.req.RolePageReqVO;

import com.yingxue.lesson.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: RoleService
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public interface RoleService {
    PageVO<SysRole> pageInfo(RolePageReqVO vo);
    SysRole addRole(AddRoleReqVO vo);
    List<SysRole> selectAll();

}
