package com.yingxue.lesson.service.impl;



import com.yingxue.lesson.entity.SysPermission;

import com.yingxue.lesson.exception.BusinessException;
import com.yingxue.lesson.exception.code.BaseResponseCode;
import com.yingxue.lesson.mapper.SysPermissionMapper;
import com.yingxue.lesson.service.PermissionService;
import com.yingxue.lesson.vo.req.PermissionAddReqVO;
import com.yingxue.lesson.vo.resp.PermissionRespNodeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: PermissionServiceImpl
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public List<SysPermission> selectAll() {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectAll();
        if(!sysPermissions.isEmpty()){
            for (SysPermission sysPermission :
                    sysPermissions) {
                SysPermission parent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getPid());
                if(parent!=null){
                    sysPermission.setPidName(parent.getName());
                }
            }
        }
        return sysPermissions;
    }

    @Override
    public List<PermissionRespNodeVO> selectAllMenuByTree() {
        List<SysPermission> list=sysPermissionMapper.selectAll();
        List<PermissionRespNodeVO> result=new ArrayList<>();
        PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
        respNodeVO.setId("0");
        respNodeVO.setTitle("默认顶级菜单");
        respNodeVO.setChildren(getTree(list,true));
        result.add(respNodeVO);
        return result;
    }
    /**
     * 递归遍历所有数据
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param id
     * @param all
     * @return       java.util.List<com.yingxue.lesson.vo.resp.PermissionRespNodeVO>
     * @throws
     */
    private List<PermissionRespNodeVO> getChild(String id,List<SysPermission> all){

        List<PermissionRespNodeVO> list=new ArrayList<>();
        for (SysPermission s:
                all) {
            if(s.getPid().equals(id)){
                PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(s,respNodeVO);
                respNodeVO.setTitle(s.getName());
                respNodeVO.setChildren(getChild(s.getId(),all));
                list.add(respNodeVO);
            }
        }
        return list;
    }
    /**
     * type=true 递归遍历到菜单
     * type=false 递归遍历到按钮
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param all
     * @param type
     * @return       java.util.List<com.yingxue.lesson.vo.resp.PermissionRespNodeVO>
     * @throws
     */
    private List<PermissionRespNodeVO> getTree(List<SysPermission> all,boolean type){

        List<PermissionRespNodeVO> list=new ArrayList<>();
        if(all==null||all.isEmpty()){
            return list;
        }
        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals("0")){
                PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(sysPermission,respNodeVO);
                respNodeVO.setTitle(sysPermission.getName());
                if(type){
                    respNodeVO.setChildren(getChildExBtn(sysPermission.getId(),all));
                }else{
                    respNodeVO.setChildren(getChild(sysPermission.getId(),all));
                }

                list.add(respNodeVO);
            }
        }
        return list;
    }
    /**
     * 只递归到菜单
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param id
     * @param all
     * @return       java.util.List<com.yingxue.lesson.vo.resp.PermissionRespNodeVO>
     * @throws
     */
    private List<PermissionRespNodeVO> getChildExBtn(String id,List<SysPermission> all){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        for (SysPermission s:
                all) {
            if(s.getPid().equals(id)&&s.getType()!=3){
                PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(s,respNodeVO);
                respNodeVO.setTitle(s.getName());
                respNodeVO.setChildren(getChildExBtn(s.getId(),all));
                list.add(respNodeVO);
            }
        }
        return list;
    }

    @Override
    public SysPermission addPermission(PermissionAddReqVO vo) {
        SysPermission sysPermission=new SysPermission();
        BeanUtils.copyProperties(vo,sysPermission);
        verifyForm(sysPermission);
        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setCreateTime(new Date());
        int insert = sysPermissionMapper.insertSelective(sysPermission);
        if(insert!=1){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return sysPermission;
    }
    /**
     * - 操作后的菜单类型是目录的时候 父级必须为目录
     * - 操作后的菜单类型是菜单的时候，父类必须为目录类型
     * - 操作后的菜单类型是按钮的时候 父类必须为菜单类型
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param sysPermission
     * @return       void
     * @throws
     */
    private void verifyForm(SysPermission sysPermission){

        SysPermission parent=sysPermissionMapper.selectByPrimaryKey(sysPermission.getPid());
        switch (sysPermission.getType()){
            case 1:
                if(parent!=null){
                    if(parent.getType()!=1){
                        throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                }else if (!sysPermission.getPid().equals("0")){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if(parent==null||parent.getType()!=1){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
                }
                if(StringUtils.isEmpty(sysPermission.getUrl())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
            case 3:
                if(parent==null||parent.getType()!=2){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if(StringUtils.isEmpty(sysPermission.getPerms())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if(StringUtils.isEmpty(sysPermission.getUrl())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                if(StringUtils.isEmpty(sysPermission.getMethod())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_METHOD_NULL);
                }
                if(StringUtils.isEmpty(sysPermission.getCode())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL);
                }
                break;
        }
    }
    @Override
    public List<PermissionRespNodeVO> permissionTreeList(String userId) {
        return getTree( selectAll(),true);
    }

    @Override
    public List<PermissionRespNodeVO> selectAllTree() {
        return getTree(selectAll(),false);
    }


}
