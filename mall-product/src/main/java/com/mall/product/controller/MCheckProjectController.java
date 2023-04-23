package com.mall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;
import com.mall.product.entity.CProjectEntity;
import com.mall.product.entity.MCheckProjectEntity;
import com.mall.product.service.CProjectService;
import com.mall.product.service.MCheckProjectService;
import com.mall.product.vo.MCheckProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product/checkproject")
public class MCheckProjectController {

    @Autowired
    private MCheckProjectService mCheckProjectService;

    @Autowired
    private CProjectService cProjectService;

    /**
     * 获取所有分类列表
     */
    @GetMapping(value = "/project/list")
    public R catelogList(@RequestParam("checkId") Long checkId) {

        List<MCheckProjectEntity> data = mCheckProjectService.list(new QueryWrapper<MCheckProjectEntity>().eq("check_id", checkId));

        List<CProjectEntity> relationList = cProjectService.list();

        List<MCheckProjectVo> voList = data.stream().map(c -> {
            MCheckProjectVo vo = new MCheckProjectVo();
            BeanUtils.copyProperties(c, vo);
            String name = relationList.stream().filter(k -> k.getProjectId().equals(c.getProjectId())).findFirst().get().getName();
            vo.setProjectName(name);
            return vo;
        }).collect(Collectors.toList());


        return R.ok().put("data", voList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:mcheckproject:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = mCheckProjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:mcheckproject:info")
    public R info(@PathVariable("id") Long id) {
        MCheckProjectEntity mCheckProject = mCheckProjectService.getById(id);

        return R.ok().put("mCheckProject", mCheckProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:mcheckproject:save")
    public R save(@RequestBody MCheckProjectEntity mCheckProject) {

        mCheckProjectService.save(mCheckProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:mcheckproject:update")
    public R update(@RequestBody MCheckProjectEntity mCheckProject) {
        mCheckProjectService.updateById(mCheckProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:mcheckproject:delete")
    public R delete(@RequestBody Long[] ids) {
        mCheckProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
