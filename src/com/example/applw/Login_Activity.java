package com.example.applw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends Activity {

	EditText email;
	EditText password;
	private ProgressDialog pDialog;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login_);
		

		email = (EditText) findViewById(R.id.logineditText1);
		password = (EditText) findViewById(R.id.logineditText2);
		// email.getBackground().setColorFilter(Color.WHITE,
		// PorterDuff.Mode.SRC_IN);

	
		preferences = getSharedPreferences("LoginData", MODE_PRIVATE);
//		getActionBar().hide();
		email.setText(preferences.getString("user_name", ""));
		password.setText(preferences.getString("password", ""));
		Url_and_Strings.Login_mode = Url_and_Strings.Guest;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onlogin(View v) {

		(new CreateNewProduct()).execute();

	}

	public void onguestlogin(View v) {
		Url_and_Strings.Login_mode = Url_and_Strings.Guest;

		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("user_name", "");
		editor.putString("password", "");
		editor.commit();
		email.setText(preferences.getString("user_name", ""));
		password.setText(preferences.getString("password", ""));
		Intent in = new Intent(this, MainActivity.class);
		startActivity(in);
	}

	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@SuppressWarnings("deprecation")
		@Override
		/**
		 * Creating product
		 * */
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login_Activity.this);
			pDialog.setMessage("Loading....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			InputStream is = null;
			String result = null;
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			String user, pass;
			user = email.getText().toString();
			pass = password.getText().toString();
			Log.e("log_tag", user + pass);

			nameValuePairs.add(new BasicNameValuePair("Username", user));
			nameValuePairs.add(new BasicNameValuePair("Password", pass));

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						Url_and_Strings.ip+"myproject/login.php");
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
					// long q=object.getLong("f1");
					return "true";

				}

				else {

					// Toast.makeText(getApplicationContext(),
					// "Record is not available.. Enter valid number",
					// Toast.LENGTH_SHORT).show();

				}

			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
				// Toast.makeText(getApplicationContext(), "JsonArray fail",
				// Toast.LENGTH_SHORT).show();
			} catch (Exception e) {

			}

			return "false";
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			 pDialog.dismiss();
			if (file_url == "true") {

				String user, pass;
				user = email.getText().toString();
				pass = password.getText().toString();
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("user_name", user);
				editor.putString("password", pass);
				editor.commit();

				Url_and_Strings.Login_mode = Url_and_Strings.User;

				Intent in = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(in);
			} else if (file_url == "no connection") {
				Toast.makeText(getApplicationContext(),
						"Connection Problem",
						Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(getApplicationContext(),
						"Please Enter Correct Username and Password",
						Toast.LENGTH_LONG).show();

			}
		}
	}
}
