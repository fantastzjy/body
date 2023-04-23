package com.mall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.CPackageProjectRelationDao;
import com.mall.product.entity.CPackageProjectRelationEntity;
import com.mall.product.service.CPackageProjectRelationService;


@Service("cPackageProjectRelationService")
public class CPackageProjectRelationServiceImpl extends ServiceImpl<CPackageProjectRelationDao, CPackageProjectRelationEntity> implements CPackageProjectRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CPackageProjectRelationEntity> page = this.page(
                new Query<CPackageProjectRelationEntity>().getPage(params),
                new QueryWrapper<CPackageProjectRelationEntity>()
        );

        return new PageUtils(page);
    }

}