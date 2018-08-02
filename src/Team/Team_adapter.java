package Team;

import java.util.ArrayList;
import java.util.List;

import com.example.applw.R;

import android.net.Uri;
import android.view.View.OnClickListener;
import Culture.Images_of_Culture;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Team_adapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private List items = new ArrayList();
	Context context;
	team_class s;

	public Team_adapter(Context context, List items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (Team_adapter) items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		 s = (team_class) items.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.teamcustomlistview, null);
			holder = new ViewHolder();
			// holder.name = (TextView) convertView.findViewById(R.id.);
			holder.image = (ImageView) convertView
					.findViewById(R.id.team_custom_image_id);
			holder.name = (TextView) convertView
					.findViewById(R.id.team_member_name);
			holder.desig = (TextView) convertView
					.findViewById(R.id.team_member_desig);
			holder.email = (TextView) convertView
					.findViewById(R.id.team_memberemail);
			holder.phoneno = (TextView) convertView
					.findViewById(R.id.team_memberphone);
			holder.call = (Button) convertView
					.findViewById(R.id.team_memberbutton);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// holder.name.setText(s.getName());
		if (s.getImagebitmap() != null) {
			holder.image.setImageBitmap(s.getImagebitmap());
			holder.name.setText(s.getName());
			holder.email.setText(s.getEmail());
			holder.desig.setText(s.getDesignation());
			holder.phoneno.setText(s.getPhone());
			holder.call.setBackgroundResource(R.drawable.callbutton);
			
		} else {
			// MY DEFAULT IMAGE
			holder.image.setImageResource(R.drawable.ajaxloader);
			holder.name.setText(s.getName());
			holder.email.setText(s.getEmail());
			holder.desig.setText(s.getDesignation());
			holder.phoneno.setText(s.getPhone());
			holder.call.setBackgroundResource(R.drawable.callbutton);

		}
		holder.call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"
						+ s.getPhone()));
				context.startActivity(callIntent);

			}
		});
		return convertView;
	}

	static class ViewHolder {
		// TextView name;

		ImageView image;
		TextView name;
		TextView email;
		TextView desig;
		TextView phoneno;
		Button call;
	}
}
