package com.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import com.mall.product.dao.CPackageDao;
import com.mall.product.entity.CPackageEntity;
import com.mall.product.service.CPackageService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cPackageService")
public class CPackageServiceImpl extends ServiceImpl<CPackageDao, CPackageEntity> implements CPackageService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		QueryWrapper<CPackageEntity> wrapper = new QueryWrapper<>();
		IPage<CPackageEntity> page = this.page(
				new Query<CPackageEntity>().getPage(params),
				wrapper
		);

		return new PageUtils(page);
	}

	@Override
	public void updateIsValid(Long id, Integer isValid) {
		CPackageEntity entity = new CPackageEntity();
		entity.setPackageId(id);
		entity.setIsValid(isValid);
		this.updateById(entity);
	}

	@Override
	public PageUtils listTree(Map<String, Object> params) {
		QueryWrapper<CPackageEntity> wrapper = new QueryWrapper<>();
		wrapper.eq("is_valid", 1);
		IPage<CPackageEntity> page = this.page(
				new Query<CPackageEntity>().getPage(params),
				wrapper
		);

		return new PageUtils(page);
	}

}