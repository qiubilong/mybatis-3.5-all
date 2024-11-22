package com.experiment.mapper;

import com.experiment.entity.MomentNum;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/3/21
 */
public interface MomentCommentExtMapper {

    /**
     * 变更-评论-回复数
     */
    @Update("update moment_comment set reply_num = reply_num + #{changeNum} where id = #{commentId}")
    boolean updateCommentReplyNum(@Param("commentId") Long commentId, @Param("changeNum") Integer changeNum);

    /**
     * 批量查询是否存在点赞
     */
    @Select("<script>" +
            "select comment_id from moment_comment_praise where  user_id = #{userId}" +
            " and comment_id in " +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    List<Long> ifUserPraisedBatch(@Param("ids") List<Long> ids, @Param("userId") Long userId);

}