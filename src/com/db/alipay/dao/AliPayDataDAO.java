package com.db.alipay.dao;

import com.db.alipay.entity.AliPayData;
import com.db.alipay.entity.AliPayDataExample;
import java.sql.SQLException;
import java.util.List;

public interface AliPayDataDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    void insert(AliPayData record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int updateByPrimaryKey(AliPayData record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int updateByPrimaryKeySelective(AliPayData record) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    List selectByExample(AliPayDataExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    AliPayData selectByPrimaryKey(String id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int deleteByExample(AliPayDataExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int deleteByPrimaryKey(String id) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int countByExample(AliPayDataExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int updateByExampleSelective(AliPayData record, AliPayDataExample example) throws SQLException;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table alipay
     *
     * @abatorgenerated Mon Oct 16 12:07:10 CST 2017
     */
    int updateByExample(AliPayData record, AliPayDataExample example) throws SQLException;
}