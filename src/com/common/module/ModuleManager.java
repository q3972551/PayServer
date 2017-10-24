package com.common.module;

import com.db.DBProviderModule;
import com.db.alipay.AliPayProvider;
import com.db.applepay.ApplePayProvider;
import com.db.wxpay.WxPayProvider;
import com.logic.ali.AliPayManager;

public class ModuleManager extends IModuleManager{
	private static ModuleManager instance;

	private ModuleManager() {

	}

	public static ModuleManager getInstance() {
		if (instance == null) {
			instance = new ModuleManager();
		}
		return instance;
	}

	public void init() {
		this.initDataBase();
		this.registerModules();
		super.init();
		this.registDBModule();
		
	}
	
	private void initDataBase()
	{
	}
	
	private void registerModules()
	{
		
		this.registerModule(DBProviderModule.getInstance());
		this.registerModule(AliPayManager.getInstance());
	}
	
	private void registDBModule()
	{
		ApplePayProvider.getInstance().init();
		AliPayProvider.getInstance().init();
		WxPayProvider.getInstance().init();
	}
}
