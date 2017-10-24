package com.db;

import java.io.InputStream;

import com.common.module.IModule;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class DBProviderModule implements IModule{
	
	private static DBProviderModule s_instance = null;
	private SqlMapClient m_roleSqlMapClient    = null;
	
	private DBProviderModule()
	{
		
	}
	
	public static DBProviderModule getInstance(){
		if (s_instance == null)
		{
			s_instance = new DBProviderModule();
		}
		return s_instance;
	}
	
	private void initRoleSQLMapClient() throws Exception {
		if (m_roleSqlMapClient == null)
			m_roleSqlMapClient = getSqlMapClient("/dbconfig/role_sqlmapconfig.xml");
	}
	
	private SqlMapClient getSqlMapClient(String ibatisconfig) {
		InputStream heishidbMapConfigFile = DBProviderModule.class.getResourceAsStream(ibatisconfig);
		if (heishidbMapConfigFile == null)
			throw new RuntimeException("cannot found the resource file of " + ibatisconfig);
		SqlMapClient sqlMapperclient = SqlMapClientBuilder.buildSqlMapClient(heishidbMapConfigFile);
		return sqlMapperclient;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			initRoleSQLMapClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SqlMapClient getRoleSqlMapClient() {
		return m_roleSqlMapClient;
	}
	
}
