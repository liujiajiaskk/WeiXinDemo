package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;

import menu.Button;
import menu.ClickButton;
import menu.Menu;
import menu.ViewButton;

import entity.AccessToken;
import dao.AccessTokenDao;


public class WeixinUtil {
	private static final String APPID = "wxa67b599d296ae71f";
	private static final String APPSECRET = "56bf569711727d05e4ba8f7b788ab250";
	
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/**
	 * POST请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);		
		HttpEntity entity = response.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}		
		return jsonObject;
	}
	
	/**
	 * 获取accessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws DocumentException 
	 * @throws java.text.ParseException 
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException, java.text.ParseException{
		
		AccessToken token = AccessTokenDao.getAccessTokenFromXml();	
		Date date = new Date();
		long time = date.getTime() - token.getCreateTime().getTime();		
		if("".equals(token.getToken()) || time>token.getExpiresIn()*1000){
						
			String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
			JSONObject jsonObject = doGetStr(url);
			if(jsonObject!=null){
				token.setToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
				token.setCreateTime(date);
				AccessTokenDao.setAccessTokenToXml(token);
			}
		}		
		return token;
	}
	
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("我的生活");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("我的工作");
		button21.setType("view");
		button21.setUrl("http://liujiajiaskk.ngrok.cc");
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button = new Button();
		button.setName("日常工具");
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}
	/**
	 * 创建菜单
	 * @param token
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	/**
	 * 查询菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	/**
	 * 删除菜单
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int deleteMenu(String token) throws ParseException, IOException{
		int result = 0;
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
