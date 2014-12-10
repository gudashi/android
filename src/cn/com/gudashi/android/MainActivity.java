package cn.com.gudashi.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.android.user.UserService;
import cn.com.gudashi.domain.User;


public class MainActivity extends Activity {
	private static final int LOGIN_REQ_CODE = 1;

    private Menu menu;
    private TextView textGreeting;
    User user = null;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGreeting = (TextView)findViewById(id.text_greeting);

        User user = UserService.getLoggedInUser(this);
        if(user != null){
        	updateLoggedInUser(user);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;

    	updateMenu();

        return true;
    }

    private void updateLoggedInUser(User user){
    	this.user = user;

    	if(user != null){
    		textGreeting.setText("你好, " + user.getName() + "！");
    	}else{
    		textGreeting.setText("你好, 游客！");
    	}

    	updateMenu();
    }

    private void updateMenu(){
    	if(menu != null){
    		menu.clear();
    		if(user != null){
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
    	updateLoggedInUser(null);
    }

    public void onShowMyStocks(MenuItem menuItem){
    	startActivity(new Intent(this, MyStocksActivity.class));
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == LOGIN_REQ_CODE){
				if(data.getSerializableExtra("user") instanceof User){
					User user = (User)data.getSerializableExtra("user");
					UserService.setLoggedInUser(this, user);
					updateLoggedInUser(user);
				}
			}
		}
	}
}
