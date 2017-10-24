/**
 * @author peanut
 * 启动类的servlet,实现init方法
 */

package com;

import javax.servlet.http.HttpServlet;

import com.common.module.ModuleManager;

public class StartUpServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init()
	{
		ModuleManager.getInstance().init();
		System.out.println("Start up!");
	}
	
	

}
