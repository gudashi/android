package cn.com.gudashi.android;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.domain.User;
import cn.com.gudashi.service.UserService;

public class SignUpActivity extends Activity {
	private EditText textUsername;
	private EditText textNickname;
	private EditText textPassword;
	private EditText textPasswordConfirm;

	private ProgressDialog progress;

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
		if(!ok){
			return;
		}

		cancelLastTask();
		progress = ProgressDialog.show(this, null, "Loading...");
		lastTask = new AsyncTask<User, Integer, User>(){
			private String error;

			@Override
			protected User doInBackground(User... params) {
				try {
					return UserService.register(params[0]);
				} catch (Exception ex) {
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
			protected void onPostExecute(User result) {
				progress.dismiss();
				if(isCancelled()){
					return;
				}

				if(result != null){
					onRegisterSuccess(result);
				}else if(error != null){
					Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
				}
			}
		}.execute(new User(username, nickname, password));
	}

	private void onRegisterSuccess(User user) {
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
