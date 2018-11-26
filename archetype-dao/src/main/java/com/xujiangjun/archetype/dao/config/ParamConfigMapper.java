package com.xujiangjun.archetype.dao.config;

import com.xujiangjun.archetype.dao.base.BaseMapper;
import com.xujiangjun.archetype.model.config.ParamConfigDO;
import org.springframework.stereotype.Repository;

/**
 * 参数配置Mapper
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Repository
public interface ParamConfigMapper extends BaseMapper<ParamConfigDO> {

    ParamConfigDO selectByParamNo(String paramNo);

    int updateByParamNoSelective(ParamConfigDO paramConfigDO);
}