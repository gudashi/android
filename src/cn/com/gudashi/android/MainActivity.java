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

    	textGreeting.setText(greeting);

    	updateMenu();
    }

    private void updateMenu(){
    	if(menu != null){
    		menu.clear();
    		if(username != null){
    			getMenuInflater().inflate(R.menu.main_user, menu);
    		}else{
    			getMenuInflater().inflate(R.menu.main_anonymous, menu);
    		}
    	}
    }

    public void onLogin(MenuItem menuItem){
    	startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQ_CODE);
    }

    public void onLogout(MenuItem menuItem){
    	UserService.setLoggedInUser(this, null);
    	updateLoggedInUser();
    }

    public void onShowMyStocks(MenuItem menuItem){
    	startActivity(new Intent(this, MyStocksActivity.class));
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
