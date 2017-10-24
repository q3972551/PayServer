package com.servlet;

import java.io.IOException;
import java.util.HashMap;
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
import com.xml.XMLUtil;

public class WeChatResultPayServlet extends HttpServlet
{
	//	private Logger m_logger = Logger.getLogger(WeChatResultPayServlet.class);
	private static final long serialVersionUID = 1L;
	public WeChatResultPayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strxml = request.getRequestURI();
		String str    = "";
		String notityXml = "";
		String inputLine = null;
		try {

			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
			System.out.println("strxml:" + strxml);
			System.out.println("notityXml:" + notityXml);

			Map<String,String> requestMap = XMLUtil.doXMLParse(notityXml);
			String sign = requestMap.get("sign");
			String outradeNo  = requestMap.get("out_trade_no");
			String payFee     = requestMap.get("total_fee");
			
			
			WxPayData data = WxPayProvider.getInstance().getData(outradeNo);
			Map<String,String> map = new HashMap<String,String>();
			boolean isOk = false;
			if ("0".equals(data.getStatus()))
			{
				String requestSign = WXPayRequest.formatUrlMapToSign(requestMap);
				if(sign.equals(requestSign)&& payFee.equals(String.valueOf(data.getTotalfee())))
				{
					isOk = true;
					RedisTools.putForHash("tradeNo_status", outradeNo, PayStatus.SUCCESS.Status());
				}
				else
				{
					RedisTools.putForHash("tradeNo_status", outradeNo, PayStatus.FAILURE.Status());
				}

				data.setEndtime(System.currentTimeMillis());
				data.setStatus(PayStatus.SUCCESS.Status());
				WxPayProvider.getInstance().update(data);
			}
			if (isOk)
			{
				map.put("return_code", "SUCCESS");
				map.put("return_msg", "OK");
			}
			else
			{
				map.put("return_code", "SUCCESS");
				map.put("return_msg", "签名失败");
			}


			String xml = WXPayRequest.getRequestXml(map);
			response.getWriter().write(xml);
		}
		catch(Exception e)
		{

			e.printStackTrace();
			Map<String,String> map = new HashMap<String,String>();
			map.put("return_code", "SUCCESS");
			map.put("return_msg", "OK");
			String xml = WXPayRequest.getRequestXml(map);
			response.getWriter().write(xml);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
