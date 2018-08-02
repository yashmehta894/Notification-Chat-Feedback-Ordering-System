package calendar_new;

import java.util.ArrayList;
import java.util.List;

import com.example.applw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class calendar_new_adapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private List items = new ArrayList();
	Context context;

	public calendar_new_adapter(Context context, List items) {

		mInflater = LayoutInflater.from(context);
		this.items = items;
		this.context=context;
	}

	public int getCount() {
		return items.size();
	}

	public Date_Class getItem(int position) {
		return (Date_Class) items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	/*	ViewHolder holder;
		Date_Class s = (Date_Class) items.get(position);
		if (convertView == null) {

			if (s.getLength() == 1) {
				convertView = mInflater.inflate(
						R.layout.calendar_custom_layout, null);
				holder = new ViewHolder();
				holder.date = (TextView) convertView
						.findViewById(R.id.Calendar_date_text);
				holder.image.add((ImageView) convertView
						.findViewById(R.id.Calendar_image_event_1));
				holder.description.add((TextView) convertView
						.findViewById(R.id.Calendar_text_event_1));

				convertView.setTag(holder);
			} else  {
				convertView = mInflater.inflate(
						R.layout.calendar_custom_layout_2, null);
				holder = new ViewHolder();
				holder.date = (TextView) convertView
						.findViewById(R.id.Calendar_date_text2);
				holder.image.add((ImageView) convertView
						.findViewById(R.id.Calendar_image_event2_1));
				holder.description.add((TextView) convertView
						.findViewById(R.id.Calendar_text_event2_1));
				
				
				holder.image.add((ImageView) convertView
						.findViewById(R.id.Calendar_image_event22_2));
				holder.description.add((TextView) convertView.findViewById(R.id.Calendar_text_event22_2));
				holder.txt=(TextView) convertView.findViewById(R.id.Calendar_text_event22_2);

				convertView.setTag(holder);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (s.getLength() == 1) {
               
			holder.date.setText(s.Date_of_event);
			(holder.description.get(0)).setText(s.getDescription(0));
            if(s.getEventtype().get(0).equals("birthday"))
            {
            	(holder.image.get(0)).setImageResource(R.drawable.birthday);
            }
            else if(s.getEventtype().get(0).equals(""))
            {
            	(holder.image.get(0)).setImageResource(R.drawable.specialevent);
            }
            else           
            {
            	(holder.image.get(0)).setImageResource(R.drawable.holiday);
            }
			
			
		}
		else if (s.getLength() == 2) {
            
			holder.date.setText(s.Date_of_event);
			(holder.description.get(0)).setText(s.getDescription(0));
			(holder.description.get(1)).setText(
			//if(holder.txt!=null)
			holder.txt=(TextView) convertView.findViewById(R.id.Calendar_text_event22_2);

			holder.txt.setText(s.getDescription(1));
            if(s.getEventtype().get(0).equals("birthday"))
            {
            	(holder.image.get(0)).setImageResource(R.drawable.birthday);
            }
            else if(s.getEventtype().get(0).equals("event"))
            {
            	(holder.image.get(0)).setImageResource(R.drawable.specialevent);
            }
            else           
            {
            	(holder.image.get(0)).setImageResource(R.drawable.holiday);
            }
            
            if(s.getEventtype().get(1).equals("birthday"))
            {
            	(holder.image.get(1)).setImageResource(R.drawable.birthday);
            }
            else if(s.getEventtype().get(1).equals("event"))
            {
            	(holder.image.get(1)).setImageResource(R.drawable.specialevent);
            }
            else           
            {
            	(holder.image.get(1)).setImageResource(R.drawable.holiday);
            }
			
			
		}
		return convertView;*/
		Date_Class s = (Date_Class) items.get(position);
		
		
		
		
		convertView= mInflater.inflate(R.layout.calendar_custom_layout, null);
		TextView textView = (TextView)convertView.findViewById(R.id.Calendar_date_text);
		LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.llContainer);
		textView.setText(s.Date_of_event);
		for(int i=0;i<s.length;i++)
		{LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.calendar_custom_addextra, null);
		TextView textView1 = (TextView) v.findViewById(R.id.Calendar_text_event_1);
		textView1.setText(s.getDescription(i));
		ImageView image_ev = (ImageView) v.findViewById(R.id.Calendar_image_event_1);
		textView1.setText(s.getDescription(i));
		
		if(s.getEventtype().get(i).equals("birthday"))
        {
        	image_ev.setImageResource(R.drawable.birthday);
        }
        else if(s.getEventtype().get(0).equals("event"))
        {
        	image_ev.setImageResource(R.drawable.specialevent);
        }
        else           
        {
        	image_ev.setImageResource(R.drawable.holiday);
        }
		linearLayout.addView(v);
		}
//		ViewGroup insertPoint = (ViewGroup)v.findViewById(R.id.calendar_linearlayout_insertpoint);
//		insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		
		return convertView;
	}

	
	
	/*class ViewHolder {
		public TextView date;

		public List<ImageView> image;
		public List<TextView> description;
		
		ImageView img;
		TextView txt;

		public ViewHolder() {

			image = new ArrayList<ImageView>();
			description = new ArrayList<TextView>();
		}
	}*/
}
