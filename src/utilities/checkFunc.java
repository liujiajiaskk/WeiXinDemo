package utilities;

import java.util.Arrays;
import utilities.sha1;
public class checkFunc {
	public static String token = "liujiajiaskk";
	public static Boolean checkSignature(String signature,String timestamp,String nonce){
		//��token��timestamp��nonce�������������ֵ�������
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);
		
		//�����������ַ���ƴ�ӳ�һ���ַ�������sha1����
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<array.length; i++){
			buffer.append(array[i]);
		}
		
		//�����߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		if(signature.equals(sha1.hex_sha1(buffer.toString()))){
			return true;
		}else {
			return false;
		}
		
	}
	
	
}
