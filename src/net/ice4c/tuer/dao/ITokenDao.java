package net.ice4c.tuer.dao;

public interface ITokenDao {
	
	/**
	 * 以用户编号获得一个Token
	 * @param userid
	 * @return
	 */
	public String getToken(Long userid);
	/**
	 * 以用户编号获得一个Token
	 * @param userid
	 * @return
	 */
	public String saveToken(Long userid,String token);

}
