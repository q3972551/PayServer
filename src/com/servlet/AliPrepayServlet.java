package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.db.alipay.AliPayProvider;
import com.db.alipay.entity.AliPayData;
import com.logic.order.OrderManager;
import com.pay.PayStatus;
import com.redis.RedisTools;

public class AliPrepayServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String appid = request.getParameter("appid");
		String bundleid = request.getParameter("bundle_id");
		String out_trade_no = OrderManager.getInstance().createOrderNo();
		Integer totalfee = Integer.valueOf(request.getParameter("total_fee"));
		
		AliPayData data = new AliPayData();
		data.setAppid(appid);
		data.setBundleid(bundleid);
		data.setId(out_trade_no);
		data.setStarttime(System.currentTimeMillis());
		data.setTotalfee(totalfee);
		data.setStatus(PayStatus.START.Status());
		AliPayProvider.getInstance().insert(data);
		RedisTools.putForHash("tradeNo_status", out_trade_no, PayStatus.START.Status());
		JSONObject ob = new JSONObject();
		ob.put("out_trade_no", out_trade_no);
		response.getWriter().write(ob.toString());
		
	}

}
