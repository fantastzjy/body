package com.mall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.coupon.entity.CouponHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 *
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
