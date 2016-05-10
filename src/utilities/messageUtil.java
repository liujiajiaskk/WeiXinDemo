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
 * ��Ϣ�࣬���������Ϣ��Ӧ
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
	 * xmlת��MAP����
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
	 * �ı���ϢתXML
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * ��װ�ı���Ϣ
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
	 * ��ע΢���¼�
	 * @return
	 */
	public static String eventSubscribe(){
		StringBuffer sb = new StringBuffer();
		sb.append("���ã���ӭ��עSKK΢�Ź��ںš�\n");
		sb.append("SKK��ע�����עGIS����עWEB��\n");
		return sb.toString();
	}
	/**
	 * �ı���Ϣ�ظ�
	 * @param content
	 * @return
	 */
	public static String messageTextReply(String content){
		if("1".equals(content)){
			StringBuffer sb = new StringBuffer();
			sb.append("SKK�����ص㣺����ʡ�γ��и����س¼���¼���\n");
			sb.append("SKK����ʱ�䣺1986��9�³���\n");
			return sb.toString();
		}
		else{
			return "���������Ϣ�ǣ�"+content;
		}		
	}
}
