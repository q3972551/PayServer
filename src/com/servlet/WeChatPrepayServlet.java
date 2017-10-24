package com.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.db.wxpay.WxPayProvider;
import com.db.wxpay.entity.WxPayData;
import com.logic.wx.WXPayRequest;
import com.pay.PayStatus;
import com.redis.RedisTools;

public class WeChatPrepayServlet extends HttpServlet
{

	/**
	 * 
	 */
//	private Logger m_logger = Logger.getLogger(WeChatPrepayServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			String appid   = request.getParameter("appid");
			String mch_id  = request.getParameter("mch_id");
			String body    = request.getParameter("body");
			
			String ip      = request.getParameter("ip");
			String total_fee = request.getParameter("total_fee");
			String bundleid  = request.getParameter("bundle_id"); 
			Map<String,String> map = WXPayRequest.Prepay_id(appid, mch_id, body,total_fee, ip);


			WxPayData data = new WxPayData();
			data.setAppid(appid);
			data.setBundleid(bundleid);
			data.setStarttime(System.currentTimeMillis());
			data.setId(map.get("out_trade_no"));
			data.setSign(map.get("sign"));
			data.setTotalfee(Integer.valueOf(total_fee));
			data.setStatus(PayStatus.START.Status());
			WxPayProvider.getInstance().insert(data);
			RedisTools.putForHash("tradeNo_status", map.get("out_trade_no"), PayStatus.START.Status());

			JSONObject ob = JSONObject.fromObject(map);
			response.getWriter().write(ob.toString());

		}
		catch(Exception e)
		{
			e.printStackTrace();
//			m_logger.error("WeChatPrepayServlet.class", e);
			JSONObject jo = new JSONObject();
			jo.put("status", 1);
			response.getWriter().write(jo.toString());
		}
	}

}
