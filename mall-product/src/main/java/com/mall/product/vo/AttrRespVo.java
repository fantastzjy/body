package com.mall.product.vo;

import lombok.Data;

//responseVo
@Data
public class AttrRespVo extends AttrVo {

    /**
     * catelogName: 手机/数码/手机 【属性所属三级分类全名】
     * groupName:   主题  【属性所属分组的名字】
     */

    private String catelogName;

    private String groupName;

    private Long[] catelogPath;

}
