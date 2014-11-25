package cn.com.gudashi.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.android.user.UserService;


public class MainActivity extends Activity {
	private static final int LOGIN_REQ_CODE = 1;

    private Menu menu;
    private TextView textGreeting;
    private String username;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGreeting = (TextView)findViewById(id.text_greeting);

        updateLoggedInUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;

    	updateMenu();

        return true;
    }

    private void updateLoggedInUser(){
    	String greeting = "Hello, ÓÎ¿Í£¡";

    	username = UserService.getLoggedInUser(this);
    	if(username != null){
    		greeting = "Hello, " + username;
    	}

    	System.out.println(username != null ? username : "Î´µÇÂ¼");

    	textGreeting.setText(greeting);

    	updateMenu();
    }

    private void updateMenu(){
    	if(menu != null){
    		menu.clear();
    		if(username != null){
    			getMenuInflater().inflate(R.menu.user, menu);
    		}else{
    			getMenuInflater().inflate(R.menu.anonymous, menu);
    		}
    	}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.login_button) {
        	startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQ_CODE);
            return true;
        }else if(id == R.id.logout_button){
        	UserService.setLoggedInUser(this, null);
        	updateLoggedInUser();
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == LOGIN_REQ_CODE){
				updateLoggedInUser();
			}
		}
	}
}
