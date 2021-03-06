package com.qingqiao.vhr.mapper;

import com.qingqiao.vhr.bean.JobLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JObLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobLevel record);

    int insertSelective(JobLevel record);

    JobLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobLevel record);

    int updateByPrimaryKey(JobLevel record);

    int deleteManyJobLevel(@Param("ids") Integer[] ids);

    List<JobLevel> getAllJobLevels();
}