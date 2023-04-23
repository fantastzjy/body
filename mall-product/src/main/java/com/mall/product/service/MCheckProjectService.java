package com.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.utils.PageUtils;
import com.mall.product.entity.MCheckProjectEntity;

import java.util.Map;

public interface MCheckProjectService extends IService<MCheckProjectEntity> {

	PageUtils queryPage(Map<String, Object> params);
}

