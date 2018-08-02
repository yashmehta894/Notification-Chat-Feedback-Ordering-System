package Calendar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applw.R;

public class Calendar_adapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private List items = new ArrayList();

	public Calendar_adapter(Context context, List items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	public int getCount() {
		return items.size();
	}

	public calendar_class getItem(int position) {
		return (calendar_class) items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		calendar_class s = (calendar_class) items.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.calendar_custom_layout,
					null);
			holder = new ViewHolder();
			holder.date = (TextView) convertView
					.findViewById(R.id.Calendar_date_text);
			holder.image = (ImageView) convertView
					.findViewById(R.id.Calendar_image_event_1);
			holder.description = (TextView) convertView
					.findViewById(R.id.Calendar_text_event_1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (s.getEventtype().equals("birthday")) {
			holder.date.setText(s.getDate());
            holder.image.setImageResource(R.drawable.birthday);
            holder.description.setText(s.getDescription());
		}
		else if(s.getEventtype().equals("event")){
			holder.date.setText(s.getDate());
            holder.image.setImageResource(R.drawable.specialevent);
            holder.description.setText(s.getDescription());
		}
		else
		{
			holder.date.setText(s.getDate());
            holder.image.setImageResource(R.drawable.holiday);
            holder.description.setText(s.getDescription());
		}
			return convertView;
		
	}

		class ViewHolder {
			public TextView date;

			public ImageView image;
			public TextView description;
			
		}
	
}