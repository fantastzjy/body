package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.CategoryEntity;
import com.mall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类

 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();
    /**
     * 删除分类
     */
    void removeMenuByIds(List<Long> asList);

    /**
     * 根据catelogId查询所有父分类ID
     * 找到catelogId的完整路径
     * [父/子/孙]
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    /**
     * 级联更新所有关联表的冗余数据
     */
      void updateCascade(CategoryEntity category);

    /**
     * 查出所有1级分类
     */
    List<CategoryEntity> getLevel1Categorys();
    /**
     * 查询三级分类并封装成Map返回
     * 使用SpringCache注解方式简化缓存设置
     */

    Map<String, List<Catelog2Vo>> getCatelogJsonWithSpringCache();
    /**
     * 查询三级分类并封装成Map返回
     * 使用redis客户端实现缓存设置
     */
    Map<String, List<Catelog2Vo>> getCatalogJson();
}

