package com.yingxue.lesson.controller;

import com.yingxue.lesson.entity.SysUser;
import com.yingxue.lesson.exception.BusinessException;
import com.yingxue.lesson.exception.code.BaseResponseCode;
import com.yingxue.lesson.utils.DataResult;
import com.yingxue.lesson.vo.req.TestReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/swagger")
@Api(tags = "测试接口模块",description = "主要是为了提供测试接口用")
public class TestController {

    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public  String testSwagger(){
        return "测试成功";
    }

    @GetMapping("/test/data")
    @ApiOperation(value="统一的响应格式测试接口")
    public DataResult<System> test(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("张三");
        sysUser.setPhone("15691509889");
        SysUser sysUser1 = new SysUser();
        sysUser1.setUsername("李四");
        sysUser1.setPhone("15691509889");
        List<SysUser> list=new ArrayList<>();
        list.add(sysUser);
        list.add(sysUser1);
        DataResult<System> result=DataResult.success(list);
        return result;

    }
    @GetMapping("/business/error")
    @ApiOperation(value = "测试主动抛出业务异常接口")
    public DataResult<String> testBusinessError(@RequestParam String type){
        if (!type.equals("1")){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        DataResult<String> result=new DataResult(0,type);
        return result;
    }

    @PostMapping("/test/valid")
    @ApiOperation(value = "测试校验验证器")
    public DataResult testValid(@RequestBody @Valid TestReqVO vo){
        DataResult result=DataResult.success(vo);
        return result;
    }


}
