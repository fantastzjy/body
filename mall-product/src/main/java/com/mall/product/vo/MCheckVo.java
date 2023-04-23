package com.mall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class MCheckVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long checkId;
	/**
	 * 用户 id
	 */
	private Long memberId;
	/**
	 * 用户昵称
	 */
	private String memberName;
	/**
	 * 预约日期
	 */
	private Date date;
	/**
	 * 体检套餐
	 */
	private Long packageId;

	/**
	 * 体检套餐名称
	 */
	private String packageName;

	/**
	 * 加项项目列表
	 */
	private String projectIds;
	/**
	 * 已体检项目 id 列表
	 */
	private String checkedIds;
	/**
	 * 体检状态
	 */
	private Integer status;
	/**
	 * 异常项目 id列表
	 */
	private String exceptionIds;
	/**
	 * 体检报告
	 */
	private String report;
	/**
	 * 体检报告出具医生
	 */
	private String reportBy;
	/**
	 * 是否有效
	 */
	private Integer isValid;
	/**
	 * 是否删除
	 */
	private Integer isDel;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
