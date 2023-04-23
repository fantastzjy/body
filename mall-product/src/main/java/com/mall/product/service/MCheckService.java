package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.MCheckEntity;

import java.util.Map;

/**
 * @author
 * @email
 * @date 2023-04-05 14:25:25
 */
public interface MCheckService extends IService<MCheckEntity> {

	PageUtils queryPage(Map<String, Object> params);

	PageUtils analysis(Map<String, Object> params);
}

