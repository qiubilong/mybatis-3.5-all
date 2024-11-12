package com.experiment.mapper;


import com.experiment.entity.MomentPraise;

import java.util.List;

/**
 * 点赞Mapper接口
 *
 * @author Ashin
 * @date 2021-07-08
 */
public interface MomentPraiseMapper {
    /**
     * 查询点赞
     *
     * @param id 点赞ID
     * @return 点赞
     */
     MomentPraise selectMomentPraiseById(Long id);

    /**
     * 查询点赞列表
     *
     * @param momentPraise 点赞
     * @return 点赞集合
     */
     List<MomentPraise> selectMomentPraiseList(MomentPraise momentPraise);

    /**
     * 新增点赞
     *
     * @param momentPraise 点赞
     * @return 结果
     */
     int insertMomentPraise(MomentPraise momentPraise);

    /**
     * 修改点赞
     *
     * @param momentPraise 点赞
     * @return 结果
     */
     int updateMomentPraise(MomentPraise momentPraise);

    /**
     * 删除点赞
     *
     * @param id 点赞ID
     * @return 结果
     */
     int deleteMomentPraiseById(Long id);

    /**
     * 批量删除点赞
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteMomentPraiseByDynamicIds(Long[] ids);
}
