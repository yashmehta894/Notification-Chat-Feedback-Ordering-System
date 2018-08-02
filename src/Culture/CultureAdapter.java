package Culture;

import java.util.ArrayList;
import java.util.List;

import com.example.applw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CultureAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private List items = new ArrayList();

	public CultureAdapter(Context context, List items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	public int getCount() {
		return items.size();
	}

	public Images_of_Culture getItem(int position) {
		return (Images_of_Culture) items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Images_of_Culture s = (Images_of_Culture) items.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.culturefragment_customlistview, null);
			holder = new ViewHolder();
			// holder.name = (TextView) convertView.findViewById(R.id.);
			holder.image = (ImageView) convertView
					.findViewById(R.id.culturefragment_custom_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// holder.name.setText(s.getName());
		if (s.getImage() != null) {
			holder.image.setImageBitmap(s.getImage());
		} else {
			// MY DEFAULT IMAGE
			holder.image.setImageResource(R.drawable.ajaxloader);
		}
		return convertView;
	}

	static class ViewHolder {
		// TextView name;

		ImageView image;
	}
}
