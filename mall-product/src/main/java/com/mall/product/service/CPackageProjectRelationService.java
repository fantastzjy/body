package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.CPackageProjectRelationEntity;

import java.util.Map;

/**
 * 
 *
 * @author 
 * @email 
 * @date 2023-03-27 20:34:22
 */
public interface CPackageProjectRelationService extends IService<CPackageProjectRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

