package cn.com.gudashi.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.android.user.UserService;

public class LoginActivity extends Activity {
	private EditText textUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		textUserName = (EditText)findViewById(id.text_username);
	}

	public void onLogin(View view){
		String username = textUserName.getText().toString().trim();

		if(username.length() == 0){
			textUserName.setError("用户名总得填填吧？");
			return;
		}

		UserService.setLoggedInUser(this, username);

		setResult(RESULT_OK);
		finish();
	}
}
