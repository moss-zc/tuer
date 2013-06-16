package net.ice4c.tuer.pojo;

public class NoteBook {
	/**
	 * 日记本编号
	 */
	private Long id;
	/**
	 * 日记本名称
	 */
	private String name;
	/**
	 * 创建用户
	 */
	private String created_user;
	/**
	 * 背景颜色
	 */
	private String bgcolor;
	/**
	 * 链接地址
	 */
	private String pageurl;
	/**
	 * 创建时间
	 */
	private String created_at;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated_user() {
		return created_user;
	}
	public void setCreated_user(String created_user) {
		this.created_user = created_user;
	}
	public String getBgcolor() {
		return bgcolor;
	}
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	public String getPageurl() {
		return pageurl;
	}
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
}
