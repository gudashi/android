package cn.com.gudashi.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.domain.User;
import cn.com.gudashi.service.UserService;

public class LoginActivity extends Activity {
	private static final int REQ_CODE_SIGN_UP = 0;

	private EditText textUserName;
	private EditText textPassword;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		textUserName = (EditText)findViewById(id.text_username);
		textPassword = (EditText)findViewById(id.text_password);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void onLogin(View view){
		String username = textUserName.getText().toString().trim();
		String password = textPassword.getText().toString();

		if(username.length() == 0){
			textUserName.setError("用户名总得填填吧？");
			return;
		}else if(password.length() == 0){
			textPassword.setError("填一下密码吧！");
		}else{
			cancelLastTask();
			progress = ProgressDialog.show(this, null, "Loading...");
			lastTask = new AsyncTask<String, Integer, User>(){
				private String error;

				@Override
				protected User doInBackground(String... params) {
					try{
						return UserService.login(params[0], params[1]);
					}catch(Exception ex){
						ex.printStackTrace();
						error = ex.toString();
						return null;
					}
				}

				@Override
				protected void onCancelled(User result) {
					progress.dismiss();
				}

				@Override
				protected void onCancelled() {
					progress.dismiss();
				}

				@Override
				protected void onPostExecute(User user) {
					progress.dismiss();
					if(isCancelled()){
						return;
					}

					if(user != null){
						onSignedIn(user);
					}else if(error != null){
						Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
					}
				}
			}.execute(username, password);
		}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == REQ_CODE_SIGN_UP){
				if(data.getSerializableExtra("user") instanceof User){
					User user = (User)data.getSerializableExtra("user");
					onSignedIn(user);
				}
			}
		}
	}

	private void onSignedIn(User user) {
		Toast.makeText(this, "Welcome, " + user.getName(), Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK, new Intent().putExtra("user", user));
		finish();
	}

	private AsyncTask<?, ?, ?> lastTask;
	private void cancelLastTask() {
		if(lastTask != null){
			lastTask.cancel(true);
		}
	}

	@Override
	protected void onDestroy() {
		cancelLastTask();
		super.onDestroy();
	}
}
