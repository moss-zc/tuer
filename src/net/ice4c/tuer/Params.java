package net.ice4c.tuer;

public class Params {
	public final static String URL_MSITE = "http://m.tuer.me";// 兔耳手机主页
	public final static String URL_ALL_DIARYS = "http://m.tuer.me/diaries";// 兔耳手机查看全部
	public final static String URL_USER_HEAD = "http://www.tuer.me/user/avatar/";// 兔耳的头像前缀
	public final static String URL_FULL_SITE = "http://www.tuer.me";// 暂时是不能用的
	public final static String URL_LOGIN = "http://m.tuer.me/login";// 兔耳手机站的登录地址
	public final static String URL_WRITE = "http://m.tuer.me/diary/write";// 兔耳手机的写日记地址
	public final static String URL_COMMENT_SAVE = "http://m.tuer.me/comment/save";// 兔耳手机的回复地址

	public final static String PATTERN_SINGLE_DIARY_INFO_NOLOG = "<h2>(.*)</h2><p>(.*)</p><div> ((<p> <img src=\".*\"/></p>)*?)<a href=\"/profile/(.*)\" title=\".*\">(.*):</a><div>((.|\n|\r)*?)</div></div>";
	public final static String PATTERN_SINGLE_DIARY_INFO_NOLOG_COMENTS = "<p> <a href=\"/profile/([^\"]*)\">([^:]*):</a><span>((.|\r|\n)*?)</span></p>";
	public final static String PATTERN_ALL_DIARYS_NOLOG = "<li><a href=\"([^\"]*)\">([^<]*)</a><a href=\"/profile/([^\"]*)\">([^:]*): </a>(.*?)<span>((.|\n|\r)*?)</span></li>";
	public final static String PATTERN_MY_DIARYS_LOGIN = "<p> <a href=\"([^\"]*)\">([^<]*)</a><p> (.*?)<span>((.|\n|\r)*?)</span></p></p>";
	public final static String PATTERN_MY_DIARYBOOKS = "<option value=\"(.*)\">(.*)</option>";
}
