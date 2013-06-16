package net.ice4c.tuer.pojo;

/**
 * 日志类
 * @author ice4c
 *
 */
public class Diary {
	/**
	 * 编号
	 */
	private Long id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 日记本名称
	 */
	private String bookname;
	/**
	 * 日记本编号
	 */
	private String bookid;
	/**
	 * 引用地址
	 */
	private String pageurl;
	/**
	 * 日记类型，1为通告，不返回content
	 */
	private String privacy;
	/**
	 * 写日志的地点
	 */
	private String location;
	/**
	 * 心情
	 */
	private String mood;
	/**
	 * 天气
	 */
	private String weather;
	/**
	 * 日记本的标题img
	 */
	private String img;
	/**
	 * 
	 */
	private String created_at;
	/**
	 * 创建用户编号
	 */
	private String created_user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getPageurl() {
		return pageurl;
	}
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getCreated_user() {
		return created_user;
	}
	public void setCreated_user(String created_user) {
		this.created_user = created_user;
	}
	
	
}
