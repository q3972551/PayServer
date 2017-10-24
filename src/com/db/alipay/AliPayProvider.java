package com.db.alipay;

import java.sql.SQLException;

import com.common.module.IModule;
import com.db.DBProviderModule;
import com.db.alipay.dao.AliPayDataDAO;
import com.db.alipay.dao.AliPayDataDAOImpl;
import com.db.alipay.entity.AliPayData;

public class AliPayProvider implements IModule
{
	private static AliPayProvider s_instance = null;
	private AliPayDataDAO m_dao = new AliPayDataDAOImpl(DBProviderModule.getInstance().getRoleSqlMapClient());
	private AliPayProvider()
	{
		
	}
	
	public static AliPayProvider getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new AliPayProvider();
		}
		
		return s_instance;
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void insert(AliPayData data)
	{
		try {
			this.m_dao.insert(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AliPayData getData(String id)
	{
		AliPayData data = null;
		try {
			data = this.m_dao.selectByPrimaryKey(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public boolean update(AliPayData data)
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
