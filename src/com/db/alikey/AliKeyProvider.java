package com.db.alikey;

import java.sql.SQLException;
import java.util.List;

import com.common.module.IModule;
import com.db.DBProviderModule;
import com.db.alikey.dao.AliKeyDataDAO;
import com.db.alikey.dao.AliKeyDataDAOImpl;
import com.db.alikey.entity.AliKeyData;
import com.db.alikey.entity.AliKeyDataExample;

public class AliKeyProvider  implements IModule
{
	private static AliKeyProvider s_instance = null;
	private AliKeyDataDAO m_dao = new AliKeyDataDAOImpl(DBProviderModule.getInstance().getRoleSqlMapClient());
	private AliKeyProvider()
	{
		
	}
	
	public static AliKeyProvider getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new AliKeyProvider();
		}
		
		return s_instance;
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AliKeyData> getData(AliKeyDataExample ex)
	{
		List<AliKeyData> list = null;
		try {
			list = this.m_dao.selectByExampleWithBLOBs(ex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
