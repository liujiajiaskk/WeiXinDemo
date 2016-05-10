package utilities;

import java.util.Arrays;
import utilities.sha1;
public class checkFunc {
	public static String token = "liujiajiaskk";
	public static Boolean checkSignature(String signature,String timestamp,String nonce){
		//将token、timestamp、nonce三个参数进行字典序排序
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);
		
		//将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<array.length; i++){
			buffer.append(array[i]);
		}
		
		//开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if(signature.equals(sha1.hex_sha1(buffer.toString()))){
			return true;
		}else {
			return false;
		}
		
	}
	
	
}
