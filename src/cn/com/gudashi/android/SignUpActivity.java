package cn.com.gudashi.android;

import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.com.gudashi.android.R.id;

public class SignUpActivity extends Activity {
	private EditText textUsername;
	private EditText textNickname;
	private EditText textPassword;
	private EditText textPasswordConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		textUsername = (EditText)findViewById(id.text_username);
		textNickname = (EditText)findViewById(id.text_nickname);
		textPassword = (EditText)findViewById(id.text_password);
		textPasswordConfirm = (EditText)findViewById(id.text_password_confirm);
	}

	private static Pattern USER_NAME_PATTERN = Pattern.compile("[a-zA-Z]+\\w*");
	public void onSignup(View view){
		String username = textUsername.getText().toString();
		String nickname = textNickname.getText().toString();
		String password = textPassword.getText().toString();
		String passwordConfirm = textPasswordConfirm.getText().toString();

		boolean ok = true;
		if(!USER_NAME_PATTERN.matcher(username).matches()){
			textUsername.setError("用户名填写错误！");
			ok = false;
		}
		if(nickname.isEmpty()){
			textNickname.setError("填一下昵称吧！");
			ok = false;
		}
		if(!nickname.trim().equals(nickname)){
			textNickname.setError("昵称填写错误！");
			ok = false;
		}
		if(password.isEmpty()){
			textPassword.setError("填一下密码吧！");
			ok = false;
		}else if(!passwordConfirm.equals(password)){
			textPasswordConfirm.setError("密码确认不对哦!");
			ok = false;
		}

		System.out.println(ok);
	}
}
