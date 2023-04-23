package com.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author 
 * @email 
 * @date 2023-03-27 20:34:22
 */
@Data
@TableName("c_package_project_relation")
public class CPackageProjectRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long packageId;
	/**
	 * 
	 */
	private Long projectId;
	/**
	 * 
	 */
	private String packageName;
	/**
	 * 
	 */
	private String projectName;
	/**
	 * 
	 */
	private Integer sort;
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

}
