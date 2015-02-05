package com.schoolyard.school.console.web.vo.user;


/**
 * <p>
 * Title: schoolyard_[学校端]_[个人设置]
 * </p>
 * <p>
 * Description: [基本信息VO]
 * </p>
 * 
 * @author  XXX
 * @version $Revision$ 2014-08-13
 * @author (lastest modification by $Author$)
 * @since  20130601
 */
public class UserInfoVO {
	
	private String loginName;	//用户登陆名
	private String userName;	//用户姓名
	private String photoUrl;	//头像路径
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
