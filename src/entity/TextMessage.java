package entity;



public class TextMessage {
	String ToUserName;		//������΢�ź�
	String FromUserName;	//���ͷ��ʺţ�һ��OpenID��
	long CreateTime;		//��Ϣ����ʱ�� �����ͣ�
	String MsgType;			//text
	String Content;			//�ı���Ϣ����
	String MsgId;			//��Ϣid��64λ����
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
	
}
