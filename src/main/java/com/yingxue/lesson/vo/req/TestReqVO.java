package com.yingxue.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: TestReqVO
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Data
public class TestReqVO {
    @NotEmpty(message = "list 数据不能为空")
    @ApiModelProperty(value = "list 集合数据")
    private List<String> list;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotNull(message = "年龄不能为空")
    @ApiModelProperty(value = "年龄")
    private Integer age;
}
