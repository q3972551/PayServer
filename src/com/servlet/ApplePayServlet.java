package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.db.applepay.ApplePayProvider;
import com.db.applepay.entity.ApplePayData;
import com.pay.PayStatus;
import com.tools.IOS_Verify;

public class ApplePayServlet extends HttpServlet
{
	/**
	 * 
	 */
//	private Logger m_logger = Logger.getLogger(ApplePayServlet.class);
	private static final long serialVersionUID = 1L;

	public ApplePayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			String transactionReceipt = request.getParameter("transactionReceipt");
			String bundleid = request.getParameter("bundle_id");
			int    totalfee = Integer.valueOf(request.getParameter("total_fee"));


			JSONObject obj = IOS_Verify.buyAppVerify(transactionReceipt,"Sandbox");

			boolean isOK = false;
			String product_id = null;
			String transaction_id = null;
			JSONObject ob = new JSONObject();
			if (obj != null)
			{
				String status = obj.getString("status");
				if ("0".equals(status))
				{
					JSONObject obj2 = obj.getJSONObject("receipt");
					String bundle_id = obj2.getString("bid");

					if (bundleid.equals(bundle_id) && "0".equals(status))
					{
						transaction_id = obj2.getString("transaction_id");
						ob.put("transaction_id", transaction_id);
						if (!this.isExist(transaction_id))
						{
							isOK = true;
							product_id     = obj2.getString("product_id");
							ob.put("product_id", product_id);
							ApplePayData data = new ApplePayData();
							data.setTime(System.currentTimeMillis());
							data.setStatus(PayStatus.SUCCESS.Status());
							data.setId(transaction_id);
							data.setTotalfee(totalfee);
							data.setBundleid(bundleid);
							ApplePayProvider.getInstance().insert(data);
						}
					}
				}
			}
			ob.put("status", isOK ? 0 : 1);
			response.getWriter().write(ob.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			m_logger.error("ApplePayServlet.class", e);
			response.getWriter().write("status:1");
		}
	}


	private boolean isExist(String transaction_id)
	{
		ApplePayData data = ApplePayProvider.getInstance().getData(transaction_id);
		if (data != null)
		{
			return true;
		}

		return false;
	}

}
