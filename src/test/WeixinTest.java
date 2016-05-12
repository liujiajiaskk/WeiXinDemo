package test;

import net.sf.json.JSONObject;
import menu.Menu;
import entity.AccessToken;
import utilities.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args) {
		try {
			//AccessToken获取测试
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("票据:"+token.getToken());
			System.out.println("有效时间:"+token.getExpiresIn());
			//菜单创建测试			
			Menu menu = WeixinUtil.initMenu();
			JSONObject jsonObject = JSONObject.fromObject(menu);
			System.out.println(jsonObject.toString());
			int result = WeixinUtil.createMenu(token.getToken(), jsonObject.toString());
			if(result == 0){
				System.out.println("菜单创建成功");
			}else{
				System.out.println("菜单创建失败，错误代码："+result);
			}	
			
			//查询菜单
//			JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
//			System.out.println(jsonObject.toString());
			
			//删除菜单
//			int result = WeixinUtil.deleteMenu(token.getToken());
//			if(result == 0){
//				System.out.println("菜单删除成功");
//			}else{
//				System.out.println("菜单删除失败，错误代码："+result);
//			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
