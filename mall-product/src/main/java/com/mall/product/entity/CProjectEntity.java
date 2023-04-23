package com.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author 
 * @email 
 * @date 2023-03-26 22:54:47
 */
@Data
@TableName("c_project")
public class CProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long projectId;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String room;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private String notion;
	/**
	 * 
	 */
	private Integer isNotion;
	/**
	 * 
	 */
	private String unit;
	/**
	 * 
	 */
	private BigDecimal scopeMin;
	/**
	 * 
	 */
	private BigDecimal scopeMax;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private String extra;
	/**
	 * 
	 */
	private Integer isValid;
	/**
	 * 
	 */
	private Integer isDel;
	/**
	 * 
	 */
	private String createBy;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String updateBy;
	/**
	 * 
	 */
	private Date updateTime;


	//标记数据库中不包含该字段
	@TableField(exist = false)
	private Long[] catelogPath;
}
