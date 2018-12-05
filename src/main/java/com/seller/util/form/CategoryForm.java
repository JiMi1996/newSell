package com.seller.util.form;/**
 * created by sheting on 2018/12/3
 */

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: newsell
 * @description:
 * @author: ljm
 * @create: 2018-12-03 15:40
 **/
@Data
public class CategoryForm {

    @NotNull(message = "类目名称必填")
    private String categoryName;

    @NotNull(message = "类目类型必填")
    private Integer categoryType;
}
