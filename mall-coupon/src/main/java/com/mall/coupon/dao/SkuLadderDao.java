package com.mall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.coupon.entity.SkuLadderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品阶梯价格
 *
 */
@Mapper
public interface SkuLadderDao extends BaseMapper<SkuLadderEntity> {
	
}
