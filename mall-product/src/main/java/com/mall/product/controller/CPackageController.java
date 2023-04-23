package com.mall.product.controller;

import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;
import com.mall.product.entity.CPackageEntity;
import com.mall.product.service.CPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("product/package")
public class CPackageController {

	@Autowired
	private CPackageService cPackageService;

	/**
	 * 列表
	 */
	@RequestMapping("/listTree")
	//@RequiresPermissions("product:cpackage:list")
	public R listTree(@RequestParam Map<String, Object> params) {
		PageUtils page = cPackageService.listTree(params);
		return R.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("product:cpackage:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = cPackageService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{packageId}")
	//@RequiresPermissions("product:cpackage:info")
	public R info(@PathVariable("packageId") Long packageId) {
		CPackageEntity cPackage = cPackageService.getById(packageId);
		return R.ok().put("cPackage", cPackage);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("product:cpackage:save")
	public R save(@RequestBody CPackageEntity cPackage) {
		cPackageService.save(cPackage);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("product:cpackage:update")
	public R update(@RequestBody CPackageEntity cPackage) {
		cPackageService.updateById(cPackage);
		return R.ok();
	}

	/**
	 * 是否有效
	 */
	@RequestMapping("/updateIsValid")
	//@RequiresPermissions("product:cproject:delete")
	public R updateIsValid(@RequestParam Long id, Integer isValid) {
		cPackageService.updateIsValid(id, isValid);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("product:cpackage:delete")
	public R delete(@RequestBody Long[] packageIds) {
		cPackageService.removeByIds(Arrays.asList(packageIds));
		return R.ok();
	}
}
