package com.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import com.mall.product.dao.MCheckDao;
import com.mall.product.entity.CPackageEntity;
import com.mall.product.entity.MCheckEntity;
import com.mall.product.service.CPackageService;
import com.mall.product.service.MCheckService;
import com.mall.product.vo.MCheckVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("mCheckService")
public class MCheckServiceImpl extends ServiceImpl<MCheckDao, MCheckEntity> implements MCheckService {

	@Autowired
	private CPackageService cPackageService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<MCheckEntity> page = this.page(
				new Query<MCheckEntity>().getPage(params),
				new QueryWrapper<MCheckEntity>()
		);

		List<MCheckEntity> records = page.getRecords();

		List<MCheckVo> voList = records.stream().map(c -> {
			MCheckVo mCheckVo = new MCheckVo();
			BeanUtils.copyProperties(c, mCheckVo);
			mCheckVo.setPackageName(this.getPackageName(mCheckVo.getPackageId()));
			// mCheckVo.setExceptionIds();
			return mCheckVo;
		}).collect(Collectors.toList());

		IPage<MCheckVo> pageVo = new Page<>();

		BeanUtils.copyProperties(page, pageVo);

		pageVo.setRecords(voList);

		return new PageUtils(pageVo);
	}

	@Override
	public PageUtils analysis(Map<String, Object> params) {
		return null;
	}

	private String getPackageName(Long id) {
		CPackageEntity byId = cPackageService.getById(id);
		if (byId != null) {
			return byId.getName();
		}
		return "";
	}

}