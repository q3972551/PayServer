package com.common.module;

import java.util.ArrayList;
import java.util.List;

public abstract class IModuleManager
{

	protected List<IModule> list = new ArrayList<IModule>();

	public void registerModule(IModule module)
	{
		list.add(module);
	}

	// 初始化
	protected void init()
	{
		for (IModule module : list)
		{
			try
			{
				module.init();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
