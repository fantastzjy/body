package com.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.product.entity.CPackageProjectRelationEntity;
import com.mall.product.service.AttrGroupService;
import com.mall.product.service.CPackageProjectRelationService;
import com.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mall.product.entity.CProjectEntity;
import com.mall.product.service.CProjectService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;

@RestController
@RequestMapping("product/project")
public class CProjectController {

    @Autowired
    private CProjectService cProjectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CPackageProjectRelationService cPackageProjectRelationService;

    /**
     * 获取所有分类列表
     */
    @GetMapping(value = "/all/list")
    public R catelogList(@RequestParam("packageId") Long packageId) {

        List<CProjectEntity> data = cProjectService.list();

        List<CPackageProjectRelationEntity> relationList = cPackageProjectRelationService.
                list(new QueryWrapper<CPackageProjectRelationEntity>().eq("package_id", packageId));

        data = data.stream().filter(c -> !relationList.stream().map(m -> m.getProjectId()).collect(Collectors.toList()).contains(c.getProjectId())).collect(Collectors.toList());

        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("product:cproject:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cProjectService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{projectId}")
    //@RequiresPermissions("product:cproject:info")
    public R info(@PathVariable("projectId") Long projectId) {
        CProjectEntity cProject = cProjectService.getById(projectId);

        Integer catelogId = cProject.getType();
        Long[] path = categoryService.findCatelogPath(catelogId.longValue());// todo 替换数据库类型为bignt

        cProject.setCatelogPath(path);

        return R.ok().put("cProject", cProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:cproject:save")
    public R save(@RequestBody CProjectEntity cProject) {
        cProjectService.save(cProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:cproject:update")
    public R update(@RequestBody CProjectEntity cProject) {
        cProjectService.updateById(cProject);

        return R.ok();
    }

    /**
     * 是否有效
     */
    @RequestMapping("/updateIsValid")
    //@RequiresPermissions("product:cproject:delete")
    public R updateIsValid(@RequestParam Long id, Integer isValid) {

        cProjectService.updateIsValid(id, isValid);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:cproject:delete")
    public R delete(@RequestBody Long[] projectIds) {
        cProjectService.removeByIds(Arrays.asList(projectIds));

        return R.ok();
    }
}
