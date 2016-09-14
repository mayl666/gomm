package com.gome.threshold.dao;

import java.util.List;

import com.gome.threshold.domain.prtg.MoNetSensorThreshold;

public interface MoNetSensorThresholdMapper {

    List<MoNetSensorThreshold> selectAll();
    
    List<MoNetSensorThreshold> selectAll2();
    
    int deleteByPrimaryKey(Integer sensorId);

    int insert(MoNetSensorThreshold record);

    int insertSelective(MoNetSensorThreshold record);

    MoNetSensorThreshold selectByPrimaryKey(Integer sensorId);

    int updateByPrimaryKeySelective(MoNetSensorThreshold record);

    int updateByPrimaryKey(MoNetSensorThreshold record);
    
    Integer selectCount(MoNetSensorThreshold record);
}