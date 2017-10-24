package com.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.db.alikey.entity.AliKeyData;
import com.db.alikey.entity.AliKeyDataWithBLOBs;
import com.db.alipay.AliPayProvider;
import com.db.alipay.entity.AliPayData;
import com.logic.ali.AliPayManager;
import com.pay.PayStatus;
import com.redis.RedisTools;

public class AliPayServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("resultStatus").equals("9000"))
		{
			Map<String,String> params = new HashMap<String,String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			try{
				String out_trade_no = request.getParameter("out_trade_no");
				AliPayData data = AliPayProvider.getInstance().getData(out_trade_no);
				String total_amount = request.getParameter("total_amount");
				String sellerid = request.getParameter("seller_id");
				if (data != null)
				{
					String bundleid = data.getBundleid();
					AliKeyData keyData = AliPayManager.getInstance().getData(bundleid);
					AliKeyDataWithBLOBs bData = (AliKeyDataWithBLOBs)keyData;
					String alipaypublicKey = bData.getPubkey();
					boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey, "UTF-8","RSA2");
					boolean isSell = sellerid.equals(bData.getSellerid());
					boolean isfee  = data.getTotalfee() == Integer.valueOf(total_amount);
					
					if (flag && isSell && isfee)
					{
						data.setEndtime(System.currentTimeMillis());
						data.setStatus(PayStatus.SUCCESS.Status());
						AliPayProvider.getInstance().update(data);
						RedisTools.putForHash("tradeNo_status", "out_trade_no", PayStatus.SUCCESS.Status());
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		response.getWriter().write("sucess");
	}


}
