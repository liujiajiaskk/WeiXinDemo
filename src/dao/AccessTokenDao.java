package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import utilities.DateUtil;
import entity.AccessToken;

public class AccessTokenDao {
	
	private static String xmlPath = "WebRoot/WEB-INF/access_token.xml";
	
	public static AccessToken getAccessTokenFromXml() throws ParseException{
		AccessToken token = new AccessToken();			
		try {
			Map<String,String> map = new HashMap<String, String>();
			SAXReader sr = new SAXReader();		
			Document document = sr.read(new File(xmlPath));
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			for(Element e:list){
				map.put(e.getName(), e.getText());
			}
			token.setToken(map.get("token"));
			token.setExpiresIn(Integer.parseInt(map.get("expiresIn")));
			token.setCreateTime(DateUtil.parse(map.get("createTime"),"yyyy-MM-dd HH:mm:ss"));			
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

		return token;
	}
	
	public static void setAccessTokenToXml(AccessToken accessToken) throws IOException{
		XStream xstream = new XStream();
		xstream.alias("xml", accessToken.getClass());
	
		try {
			Document dcmt = DocumentHelper.parseText(xstream.toXML(accessToken));
			saveDocumentToFile(dcmt, xmlPath, true, "UTF-8");
		} catch (DocumentException e) {
			e.printStackTrace();
		}			
	}
	
	 /**
	  * 方法描述：<b>存储完整XML文件.</b></br> 
	  * 备 注: </br> 
	  * 创 建 人: zyl</br> 
	  * 创建日期:2013-3-18</br>
	  * 
	  * @param document
	  * @param xmlFilePath
	  * @param isTrimText
	  * @param xmlEncoding
	 * @throws IOException 
	  */
	 public static void saveDocumentToFile(Document document, String xmlFilePath, boolean isTrimText,
	   String xmlEncoding) throws IOException {
	  if (document == null || xmlFilePath == null || "".equals(xmlFilePath)) {
	   return;
	  }

	  File file = new File(xmlFilePath);
	  // 判断路径是否存在，不存在创建
	  if (!file.exists()) {
	   file.createNewFile();
	  }
	  // 保存文件
	  OutputFormat format = null;

	  if (isTrimText) {
	   format = OutputFormat.createPrettyPrint();
	  } else {
	   format = createPrettyPrint();// 保留xml属性值中的回车换行
	  }

	  if (xmlEncoding != null) {
	   format.setEncoding(xmlEncoding);// GBK
	  } else {
	   format.setEncoding("UTF-8");// UTF-8
	  }

	  try {
	   org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(
	     new FileOutputStream(xmlFilePath), format);// FileOutputStream可以使UTF-8生效
	   xmlWriter.write(document);
	   xmlWriter.close();
	  } catch (IOException e) {
	   System.out.println("保存XML异常：" + e.getMessage());
	   System.out.println("正在保存的文件名是：" + xmlFilePath);
	  }
	  // 存到文件中结束
	 }
	 
	 /**
	  * 方法描述：<b>dom4j输出格式化..</b></br>
	  * 备          注: 保存Document到xml文件时，xml属性中的回车换行需要保留，
	  *    可是发现在使用过程中发现dom4j自动把回车换行去掉了。特写此类</br>
	  * 创  建   人: yanlei.zhao</br>
	  * 创建日期: 2011-12-1</br>
	  * @return
	  */
	    public static OutputFormat createPrettyPrint() {   
	        OutputFormat format = new OutputFormat();   
	        format.setIndentSize(2);   
	        format.setNewlines(true);   
	        format.setTrimText(false); // 覆盖 父类的  format.setTrimText(true);  
	        format.setPadText(true);  

	        return format;   
	    }
}
