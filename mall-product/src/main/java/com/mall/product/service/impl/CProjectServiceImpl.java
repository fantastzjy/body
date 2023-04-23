package com.mall.product.service.impl;

import com.mall.product.entity.SkuInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.CProjectDao;
import com.mall.product.entity.CProjectEntity;
import com.mall.product.service.CProjectService;


@Service("cProjectService")
public class CProjectServiceImpl extends ServiceImpl<CProjectDao, CProjectEntity> implements CProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CProjectEntity> page = this.page(
                new Query<CProjectEntity>().getPage(params),
                new QueryWrapper<CProjectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateIsValid(Long id, Integer isValid) {
        CProjectEntity entity = new CProjectEntity();
        entity.setProjectId(id);
        entity.setIsValid(isValid);
        this.updateById(entity);
    }
}