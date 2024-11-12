package com.experiment.entity;

import java.io.Serializable;
import java.util.Date;

public class MomentPraise implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 动态id */
    private Long dynamicId;

    /** 点赞人 */
    private Long userId;

    /** 点赞时间 */
    private Date praiseTime;

    /** 状态(1=正常;0=取消) */
    private Long status;




}