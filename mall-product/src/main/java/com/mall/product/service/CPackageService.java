package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.CPackageEntity;

import java.util.Map;

/**
 * @author
 * @email
 * @date 2023-03-26 18:29:00
 */
public interface CPackageService extends IService<CPackageEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void updateIsValid(Long id, Integer isValid);

	PageUtils listTree(Map<String, Object> params);
}

