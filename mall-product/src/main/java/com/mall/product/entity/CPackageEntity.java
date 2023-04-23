package com.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author 
 * @email 
 * @date 2023-03-26 18:29:00
 */
@Data
@TableName("c_package")
public class CPackageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 PK
	 */
	@TableId
	private Long packageId;
	/**
	 * 套餐类别
	 */
	private Integer type;
	/**
	 * 套餐名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 套餐标签
	 */
	private String tags;
	/**
	 * 注意事项
	 */
	private String notion;
	/**
	 * 拓展参数
	 */
	private String extra;
	/**
	 * 是否有效：0 无，1 有
	 */
	private Integer isValid;
	/**
	 * 是否删除：0 否，1 是
	 */
	private Integer isDel;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

}
