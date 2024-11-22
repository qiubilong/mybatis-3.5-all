package com.experiment.entity;


import java.util.Date;

/**
 * @author chenxuegui
 * @since 2024/3/8
 */
public class MomentCounter {

    private Long mid;

    private Long numLike;

    private Long numComment;

    private Long numUV;

    private Date createTime;

    private Date updateTime;

    public MomentCounter() {
    }

    public MomentCounter(Long mid) {
        this.mid = mid;
        this.createTime = new Date();
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }



    public Long getNumLike() {
        return numLike;
    }

    public void setNumLike(Long numLike) {
        this.numLike = numLike;
    }

    public Long getNumComment() {
        return numComment;
    }

    public void setNumComment(Long numComment) {
        this.numComment = numComment;
    }

    public Long getNumUV() {
        return numUV;
    }

    public void setNumUV(Long numUV) {
        this.numUV = numUV;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
