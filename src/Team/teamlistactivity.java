package Team;

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

import com.example.applw.MainActivity;
import com.example.applw.R;
import com.example.applw.Url_and_Strings;

import Culture.CultureAdapter;
import Culture.Images_of_Culture;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class teamlistactivity extends Fragment {
	private List<team_class> list_of_meamber = new ArrayList<team_class>();
	private Team_adapter sta;
	ListView MylistView;
	private LoadAllProducts lap;
	//private ProgressDialog pDialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.team_list_activity,
				container, false);
		MylistView = (ListView) (rootView.findViewById(R.id.team_list_view));
		lap = new LoadAllProducts();
		lap.execute();

		Log.e("ImageLoadTask", "Culture fragment Loaded ");

		

		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(6);
	}
	


	@Override
	public void onDestroy() {
		lap.cancel(true);
		for (Object s : list_of_meamber) {
			// START LOADING IMAGES FOR EACH STUDENT
			team_class k = (team_class) s;
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
				HttpPost httppost = new HttpPost(Url_and_Strings.ip
						+ "myproject/Team_connect.php");
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
						String id = c.getString("id");
						String Imageurl = c.getString("url");
						String desig = c.getString("designation");
						String name = c.getString("name");
						String phone = c.getString("mobile");
						String email = c.getString("email");

						team_class mem = new team_class(id, name, desig, email,
								phone, Imageurl);
						list_of_meamber.add(mem);
						Log.e("log_tag", name + email);
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

			/*
			 * ListAdapter adapter = new SimpleAdapter(
			 * AllProductsActivity.this, productsList, R.layout.list_item, new
			 * String[] { TAG_PID, TAG_NAME}, new int[] { R.id.pid, R.id.name
			 * }); // updating listview setListAdapter(adapter); } });
			 */
			
			if(file_url=="true")
			{
			sta = new Team_adapter(Url_and_Strings.context, list_of_meamber);
			MylistView.setAdapter(sta);
			for (Object s : list_of_meamber) {
				// START LOADING IMAGES FOR EACH STUDENT
				team_class k = (team_class) s;
				k.loadImage(sta);

				Log.e("Team", "main assync task finished");
			}
			}
			else
			{
				Toast.makeText(Url_and_Strings.context, "Connection Problem..", Toast.LENGTH_LONG).show();
			}
		}

	}
}
