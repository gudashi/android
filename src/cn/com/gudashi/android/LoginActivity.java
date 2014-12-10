package cn.com.gudashi.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.android.user.UserService;

public class LoginActivity extends Activity {
	private static final int REQ_CODE_SIGN_UP = 0;

	private EditText textUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		textUserName = (EditText)findViewById(id.text_username);

		getActionBar().setDisplayHomeAsUpEnabled(true);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onSignup(MenuItem menuItem){
		startActivityForResult(new Intent(this, SignUpActivity.class), REQ_CODE_SIGN_UP);
	}
}
