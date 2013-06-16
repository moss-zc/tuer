package net.ice4c.tuer.pojo;

/**
 * 兔耳的用户
 * @author ice4c
 *
 */
public class User {
	/**
	 * 用户编号
	 */
	private Long id;
	/**
	 * 用户昵称
	 */
	private String nick;
	/**
	 * 用户的连接地址
	 */
	private String pageurl;
	/**
	 * 暂时不知
	 */
	private String profile;
	/**
	 * 关于用户
	 */
	private String about;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPageurl() {
		return pageurl;
	}
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
}