package calendar_new;

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






import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Calendar_new_list extends Fragment {

	private List<Date_Class> List_of_events= new ArrayList<Date_Class>();
	ListView MylistView;
	private LoadAllProducts lap;
	calendar_new_adapter cap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.calendar_list_activity,
				container, false);
		
		MylistView = (ListView)rootView.findViewById(R.id.Calendarfragment_List_view);
		lap = new LoadAllProducts();
		lap.execute();
		

		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(3);
	}

	class LoadAllProducts extends AsyncTask<String, String, String> {

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
						+ "myproject/calendar_access.php");
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
				return "false";

			}

			try {

				JSONObject object = new JSONObject(result);
				String ch = object.getString("success");
				if (ch.equals("1")) {

					Log.e("log_tag", "connection success ");

					JSONArray productObj = object.getJSONArray("products");

					// get first product object from JSON Array
					for (int i = 0; i < productObj.length(); i++) {

						JSONObject c = productObj.getJSONObject(i);
						Log.e("log_tag", "inside..... ");
						// Storing each json item in variable
						String id = c.getString("id");
						String date = c.getString("date");
						String event = c.getString("event_name");
						String desc = c.getString("description");
						String othfd = c.getString("other_field");

						List<String> lid = new ArrayList<String>();

						List<String> levent = new ArrayList<String>();
						List<String> ldesc = new ArrayList<String>();
						List<String> lothfd = new ArrayList<String>();

						lid.add(id);
						levent.add(event);
						ldesc.add(desc);
						lothfd.add(othfd);

						int j = i + 1;
						while (j < productObj.length()
								&& (productObj.getJSONObject(j)).getString(
										"date").equals(date)) {
							lid.add((productObj.getJSONObject(j))
									.getString("id"));
							levent.add((productObj.getJSONObject(j))
									.getString("event_name"));
							ldesc.add((productObj.getJSONObject(j))
									.getString("description"));
							lothfd.add((productObj.getJSONObject(j))
									.getString("other_field"));

							j++;
						}
						i = j - 1;
						Date_Class d = new Date_Class(date, levent, ldesc,
								lothfd);
						List_of_events.add(d);
						Log.e("calendar", "" + List_of_events.size());

					}
					Log.e("calendar", "return true");
					return "true";

				}

				else {

					return "false";

				}

			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
				// Toast.makeText(getApplicationContext(), "JsonArray fail",
				// Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("log_tag", "other exception " + e.toString());
			}
			return "false";
		}

		protected void onPostExecute(String file_url) {
			
			if (file_url.equals("true")) {
				Log.e("calendar", "Inside post");
				cap = new calendar_new_adapter(Url_and_Strings.context,List_of_events);
				MylistView.setAdapter(cap);

				Log.e("Culture", "main assync task finished");
			}
			else{
				Toast.makeText(Url_and_Strings.context, "Connection Problem..", Toast.LENGTH_LONG).show();
			}
		}

	}

}
