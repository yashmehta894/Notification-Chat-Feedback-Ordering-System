package Rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.applw.*;



import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RulesPreFragment extends Fragment{
	
	
	// url to get all products list
		private static String url_list_rules = Url_and_Strings.ip+"SparkNextCodes/list_rules.php";
		String Titles[];
		
		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_PRODUCTS = "products";
		private static final String TAG_PID = "ruid";
		private static final String TAG_NAME = "Titles";

		// products JSONArray
		JSONArray products = null;
		private ProgressDialog pDialog;
		// Creating JSON Parser object
		JSONParser jParser = new JSONParser();
		LinearLayout ll;
		CustomRulesAdapter adapter;
		ListView list_of_rules;
		Communicator comm;

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.rules_fragment, container,
				false);
		//Titles=new String[100];
			new RulesSubTask().execute();
			
			
			comm=(Communicator)getActivity();
		
			adapter = new CustomRulesAdapter(Url_and_Strings.context);
			list_of_rules=(ListView) rootView.findViewById(R.id.list_of_titles);
			
			 list_of_rules.setOnItemClickListener(new OnItemClickListener() {
				 
		           

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						
						comm.respond(arg2+1);
						
		            }
						
			 });
			 
		return rootView;
		
		
	}
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(3);
	}
      
	
	class RulesSubTask extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			/*super.onPreExecute();
			pDialog = new ProgressDialog(MainRules.this);
			pDialog.setMessage("Loading Rules..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();*/
		}

		@Override
		protected String doInBackground(String... params) {
			// Building Parameters
			List<NameValuePair> bun = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_list_rules, "POST",
					bun);

			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);
					Titles = new String[products.length()];

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);
						Titles[i] = c.getString(TAG_NAME);
						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_NAME);
						Titles[i] = name;
						// creating new HashMap
						// HashMap<String, String> map = new HashMap<String,
						// String>();

						// adding each child node to HashMap key => value
						// map.put(TAG_PID, id);
						// map.put(TAG_NAME, name);

						// adding HashList to ArrayList
						// productsList.add(map);

					}
					/*
					 * for(int i=0;i<Titles.length;i++) {
					 * System.out.println(Titles[i]); }
					 */

				} else {
					// no products found
					// Launch Add New product Activity
					Toast.makeText(Url_and_Strings.context, "No success",
							Toast.LENGTH_LONG).show();

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			list_of_rules.setAdapter(adapter);
		}

	}
	
	
	
	class CustomRulesAdapter extends BaseAdapter {
		Context context;

		public CustomRulesAdapter(Context c) {
			context = c;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Titles.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return Titles[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			LayoutInflater inflator = (LayoutInflater) Url_and_Strings.context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View each_rule_row = inflator.inflate(R.layout.custom_main_rules, null);
			TextView text_hotel_name = (TextView) each_rule_row
					.findViewById(R.id.textView_of_rules_titles);
             for(int i=0;i<Titles.length;i++)
             {
            	 System.out.println(Titles[i]);
             }
			// SingleFoodRow single_row=foodArray.get(position);
			// text_hotel_name.setText(single_row.hotel_name);
			// String
			// strings=list_of_hotel_objects.get(position).getHotel_name();
			String hot = Titles[position];
			System.out.println(hot);
			text_hotel_name.setText(hot);

			return each_rule_row;

		}

	}
	
	
	

}
