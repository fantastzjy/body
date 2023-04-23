package com.mall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;
import com.mall.product.entity.CPackageProjectRelationEntity;
import com.mall.product.entity.MCheckEntity;
import com.mall.product.entity.MCheckProjectEntity;
import com.mall.product.service.CPackageProjectRelationService;
import com.mall.product.service.MCheckProjectService;
import com.mall.product.service.MCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("product/check")
public class MCheckController {

	@Autowired
	private MCheckService mCheckService;

	@Autowired
	private MCheckProjectService mCheckProjectService;

	@Autowired
	private CPackageProjectRelationService cPackageProjectRelationService;

	/**
	 * 列表
	 */
	@RequestMapping("/analysis")
	//@RequiresPermissions("product:mcheck:list")
	public R analysis(@RequestParam Map<String, Object> params) {
		PageUtils page = mCheckService.analysis(params);

		return R.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("product:mcheck:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = mCheckService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{checkId}")
	//@RequiresPermissions("product:mcheck:info")
	public R info(@PathVariable("checkId") Long checkId) {
		MCheckEntity mCheck = mCheckService.getById(checkId);

		return R.ok().put("mCheck", mCheck);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("product:mcheck:save")
	public R save(@RequestBody MCheckEntity mCheck) {
		mCheck.setCreateTime(new Date());
		mCheck.setUpdateTime(new Date());
		boolean save = mCheckService.save(mCheck);

		QueryWrapper<CPackageProjectRelationEntity> checkQueryWrapper = new QueryWrapper<>();
		checkQueryWrapper.and((wrapper) -> {
			wrapper.eq("package_id", mCheck.getPackageId());
		});

		ArrayList<MCheckProjectEntity> projectEntities = new ArrayList<>();
		List<CPackageProjectRelationEntity> entityList = cPackageProjectRelationService.list(checkQueryWrapper);
		entityList.forEach(c -> {
			MCheckProjectEntity mCheckProject = new MCheckProjectEntity();
			mCheckProject.setCheckId(mCheck.getCheckId());
			mCheckProject.setProjectId(c.getProjectId());
			mCheckProject.setIsValid(1);
			mCheckProject.setCreateTime(new Date());
			mCheckProject.setUpdateTime(new Date());
			projectEntities.add(mCheckProject);
		});

		mCheckProjectService.saveBatch(projectEntities);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("product:mcheck:update")
	public R update(@RequestBody MCheckEntity mCheck) {
		mCheckService.updateById(mCheck);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("product:mcheck:delete")
	public R delete(@RequestBody Long[] checkIds) {
		mCheckService.removeByIds(Arrays.asList(checkIds));

		return R.ok();
	}

}
