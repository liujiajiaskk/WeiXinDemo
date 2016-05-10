package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import entity.TextMessage;
/**
 * 消息类，处理各种消息响应
 * @author skk
 *
 */
public class messageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_EVENT = "event";
	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_SCAN = "SCAN";
	public static final String EVENT_LOCATION = "LOCATION";
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";
	
	/**
	 * xml转成MAP集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 文本消息转XML
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 封装文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static TextMessage initMessageText(String toUserName,String fromUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MESSAGE_TEXT);
		textMessage.setContent(content);
		return textMessage;
	}
	
	/**
	 * 关注微信事件
	 * @return
	 */
	public static String eventSubscribe(){
		StringBuffer sb = new StringBuffer();
		sb.append("您好，欢迎关注SKK微信公众号。\n");
		sb.append("SKK关注生活，关注GIS，关注WEB。\n");
		return sb.toString();
	}
	/**
	 * 文本信息回复
	 * @param content
	 * @return
	 */
	public static String messageTextReply(String content){
		if("1".equals(content)){
			StringBuffer sb = new StringBuffer();
			sb.append("SKK出生地点：江苏省盐城市阜宁县陈集镇陈集村\n");
			sb.append("SKK出生时间：1986年9月初四\n");
			return sb.toString();
		}
		else{
			return "你输入的消息是："+content;
		}		
	}
}
