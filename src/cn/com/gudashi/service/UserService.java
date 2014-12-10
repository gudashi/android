package cn.com.gudashi.service;

import java.io.EOFException;

import cn.com.gudashi.Config;
import cn.com.gudashi.domain.User;
import cn.com.gudashi.utils.HttpUtils;

public class UserService {
	public static User register(User user) throws Exception {
		user = HttpUtils.postJsonForJson(Config.SERVICE_BASE + "/user/register.json", user, User.class);
		return user;
	}

	public static User login(String username, String password) throws Exception {
		try{
			User user = HttpUtils.getForJson(Config.SERVICE_BASE + "/user/" + username + ".json", User.class);
			// TODO
//			if(new Random().nextInt(3) != 1){
//			if(!StringUtils.equals(password, user.getPassword())){
//				throw new RuntimeException("密码错误！");
//			}
			return user;
		}catch(EOFException ex){
			throw new RuntimeException("用户不存在！", ex);
		}
	}
}
