package food;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.applw.JSONParser;
import com.example.applw.MainActivity;
import com.example.applw.R;
import com.example.applw.Url_and_Strings;

import food.food_list_activity.FoodSubTask.CustomFoodAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class food_list_activity extends Fragment {
	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url_all_products = Url_and_Strings.ip
			+ "myproject/list_all_hotels.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_FID = "fid";
	private static final String TAG_NAME = "Hotel_Name";
	private static final String TAG_NUM1 = "Hotel_Num1";
	private static final String TAG_NUM2 = "HotelNum2";
	private ProgressDialog pDialog;
	ListView list_of_foods;
	String hotel_names[];
	String hotel_num[];
	List<Hotel_Object> list_of_hotel_objects;
	// products JSONArray
	JSONArray products = null;
	ArrayList<HashMap<String, String>> productsList;
	CustomFoodAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.food_list_actvity, container,
				false);

		productsList = new ArrayList<HashMap<String, String>>();
		list_of_foods = (ListView) rootView.findViewById(R.id.food_list_view);
		hotel_names = new String[100];
		hotel_num = new String[100];
		list_of_hotel_objects = new ArrayList<Hotel_Object>();

		new FoodSubTask().execute();

		System.out.println(list_of_hotel_objects);
		// System.out.println(hotel_num);
		// adapter=new CustomFoodAdapter(Url_and_Strings.context);
		// adapter.setCustomButtonListner(MainActivity.this);
		/*
		 * list_of_foods.setOnItemClickListener(new OnItemClickListener() {
		 * public void onItemClick(AdapterView<?> parent, View view, int
		 * position, long id) { // When clicked, show a toast with the TextView
		 * text Toast.makeText(Url_and_Strings.context, ((TextView)
		 * view).getText(), Toast.LENGTH_SHORT).show(); if (view.getId() ==
		 * R.id.button_to_call) { String url = "tel:" + hotel_num[position];
		 * Intent intent = new Intent(Intent.ACTION_CALL, Uri .parse(url)); } }
		 * });
		 */
		list_of_foods.setAdapter(adapter);

		/*
		 * list_of_foods.setOnItemClickListener(new OnItemClickListener() {
		 * public void onItemClick(AdapterView<?> parent, View view, int
		 * position, long id) { // When clicked, show a toast with the TextView
		 * text Toast.makeText(getApplicationContext(), ((TextView)
		 * view).getText(), Toast.LENGTH_SHORT).show(); if (view.getId() ==
		 * R.id.button_of_call) { String url = "tel:" + hotel_num[position];
		 * Intent intent = new Intent(Intent.ACTION_CALL, Uri .parse(url)); } }
		 * });
		 */

		return rootView;

	}

	class FoodSubTask extends AsyncTask<String, String, String> {
		/*
		 * @Override protected void onPreExecute() { // TODO Auto-generated
		 * method stub
		 * 
		 * super.onPreExecute(); pDialog = new
		 * ProgressDialog(Url_and_Strings.context);
		 * pDialog.setMessage("Loading products. Please wait...");
		 * pDialog.setIndeterminate(false); pDialog.setCancelable(false);
		 * pDialog.show();
		 * 
		 * }
		 */

		@Override
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("year", "1990"));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "POST",
					params);
			// List< SingleFoodRow> result=new
			// ArrayList<MainActivity.SingleFoodRow>();
			// Check your log cat for JSON reponse
			Hotel_Object temp_hotel_obj;
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {

						JSONObject c = products.getJSONObject(i);
						// result.add(new SingleFoodRow(c.getString(TAG_NAME)));
						hotel_names[i] = c.getString(TAG_NAME);
						// hotel_num[i] = c.getString(TAG_NUM1);
						// Storing each json item in variable
						String id = c.getString(TAG_FID);
						hotel_num[i] = c.getString(TAG_NUM1);
						String name = c.getString(TAG_NAME);
						String num1 = c.getString(TAG_NUM1);
						String num2 = c.getString(TAG_NUM2);
						// hotel_num[i]=c.getString(TAG_NUM1);
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						temp_hotel_obj = new Hotel_Object(id, name, num1, num2);
						list_of_hotel_objects.add(temp_hotel_obj);
						map.put(TAG_NAME, name);

						// adding HashList to ArrayList
						productsList.add(map);
						Log.d("infor loop ", "in frosndk");
						System.out.println(hotel_num);

					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String result) {
			Log.d("In onpostexecute ", "indmjf"); // dismiss the dialog after
			/*
			 * getting all products pDialog.dismiss(); // updating UI from
			 * Background Thread
			 */

			Log.d("In run ", "ujhsdjd");
			/**
			 * Updating parsed JSON data into ListView
			 * */
			/*
			 * ListAdapter adapter = new SimpleAdapter(MainActivity.this,
			 * productsList, R.layout.custom_food, new String[] { "", TAG_NAME,
			 * "" }, new int[] { (Integer) null, R.id.text_Of_hotel_name,
			 * (Integer) null }); // updating listview for(int
			 * i=0;i<hotel_names.length;i++) {
			 * System.out.println(hotel_names[i]); }
			 * //list_of_foods.setAdapter(new
			 * CustomFoodAdapter(getApplicationContext()));
			 * 
			 * }
			 */

			// list_of_foods.setAdapter(new
			// CustomFoodAdapter(getApplicationContext()));
			adapter = new CustomFoodAdapter(Url_and_Strings.context);
			list_of_foods.setAdapter(adapter);
		}

		/*
		 * class SingleFoodRow { String hotel_image; String hotel_name; Button
		 * button_to_call;
		 * 
		 * SingleFoodRow(String hotel) { hotel_name = hotel;
		 * 
		 * } }
		 */

		/*
		 * class CustomFoodAdapter extends BaseAdapter {
		 * //ArrayList<SingleFoodRow> foodArray; Context context;
		 * 
		 * CustomFoodAdapter(Context c) { context = c; //foodArray = new
		 * ArrayList<SingleFoodRow>(); for (int i = 0; i < foodArray.size();
		 * i++) { foodArray.add(new SingleFoodRow(hotel_names[i]));
		 * //foodArray=(ArrayList<SingleFoodRow>) result; }
		 * //System.out.println(foodArray);
		 * 
		 * }
		 * 
		 * @Override public int getCount() { // TODO Auto-generated method stub
		 * return list_of_hotel_objects.size(); }
		 * 
		 * @Override public Object getItem(int arg0) { // TODO Auto-generated
		 * method stub return list_of_hotel_objects.get(arg0); }
		 * 
		 * @Override public long getItemId(int arg0) { // TODO Auto-generated
		 * method stub return arg0; }
		 * 
		 * @Override public View getView(int position, View view, ViewGroup
		 * parent) { // TODO Auto-generated method stub LayoutInflater inflator
		 * = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 * View each_food_row = inflator.inflate(R.layout.custom_food, parent,
		 * false); TextView text_hotel_name = (TextView) each_food_row
		 * .findViewById(R.id.text_Of_hotel_name); //SingleFoodRow
		 * single_row=foodArray.get(position);
		 * //text_hotel_name.setText(single_row.hotel_name);
		 * text_hotel_name.setText(
		 * (list_of_hotel_objects.get(position).getHotel_name()); return
		 * each_food_row; }
		 * 
		 * }
		 */
		// @Override
		/*
		 * public void onButtonClickListner(int position) {
		 * 
		 * String url = "tel:" + hotel_num[position]; Intent intent = new
		 * Intent(Intent.ACTION_CALL, Uri .parse(url));
		 * 
		 * }
		 */
		class CustomFoodAdapter extends BaseAdapter {
			Context context;

			// CustomButtonListener customListner;
			public CustomFoodAdapter(Context c) {
				context = c;

			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list_of_hotel_objects.size();
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return list_of_hotel_objects.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public View getView(final int position, View view, ViewGroup parent) {
				LayoutInflater inflator = (LayoutInflater) Url_and_Strings.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View each_food_row = inflator.inflate(R.layout.custom_food,
						null);
				TextView text_hotel_name = (TextView) each_food_row
						.findViewById(R.id.text_of_name);
				TextView text_phone = (TextView) each_food_row
						.findViewById(R.id.text_of_phone_num);
				Button button_to_call = (Button) each_food_row
						.findViewById(R.id.button_to_call);

				// SingleFoodRow single_row=foodArray.get(position);
				// text_hotel_name.setText(single_row.hotel_name);
				// String
				// strings=list_of_hotel_objects.get(position).getHotel_name();
				String hot = hotel_names[position];
				text_hotel_name.setText(hot);
				String num = hotel_num[position];
				text_phone.setText(num);
				button_to_call.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"
								+ hotel_num[position]));
						startActivity(callIntent);

					}
				});
				/*
				 * button_to_call.setOnClickListener(new OnClickListener() {
				 * 
				 * @Override public void onClick(View v) { if (customListner !=
				 * null) { customListner.onButtonClickListner(position); }
				 * 
				 * 
				 * 
				 * } });
				 */
				return each_food_row;

			}

		}

		/*
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) {
		 * 
		 * if (view.getId() == R.id.button_to_call) {
		 * 
		 * Toast.makeText(getApplicationContext(), "on button"+position,
		 * Toast.LENGTH_LONG); Intent callIntent = new
		 * Intent(Intent.ACTION_CALL);
		 * callIntent.setData(Uri.parse("tel:"+hotel_num[position]));
		 * startActivity(callIntent); } }
		 */
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(2);
	}

}
