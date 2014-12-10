package cn.com.gudashi.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.android.user.UserService;
import cn.com.gudashi.domain.Stock;
import cn.com.gudashi.service.StockService;

public class MyStocksActivity extends Activity {
	private static final int SELECT_REQ_CODE = 1;

	private ListView listMyStocks;
	private ArrayAdapter<Stock> stockAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_stocks);

		listMyStocks = (ListView)findViewById(id.list_my_stocks);

		stockAdapter = new ArrayAdapter<Stock>(this, android.R.layout.simple_list_item_1);
		listMyStocks.setAdapter(stockAdapter);

		stockAdapter.addAll(
				StockService.getMyStocks(
						this, 
						UserService.getLoggedInUser(this).getId()));
	}

	@Override
	protected void onPause() {
		super.onPause();

		List<Stock> list = new ArrayList<Stock>(stockAdapter.getCount());
		for(int i = 0; i < stockAdapter.getCount(); ++i){
			list.add(stockAdapter.getItem(i));
		}
		StockService.storeMyStocks(
				this,
				UserService.getLoggedInUser(this).getId(), 
				list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_stocks, menu);
		return true;
	}

	public void addStock(MenuItem menuItem){
		startActivityForResult(new Intent(this, SearchAndSelectStockActivity.class), SELECT_REQ_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == SELECT_REQ_CODE){
				Stock stock = (Stock) data.getSerializableExtra("stock");
				stockAdapter.add(stock);
			}
		}
	}
}
