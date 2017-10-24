package com.logic.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.redis.RedisTools;

public class OrderManager{

	private static OrderManager s_instance = null;

	private OrderManager()
	{

	}

	public static OrderManager getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new OrderManager();
		}

		return s_instance;
	}

	public String createOrderNo()
	{
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date); 
		int count = RedisTools.getCountAutoIncrememtal("order_count");
		StringBuffer buffer = new StringBuffer();
		buffer.append(time).append(count);
		return buffer.toString();
	}
}
