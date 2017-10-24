package com.logic.ali;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.module.IModule;
import com.db.alikey.AliKeyProvider;
import com.db.alikey.entity.AliKeyData;
import com.db.alikey.entity.AliKeyDataExample;
import com.db.alikey.entity.AliKeyDataWithBLOBs;

public class AliPayManager implements IModule
{
	private static AliPayManager s_instance = null;
	private Map<String,AliKeyData> m_map = new HashMap<String,AliKeyData>();
	
	private AliPayManager()
	{
		
	}
	
	public static AliPayManager getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new AliPayManager();
		}
		
		return s_instance;
	}
	
	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		AliKeyDataExample ex = new AliKeyDataExample();
		ex.createCriteria().andBundleidIsNotNull();
		
		List<AliKeyData> list = AliKeyProvider.getInstance().getData(ex);
		
		for (AliKeyData data :list)
		{
			m_map.put(data.getBundleid(), data);
		}
		
	}
	
	public AliKeyData getData(String bundleid)
	{
		return this.m_map.get(bundleid);
	}

}
