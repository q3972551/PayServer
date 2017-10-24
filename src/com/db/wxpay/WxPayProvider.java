package com.db.wxpay;

import java.sql.SQLException;

import com.common.module.IModule;
import com.db.DBProviderModule;
import com.db.wxpay.dao.WxPayDataDAO;
import com.db.wxpay.dao.WxPayDataDAOImpl;
import com.db.wxpay.entity.WxPayData;

public class WxPayProvider implements IModule
{
	private static WxPayProvider s_instance = null;
	private WxPayDataDAO m_dao = new WxPayDataDAOImpl(DBProviderModule.getInstance().getRoleSqlMapClient());
	private WxPayProvider()
	{
		
	}
	
	public static WxPayProvider getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new WxPayProvider();
		}
		
		return s_instance;
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void insert(WxPayData data)
	{
		try {
			this.m_dao.insert(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WxPayData getData(String id)
	{
		WxPayData data = null;
		try {
			data = this.m_dao.selectByPrimaryKey(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public boolean update(WxPayData data)
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
