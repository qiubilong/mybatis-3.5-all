package com.experiment.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author chenxuegui
 * @since 2024/3/21
 */
public interface MomentCommentExtMapper {

    /**
     * 变更-评论-回复数
     */
    @Update("update moment_comment set reply_num = reply_num + #{changeNum} where id = #{commentId}")
    int updateCommentReplyNum(@Param("commentId") Long commentId, @Param("changeNum") Integer changeNum);
}