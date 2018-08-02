package suggestion;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applw.JSONParser;
import com.example.applw.MainActivity;
import com.example.applw.R;
import com.example.applw.Url_and_Strings;

public class suggesstion_list_activity extends Fragment implements View.OnClickListener {
	EditText edit_problem;
	EditText edit_improve;
	EditText edit_anything_else;
	EditText edit_email;
	EditText edit_phone;
	Button button_to_submit;

	JSONParser jParser = new JSONParser();
	private static String url_add_suggestion = Url_and_Strings.ip
			+ "myproject/add_suggestions.php";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.contactus, container, false);

		edit_problem = (EditText) rootView.findViewById(R.id.edit_for_problem);
		edit_improve = (EditText) rootView.findViewById(R.id.edit_for_improve);
		edit_anything_else = (EditText) rootView
				.findViewById(R.id.edit_for_anything_else);
		edit_email = (EditText) rootView.findViewById(R.id.edit_for_mail);
		edit_phone = (EditText) rootView.findViewById(R.id.edit_for_phone);
		button_to_submit = (Button) rootView.findViewById(R.id.buttontosubmit);

		button_to_submit.setOnClickListener(this);
		return rootView;

	}

	public void onSubmit(View v) {
		new TaskToAddSuggestion().execute();
		Toast.makeText(Url_and_Strings.context, "Suggestion Sent",
				Toast.LENGTH_LONG).show();
		edit_problem.setText("");
		edit_improve.setText("");
		edit_anything_else.setText("");
		edit_email.setText("");
		edit_phone.setText("");

	}

	class TaskToAddSuggestion extends AsyncTask<String, String, String> {
		/*
		 * protected void onPreExecute() { super.onPreExecute(); pDialog = new
		 * ProgressDialog(MainActivity.this);
		 * pDialog.setMessage("Sending your Suggestion..");
		 * pDialog.setIndeterminate(false); pDialog.setCancelable(true);
		 * pDialog.show(); }
		 */
		@Override
		protected String doInBackground(String... params) {
			String problem = edit_problem.getText().toString();
			String improve = edit_improve.getText().toString();
			String anything = edit_anything_else.getText().toString();
			String email = edit_email.getText().toString();
			String phone = edit_phone.getText().toString();
			// Building Parameters
			List<NameValuePair> bun = new ArrayList<NameValuePair>();
			bun.add(new BasicNameValuePair("Problem", problem));
			bun.add(new BasicNameValuePair("Improvement", improve));
			bun.add(new BasicNameValuePair("Anything_Else", anything));
			bun.add(new BasicNameValuePair("Email", email));
			bun.add(new BasicNameValuePair("Phone_Number", phone));
			// getting JSON Object
			// Note that create product url accepts POST method
			try{
			JSONObject json = jParser.makeHttpRequest(url_add_suggestion,
					"POST", bun);
			}
			catch(Exception e)
			{
				return "false";
			}
			// check log cat fro response
			//Log.d("Create Response", json.toString());

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			// pDialog.dismiss();
			if(file_url=="false")
			{
				Toast.makeText(Url_and_Strings.context,"Connection Problem..", Toast.LENGTH_LONG).show();
			}
		}

	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(7);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.buttontosubmit)
		{
			new TaskToAddSuggestion().execute();
			Toast.makeText(Url_and_Strings.context, "Suggestion Sent",
					Toast.LENGTH_LONG).show();
			edit_problem.setText("");
			edit_improve.setText("");
			edit_anything_else.setText("");
			edit_email.setText("");
			edit_phone.setText("");
		}
		
	}

}
