package com.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import com.mall.product.dao.MCheckProjectDao;
import com.mall.product.entity.CProjectEntity;
import com.mall.product.entity.MCheckEntity;
import com.mall.product.entity.MCheckProjectEntity;
import com.mall.product.service.CProjectService;
import com.mall.product.service.MCheckProjectService;
import com.mall.product.service.MCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("mCheckProjectService")
public class MCheckProjectServiceImpl extends ServiceImpl<MCheckProjectDao, MCheckProjectEntity> implements MCheckProjectService {

	@Autowired
	MCheckService mCheckService;

	@Autowired
	CProjectService cProjectService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<MCheckProjectEntity> page = this.page(new Query<MCheckProjectEntity>().getPage(params), new QueryWrapper<MCheckProjectEntity>());

		List<MCheckProjectEntity> list = page.getRecords().stream()
				.filter(c -> {
					QueryWrapper<MCheckEntity> checkQueryWrapper = new QueryWrapper<>();
					checkQueryWrapper.and((wrapper) -> {
						wrapper.eq("check_id", c.getCheckId());
					});

					MCheckEntity checkEntity = mCheckService.getOne(checkQueryWrapper);
					return checkEntity != null;
				}).map(c -> {

					QueryWrapper<MCheckEntity> checkQueryWrapper = new QueryWrapper<>();
					checkQueryWrapper.and((wrapper) -> {
						wrapper.eq("check_id", c.getCheckId());
					});

					MCheckEntity checkEntity = mCheckService.getOne(checkQueryWrapper);

					c.setMemberId(checkEntity.getMemberId());
					c.setMemberName(checkEntity.getMemberName());

					QueryWrapper<CProjectEntity> projectQueryWrapper = new QueryWrapper<>();
					projectQueryWrapper.and((wrapper) -> {
						wrapper.eq("project_id", c.getProjectId());
					});
					CProjectEntity projectEntity = cProjectService.getOne(projectQueryWrapper);
					c.setProjectName(projectEntity.getName());

					return c;
				}).collect(Collectors.toList());

		page.setRecords(list);

		return new PageUtils(page);
	}
}