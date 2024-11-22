package com.experiment.mapper;

import com.experiment.entity.MomentCounter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/3/8
 */
public interface MomentCounterMapper  {


    @Insert({"<script>insert into moment_counter " +
            "(mid, num_like, num_comment, num_uv, factor) " +
            "values " +
            "<foreach collection='list' item='vo' separator=','> " +
            "(#{vo.mid}, #{vo.numLike}, #{vo.numComment}, #{vo.numUV})" +
            "</foreach> " +
            "on duplicate key update " +
            "num_like = VALUES(num_like)," +
            "num_comment = VALUES(num_comment)," +
            "num_uv = VALUES(num_uv)," +
            "</script>"})
    int batchUpdateCounter(@Param("list") List<MomentCounter> list);

    @Insert("insert into moment_counter(mid, create_time) values ( #{mid}, #{createTime})")
    boolean insertCounter(MomentCounter paramVo);


    /*  sql解析示例
     *  insert into moment_counter (mid, create_time) values
     *  (#{__frch_vo_0.mid}, #{__frch_vo_0.createTime}) ,  (#{__frch_vo_1.mid}, #{__frch_vo_1.createTime})
     */
    @Insert({"<script>insert into moment_counter " +
            "(mid, create_time) " +
            "values " +
            "<foreach collection='list' item='vo' separator=','> " +
            "(#{vo.mid}, #{vo.createTime})"+
            "</foreach> " +
            "</script>"})
    boolean batchInsertCounter(@Param("list") List<MomentCounter> list, @Param("userId") Long userId);

    @Select("<script>" +
            " select mid, num_like from moment_counter where " +
            "  mid in " +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    List<MomentCounter> selectMomentLikeNum(@Param("ids") List<Long> ids);

}



