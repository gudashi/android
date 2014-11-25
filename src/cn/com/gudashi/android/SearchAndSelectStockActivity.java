package cn.com.gudashi.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cn.com.gudashi.android.R.id;
import cn.com.gudashi.domain.Stock;
import cn.com.gudashi.service.StockService;

public class SearchAndSelectStockActivity extends Activity implements OnItemClickListener {
	private EditText textKeyword;
	private ListView listStockCandidates;

	private ArrayAdapter<Stock> stockCandidates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_and_select_stock);

		textKeyword = (EditText)findViewById(id.text_keyword);
		listStockCandidates = (ListView)findViewById(id.list_stock_candidates);

		stockCandidates = new ArrayAdapter<Stock>(this, android.R.layout.simple_list_item_1);
		listStockCandidates.setAdapter(stockCandidates);
		listStockCandidates.setOnItemClickListener(this);
	}

	public void onSearch(View view){
		String keyword = textKeyword.getText().toString().trim();
		if(keyword.length() == 0){
			textKeyword.setError("ÇëÌîÐ´¹Ø¼ü×Ö£¡");
			return;
		}

		cancelLastTask();
		lastTask = new AsyncTask<String, Integer, List<Stock>>(){
			private String error;

			@Override
			protected List<Stock> doInBackground(String... params) {
				try{
					return StockService.search(params[0]);
				}catch(Throwable ex){
					if(ex.getClass() == RuntimeException.class && ex.getMessage() != null && ex.getMessage().trim().length() > 0){
						error = ex.getMessage();
					}else{
						error = ex.toString();
					}
					return null;
				}
			}

			@Override
			protected void onPostExecute(List<Stock> result) {
				if(error != null){
					Toast.makeText(listStockCandidates.getContext(), error, Toast.LENGTH_SHORT).show();
					return;
				}

				stockCandidates.clear();
				stockCandidates.addAll(result);
			}
		}.execute(keyword);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Stock stock = stockCandidates.getItem(position);
		Intent intent = new Intent();
		intent.putExtra("stock", stock);
		setResult(RESULT_OK, intent);
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
