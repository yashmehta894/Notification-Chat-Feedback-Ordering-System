package Culture;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.applw.MainActivity;
import com.example.applw.R;
import com.example.applw.Url_and_Strings;

public class Culturelistactivity extends Fragment {
	// private ProgressDialog pDialog;
	private List<Images_of_Culture> List_of_Culture = new ArrayList<Images_of_Culture>();
	private CultureAdapter sta;
	ListView MylistView;
	private LoadAllProducts lap;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.culturefragment, container,
				false);

		lap=new LoadAllProducts();
		lap.execute();

		Log.e("ImageLoadTask", "Culture fragment Loaded ");

		
		 MylistView = (ListView) (rootView
				.findViewById(R.id.Culturefragment_List_view));
		
		return rootView;
	}


	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(1);
	}

	@Override
	public void onDestroy() {
		lap.cancel(true);
		for (Object s : List_of_Culture) {
			// START LOADING IMAGES FOR EACH STUDENT
			Images_of_Culture k = (Images_of_Culture) s;
			k.stoploader();
		}
		super.onDestroy();
		Log.e("Culture", "Culture is destroyed");
	};
	
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		/*
		 * protected void onPreExecute() { super.onPreExecute(); pDialog = new
		 * ProgressDialog(MainActivity.this);
		 * pDialog.setMessage("Loading products. Please wait...");
		 * pDialog.setIndeterminate(false); pDialog.setCancelable(false);
		 * pDialog.show(); }
		 */
		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters

			InputStream is = null;
			String result = null;
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			String user, pass;
			user = "hey";
			pass = "hello";
			Log.e("log_tag", user + pass);

			nameValuePairs.add(new BasicNameValuePair("Username", user));
			nameValuePairs.add(new BasicNameValuePair("Password", pass));

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						Url_and_Strings.ip+"myproject/Culture_connect.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

				Log.e("log_tag", "connection success ");

			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
				return "no connection";
			}
			// convert response to string
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");

				}
				is.close();

				result = sb.toString();
				Log.e("result is", result);
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());

			}

			try {

				JSONObject object = new JSONObject(result);
				String ch = object.getString("success");
				if (ch.equals("1")) {

					Log.e("log_tag", "connection success ");
					JSONArray productObj = object.getJSONArray("products"); // JSON
																			// Array

					// get first product object from JSON Array
					for (int i = 0; i < productObj.length(); i++) {
						JSONObject c = productObj.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString("idculture_table");
						String Imageurl = c.getString("url_of_image");
						String weburl = c.getString("url_of_website");
						String Imagetext = c.getString("text_of_image");

						Images_of_Culture image = new Images_of_Culture(
								Imageurl, Imagetext);

						List_of_Culture.add(image);
					}
					return "true";

				}

				else {

					// Toast.makeText(getApplicationContext(),
					// "Record is not available.. Enter valid number",
					// Toast.LENGTH_SHORT).show();
					return "false";

				}

			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
				// Toast.makeText(getApplicationContext(), "JsonArray fail",
				// Toast.LENGTH_SHORT).show();
			} catch (Exception e) {

			}
			return "false";
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			/*
			 * pDialog.dismiss(); // updating UI from Background Thread
			 * runOnUiThread(new Runnable() { public void run() {
			 *//**
			 * Updating parsed JSON data into ListView
			 * */
			
			 /* ListAdapter adapter = new SimpleAdapter(
			 AllProductsActivity.this, productsList, R.layout.list_item, new
			  String[] { TAG_PID, TAG_NAME}, new int[] { R.id.pid, R.id.name
			  }); // updating listview setListAdapter(adapter); } });
*/			 
			sta = new CultureAdapter(Url_and_Strings.context, List_of_Culture);
			MylistView.setAdapter(sta);
			for (Object s : List_of_Culture) {
				// START LOADING IMAGES FOR EACH STUDENT
				Images_of_Culture k = (Images_of_Culture) s;
				k.loadImage(sta);
				
				Log.e("Culture", "main assync task finished");
			}

		}

	}
}
