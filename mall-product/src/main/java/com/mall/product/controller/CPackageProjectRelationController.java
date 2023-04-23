package com.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.product.entity.CategoryBrandRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mall.product.entity.CPackageProjectRelationEntity;
import com.mall.product.service.CPackageProjectRelationService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;

@RestController
@RequestMapping("product/packageprojectrelation")
public class CPackageProjectRelationController {

    @Autowired
    private CPackageProjectRelationService cPackageProjectRelationService;

    /**
     * 获取当前套餐关联的所有分类列表列表
     */
    @GetMapping(value = "/catelog/list")
    public R catelogList(@RequestParam Map<String, Object> params,@RequestParam("packageId") Long packageId){

        List<CPackageProjectRelationEntity> data = cPackageProjectRelationService.
                list(new QueryWrapper<CPackageProjectRelationEntity>().eq("package_id",packageId));

        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:cpackageprojectrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cPackageProjectRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:cpackageprojectrelation:info")
    public R info(@PathVariable("id") Long id){
		CPackageProjectRelationEntity cPackageProjectRelation = cPackageProjectRelationService.getById(id);

        return R.ok().put("cPackageProjectRelation", cPackageProjectRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:cpackageprojectrelation:save")
    public R save(@RequestBody CPackageProjectRelationEntity cPackageProjectRelation){
        boolean save = cPackageProjectRelationService.save(cPackageProjectRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:cpackageprojectrelation:update")
    public R update(@RequestBody CPackageProjectRelationEntity cPackageProjectRelation){
		cPackageProjectRelationService.updateById(cPackageProjectRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:cpackageprojectrelation:delete")
    public R delete(@RequestBody Long[] ids){
		cPackageProjectRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
