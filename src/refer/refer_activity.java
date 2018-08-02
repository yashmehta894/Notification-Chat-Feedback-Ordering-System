package refer;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;










import com.example.applw.JSONParser;
import com.example.applw.MainActivity;
import com.example.applw.R;
import com.example.applw.Url_and_Strings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class refer_activity extends Fragment implements View.OnClickListener {
	EditText referTo;
	EditText purpose;
	EditText yourPhone;
	
	EditText yourEmail;
	EditText theirPhone;
	EditText theirEmail;
	Button refer;
	ProgressDialog pDialog;
	JSONParser jParser=new JSONParser();
	private static String url_add_referal = Url_and_Strings.ip+"myproject/add_refer.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		  View rootView = inflater.inflate(R.layout.refer_layout,
					container, false);
		  referTo=(EditText)rootView.findViewById(R.id.edit_for_refer_to);
			purpose=(EditText)rootView.findViewById(R.id.edit_for_purpose);
			yourEmail=(EditText)rootView.findViewById(R.id.edit_for_your_email);
			yourPhone=(EditText)rootView.findViewById(R.id.edit_for_your_phone);
			theirEmail=(EditText)rootView.findViewById(R.id.edit_for_their_email);
			theirPhone=(EditText)rootView.findViewById(R.id.edit_for_their_phone_number);
			refer=(Button)rootView.findViewById(R.id.buttontorefer);
           refer.setOnClickListener(this);
		  
		  return rootView;

	}
	public void onRefer(View v)
	{
		new ReferSubTask().execute();
		Toast.makeText(Url_and_Strings.context, "Referal Sent", Toast.LENGTH_LONG).show();
		referTo.setText("");
		purpose.setText("");
		yourEmail.setText("");
		yourPhone.setText("");
		theirEmail.setText("");
		theirPhone.setText("");
	}
	class ReferSubTask extends AsyncTask<String, String, String>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Url_and_Strings.context);
			pDialog.setMessage("Sending your Referal..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> bun = new ArrayList<NameValuePair>();
			bun.add(new BasicNameValuePair("ReferTo",referTo.getText().toString() ));
			bun.add(new BasicNameValuePair("Purpose", purpose.getText().toString()));
			bun.add(new BasicNameValuePair("Their_Email", theirEmail.getText().toString()));
			bun.add(new BasicNameValuePair("Their_Phone", theirPhone.getText().toString()));
			bun.add(new BasicNameValuePair("Your_Email", yourEmail.getText().toString()));
			bun.add(new BasicNameValuePair("Your_Phone", yourPhone.getText().toString()));
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jParser.makeHttpRequest(url_add_referal,
					"POST", bun);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			
			return null;
		}
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

		
	}
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(8);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.buttontorefer)
		{
			new ReferSubTask().execute();
			Toast.makeText(Url_and_Strings.context, "Referal Sent", Toast.LENGTH_LONG).show();
			referTo.setText("");
			purpose.setText("");
			yourEmail.setText("");
			yourPhone.setText("");
			theirEmail.setText("");
			theirPhone.setText("");
		}
	}


}
