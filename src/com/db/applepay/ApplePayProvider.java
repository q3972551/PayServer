package com.db.applepay;

import java.sql.SQLException;

import com.common.module.IModule;
import com.db.DBProviderModule;
import com.db.applepay.dao.ApplePayDataDAO;
import com.db.applepay.dao.ApplePayDataDAOImpl;
import com.db.applepay.entity.ApplePayData;

public class ApplePayProvider implements IModule
{
	private static ApplePayProvider s_instance = null;
	private ApplePayDataDAO m_dao = new ApplePayDataDAOImpl(DBProviderModule.getInstance().getRoleSqlMapClient());
	private ApplePayProvider()
	{
		
	}
	
	public static ApplePayProvider getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new ApplePayProvider();
		}
		
		return s_instance;
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void insert(ApplePayData data)
	{
		try {
			this.m_dao.insert(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ApplePayData getData(String id)
	{
		ApplePayData data = null;
		try {
			data = this.m_dao.selectByPrimaryKey(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public boolean update(ApplePayData data)
	{
		boolean isOK = true;
		try {
			this.m_dao.updateByPrimaryKeySelective(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isOK = false;
		}
		
		return isOK;
	}

}
