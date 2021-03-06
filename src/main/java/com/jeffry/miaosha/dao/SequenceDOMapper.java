package com.jeffry.miaosha.dao;

import com.jeffry.miaosha.dataobject.SequenceDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Sep 23 11:16:28 CST 2020
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Sep 23 11:16:28 CST 2020
     */
    int insert(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Sep 23 11:16:28 CST 2020
     */
    int insertSelective(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Sep 23 11:16:28 CST 2020
     */
    SequenceDO selectByPrimaryKey(String name);


    SequenceDO getSequenceByName(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Sep 23 11:16:28 CST 2020
     */
    int updateByPrimaryKeySelective(SequenceDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Wed Sep 23 11:16:28 CST 2020
     */
    int updateByPrimaryKey(SequenceDO record);
}