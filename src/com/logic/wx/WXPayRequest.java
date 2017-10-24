package com.logic.wx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import net.sf.json.JSONObject;
import com.config.ServerConfig;
import com.logic.order.OrderManager;
import com.tools.MD5Util;
import com.tools.StringUtil;
import com.xml.XMLUtil;

public class WXPayRequest {

	private static String Url = "https://api.mch.weixin.qq.com";
	private static String Path = "/pay/unifiedorder";
	private static String Key = "752E22887D7BF266F04EF93F597B2A1E";

	/**
	 * 
	 * @param appid 应用id
	 * @param mch_id 商户号
	 * @param body 应用名
	 * @param total_fee 总金额
	 * @param ip  ip地址
	 * @return
	 */
	public static Map<String,String> Prepay_id(String appid,String mch_id,String body,String total_fee,
			String ip)
	{
		String device_info  = "WEB";
		String out_trade_no = OrderManager.getInstance().createOrderNo();
		String nonce_str = StringUtil.getAlphaCode(32);
		String notify_url = ServerConfig.OUTIP();
		String trade_type = "APP";

		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str",nonce_str);	
		parameters.put("body",body);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", total_fee);
		parameters.put("spbill_create_ip", ip);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		parameters.put("device_info", device_info);

		String mySign = formatUrlMapToSign(parameters);
		parameters.put("sign", mySign);
		
		Map<String, String> parameterMap2 = new HashMap<String, String>();  
		try{
			String requestXML = getRequestXml(parameters);  
			//调用统一下单接口
			String result = httpsRequest(Url + Path, "POST", requestXML);
		    Map<String, String> map = XMLUtil.doXMLParse(result);
           
		    if (map.get("return_code").equals("FAIL"))
		    {
		    	return null;
		    }
		    else
		    {
		    	parameterMap2.put("prepay_id", map.get("prepay_id"));  
	            parameterMap2.put("noncestr", nonce_str);  
	            parameterMap2.put("timestamp", String.valueOf(System.currentTimeMillis()));
	            parameterMap2.put("sign", mySign);
	            parameterMap2.put("out_trade_no",out_trade_no);
		    }
            
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return parameterMap2;
	}

	public static String formatUrlMapToSign(Map<String, String> paraMap)
	{
		String buff = "";
		Map<String, String> tmpMap = paraMap;

		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
		// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()
				{
			@Override
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
			{
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
				});
		// 构造URL 键值对的格式
		StringBuilder buf = new StringBuilder();
		for (Map.Entry<String, String> item : infoIds)
		{
			String key = item.getKey();
			if(!key.equals("sign"))
			{
				String val = item.getValue();
				buf.append(key + "=" + val);
				buf.append("&");
			}
		}
		buff = buf.append("key=").append(Key).toString();
		String sign = MD5Util.Md5(buff).toUpperCase();
		return sign;
	}

	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters  请求参数
	 * @return
	 */
	public static String getRequestXml(Map<String,String> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>)it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)
			 || "return_code".equalsIgnoreCase(k) || "return_msg".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg  返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}


	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			//          log.error("连接超时：{}", ce);
		} catch (Exception e) {
			//          log.error("https请求异常：{}", e);
		}
		return null;
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
		JSONObject jsonObject = null;
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			//conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(3000);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			//conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			//                    log.error("连接超时：{}", ce);
		} catch (Exception e) {
			System.out.println(e);
			//                    log.error("https请求异常：{}", e);
		}
		return jsonObject;
	}
}


