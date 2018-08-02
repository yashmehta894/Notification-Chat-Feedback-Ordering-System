package Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.applw.*;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RulesFragment extends Fragment {
	  int id;
	
	int i = 0;
	// Progress Dialog
	private ProgressDialog pDialog;
	int count = 0;
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	private List<Rules_Class> arr_list_rules;
	private List<Rules_perm> arr_list_rules_perm;

	String arr_sub_tit[];
	// url to get all products list
	private static String url_all_sub_rules = Url_and_Strings.ip+"SparkNextCodes/list_of_rules_1.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_SUB_TITLES = "Sub_Titles";
	private static final String TAG_SUB_TITLES_DESCRITION = "Sub_Titles_Description";
	String arr[];

	// products JSONArray
	JSONArray products = null;

	HashMap<String, ArrayList<String>> multiMap;
	ListView list_of_rules;

	public RulesFragment(int data) {
		i=data;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		
		Log.e("in rule fragment","in oncreate");
		System.out.println(id);
		View rootView = inflater.inflate(R.layout.main_sub_rules, container,
				false);
		
		multiMap = new HashMap<String, ArrayList<String>>();
		arr = new String[100];
		arr_list_rules = new ArrayList<Rules_Class>();
		arr_list_rules_perm = new ArrayList<Rules_perm>();
		list_of_rules = (ListView) rootView.findViewById(R.id.list_of_rules);
		new LoadRules().execute();
           
		
		
		
		System.out.println(arr_list_rules.toString());
		System.out.println(arr_list_rules_perm.toString());

		 return rootView;

            
		
		
		
		
	}
	public void setArr() {
		int i = 0;
		Rules_perm pere;
		Log.e("log 1", "inside set arr");
		
		ArrayList<String> temp = null;
		while (i <arr_list_rules.size()) {

			temp = new ArrayList<String>();
			temp.add(arr_list_rules.get(i).rule_sub_description);
			int j=i+1;
			Log.e("log2","inside first while");
			while (j<arr_list_rules.size()&&arr_list_rules.get(i).getRule_subtitle()
					.equals(arr_list_rules.get(j).getRule_subtitle())
					) {
				
				temp.add(arr_list_rules.get(j).getRule_sub_description());
				j++;
                    Log.e("log3","inside  second while");
			}
			
			i=j-1;
			
			pere = new Rules_perm(arr_list_rules.get(i).getRule_subtitle(),
					temp);
			i++;
			arr_list_rules_perm.add(pere);
		}
		

	}

	
	class LoadRules extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			/*super.onPreExecute();
			pDialog = new ProgressDialog(RulesFragment.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();*/
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			System.out.println(id );
			params.add(new BasicNameValuePair("cat_id",i+""));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_sub_rules,
					"POST", params);

			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);
					for (int i = 0; i < products.length(); i++) {

						JSONObject c = products.getJSONObject(i);
						String s_title = c.getString(TAG_SUB_TITLES);
						String s_title_descripton = c
								.getString(TAG_SUB_TITLES_DESCRITION);
						Rules_Class rule_obj = new Rules_Class(s_title,
								s_title_descripton);
						arr_list_rules.add(rule_obj);

					}
					System.out.println(arr_list_rules.toString());
					setArr();
					System.out.println(arr_list_rules_perm.toString());
				}
			}

			catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			setArr();
			list_of_rules.setAdapter(new customRulesAdapter());

		}
	}
	
	class customRulesAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arr_list_rules_perm.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arr_list_rules_perm.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
				LayoutInflater vi = (LayoutInflater)Url_and_Strings.context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = vi.inflate(R.layout.custom_rules, null);

			Log.e("in","in get view");
			System.out.println(position+" ");
			
			TextView tit = (TextView) convertView
					.findViewById(R.id.sub_title_heading);
			LinearLayout ll=(LinearLayout)convertView.findViewById(R.id.sub_title_description);
			Rules_perm tere=arr_list_rules_perm.get(position);
			tit.setText(tere.getTitle());
			ArrayList<String>arr_ru=tere.getRules_in_title();
			int i=0;
			while(i<arr_ru.size())
			{
				TextView tnew=new TextView (Url_and_Strings.context);
				tnew.setText((i+1)+" "+arr_ru.get(i));
				tnew.setPadding(0, 10, 0, 10);
				
				ll.addView(tnew);
				i++;
			}
			
			/*TextView titu = (TextView) convertView
					.findViewBy=(R.id.sub_title_content);
			tit.setText(arr[position]);
			ArrayList<String> list_of_details = multiMap.get(arr[position]);
			StringBuffer buff = null;
			int io = 0;
			while (io <= list_of_details.size())
				buff.append(io + ".  " + list_of_details.get(io) + " /n");
			titu.setText(buff.toString());*/
			return convertView;
		}
	}



}
