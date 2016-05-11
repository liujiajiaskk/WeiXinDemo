package test;

import net.sf.json.JSONObject;
import menu.Menu;
import entity.AccessToken;
import utilities.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("票据"+token.getToken());
			System.out.println("有效时间"+token.getExpiresIn());
			
			Menu menu = WeixinUtil.initMenu();
			JSONObject jsonObject = JSONObject.fromObject(menu);
			int result = WeixinUtil.createMenu(token.getToken(), jsonObject.toString());
			if(result == 0){
				System.out.println("菜单创建成功");
			}else{
				System.out.println(result);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
