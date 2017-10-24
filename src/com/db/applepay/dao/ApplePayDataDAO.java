package com.db.applepay.dao;

import com.db.applepay.entity.ApplePayData;
import com.db.applepay.entity.ApplePayDataExample;
import java.sql.SQLException;
import java.util.List;

public interface ApplePayDataDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	void insert(ApplePayData record) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int updateByPrimaryKey(ApplePayData record) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int updateByPrimaryKeySelective(ApplePayData record) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	List selectByExample(ApplePayDataExample example) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	ApplePayData selectByPrimaryKey(String id) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int deleteByExample(ApplePayDataExample example) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int deleteByPrimaryKey(String id) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int countByExample(ApplePayDataExample example) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int updateByExampleSelective(ApplePayData record,
			ApplePayDataExample example) throws SQLException;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table applepay
	 * @abatorgenerated  Mon Oct 16 11:23:17 CST 2017
	 */
	int updateByExample(ApplePayData record, ApplePayDataExample example)
			throws SQLException;
}