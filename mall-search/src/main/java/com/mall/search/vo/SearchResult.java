package com.mall.search.vo;

import com.mall.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class SearchResult {

    /**
     * es检索到的所有商品信息
     */
    private List<SkuEsModel> product;


    /**
     * 分页信息
     */
    private Integer pageNum;// 当前页码
    private Long total;// 总记录数
    private Integer totalPages;// 总页码
    private List<Integer> pageNavs;// 导航页码[1、2、3、4、5]

    /**
     * 当前查询到的结果，所有涉及到的品牌
     */
    private List<BrandVo> brands;

    /**
     * 当前查询到的结果，所有涉及到的所有属性
     */
    private List<AttrVo> attrs;

    /**
     * 当前查询到的结果，所有涉及到的所有分类
     */
    private List<CatalogVo> catalogs;


    //===========================以上是返回给页面的所有信息============================//


    // 面包屑导航数据
    private List<NavVo> navs = new ArrayList<>();

    // 封装筛选条件中的属性id集合【用于面包屑，选择属性后出现在面包屑中，下面的属性栏则隐藏】
    // 该字段是提供前端用的
    private List<Long> attrIds = new ArrayList<>();


    /**
     * 面包屑导航VO
     */
    @Data
    public static class NavVo {
        private String navName;// 属性名
        private String navValue;// 属性值
        private String link;// 回退地址（删除该面包屑筛选条件回退地址）
    }

    @Data
    public static class BrandVo {
        private Long brandId;//
        private String brandName;//
        private String brandImg;//
    }

    @Data
    public static class CatalogVo {
        private Long catalogId;//
        private String catalogName;//
    }

    @Data
    public static class AttrVo {
        private Long attrId;// 允许检索的 属性Id
        private String attrName;// 允许检索的 属性名
        private List<String> attrValue;// 属性值【多个】
    }
}
