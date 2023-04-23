package com.mall.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MCheckProjectVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 项目 id
	 */
	private Long projectId;
	/**
	 * 用户 id
	 */
	private Long memberId;
	/**
	 * 用户昵称
	 */
	private String memberName;

	// 项目名称
	private String projectName;
	/**
	 * 体检id
	 */
	private Long checkId;
	/**
	 * 检查值
	 */
	private BigDecimal value;
	/**
	 * 报告
	 */
	private String report;
	/**
	 * 检查人
	 */
	private String checkBy;
	/**
	 * 检查时间
	 */
	private String checkTime;
	/**
	 * 是否异常
	 */
	private Integer isException;
	/**
	 * 是否有效
	 */
	private Integer isValid;
	/**
	 * 是否删除
	 */
	private Integer isDel;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
