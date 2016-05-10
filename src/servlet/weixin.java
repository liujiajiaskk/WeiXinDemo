package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import entity.TextMessage;

import utilities.checkFunc;
import utilities.messageUtil;

public class weixin extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public weixin() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		if(checkFunc.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			Map<String,String> map = messageUtil.xmlToMap(request);
			String toUserName = map.get("ToUserName");		//开发者微信号
			String fromUserName = map.get("FromUserName");	//发送方帐号（一个OpenID）
			String msgType = map.get("MsgType");			//text
			String content = map.get("Content");			//文本消息内容
			
			String message = null;
			
			if(messageUtil.MESSAGE_TEXT.equals(msgType)){
				message = messageUtil.textMessageToXml(messageUtil.initMessageText(toUserName, fromUserName,messageUtil.messageTextReply(content)));			
			}else if(messageUtil.MESSAGE_EVENT.equals(msgType)){
				String event = map.get("Event");
				if(messageUtil.EVENT_SUBSCRIBE.equals(event)){
					message = messageUtil.textMessageToXml(messageUtil.initMessageText(toUserName, fromUserName,messageUtil.eventSubscribe()));
				}				
			}			
			out.print(message);			
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}

}
