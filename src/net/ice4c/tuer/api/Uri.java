package net.ice4c.tuer.api;

public class Uri {
	/**
	 * 应用编号
	 */
	public static final String APPKEY = "bd6430d69f20a72654f4025593eade0c";//
	/**
	 * 密钥
	 */
	public static final String SECRET = "222eadc72f92ddbe64dba224f8d09255";//
	/**
	 * 重定向地址
	 */
	public static final String REDIRECT_URI = "http://doer.me";//
	/**
	 * 获取权限的地址
	 */
	public static final String AUTH_PAGE = "http://tuer.me/oauth/authorize?client_id="+APPKEY+"&redirect_uri="+REDIRECT_URI;//

	/**
	 * 获取用户信息
	 */
	public static final String USERINFO = "http://api.tuer.me/user/info/${ID}";//
	/**
	 * 小头像
	 */
	public static final String AVATAR_SMALL = "http://www.tuer.me/user/avatar/${ID}";//
	/**
	 * 大头像
	 */
	public static final String AVATAR_BIG = "http://www.tuer.me/user/art/${ID}";//
	/**
	 * 关注列表
	 */
	public static final String FOLLOW = "http://api.tuer.me/user/follow/${ID}";//
	/**
	 * 编辑用户
	 */
	public static final String EDIT_USER = "http://api.tuer.me/user/edit/${ID}";//
	/**
	 * 添加/取消关注
	 */
	public static final String USER_ATTENTION = "http://api.tuer.me/user/attention/${ID}";//
	/**
	 * 活跃：前十五
	 */
	public static final String HOT_USERS = "http://api.tuer.me/user/hots";//
	/**
	 * 新用户：前十五
	 */
	public static final String NEW_USERS = "http://api.tuer.me/user/news";//
	/**
	 * feed协议
	 */
	public static final String FEEDS = "http://api.tuer.me/feed/news";//
	/**
	 * 日记详情，did为日记编号
	 */
	public static final String DIARY_INFO = "http://api.tuer.me/diary/info/${DID}";//
	
	/**
	 * 修改日记，did为日记编号
	 */
	public static final String DIARY_EDIT = "http://api.tuer.me/diary/edit/${DID}";//
	/**
	 * 删除日记，did为日记编号
	 */
	public static final String DIARY_DEL = "http://api.tuer.me/diary/del/${DID}";//
	/**
	 * 新增日记，did为日记编号
	 */
	public static final String DIARY_SAVE = "http://api.tuer.me/diary/save";//
	/**
	 * 用户日记列表
	 */
	public static final String USER_DIARY_LIST = "http://api.tuer.me/diaries/user/${UID}";//
	/**
	 * 日记本日记列表
	 */
	public static final String BOOK_DIARY_LIST = "http://api.tuer.me/diaries/notebook//${BOOKID}";//
	/**
	 * 新日记列表
	 */
	public static final String DIARY_NEWS = "http://api.tuer.me/diaries/news";//
	/**
	 * 新日记列表
	 */
	public static final String FOLLOW_DIARYS = "http://api.tuer.me/diaries/follow/${ID}";//
}
