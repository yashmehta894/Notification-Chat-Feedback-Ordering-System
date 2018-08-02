package Team;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Culture.CultureAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class team_class {

	private String id;
	private String name;
	private String designation;
	private String email;
	private String phone;
	private String imageurl;
	private Team_adapter sta;
	private ImageLoadTask loader_of_image;
	
	public team_class(String id,String name,String desig,String email,String phone,String imageurl) {
		// TODO Auto-generated constructor stub
		this.id =id;
		this.name=name;
		this.designation=desig;
		this.email=email;
		this.phone=phone;
		this.imageurl=imageurl;
	}

	public Team_adapter getSta() {
		return sta;
	}

	public void setSta(Team_adapter sta) {
		this.sta = sta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Bitmap getImagebitmap() {
		return imagebitmap;
	}

	public void setImagebitmap(Bitmap imagebitmap) {
		this.imagebitmap = imagebitmap;
	}

	private Bitmap imagebitmap;

	public void loadImage(Team_adapter sta) {
		// HOLD A REFERENCE TO THE ADAPTER
		this.sta = sta;
		if (imageurl != null && !imageurl.equals("")) {
			loader_of_image = new ImageLoadTask();
			loader_of_image.execute(imageurl);
		}
	}

	public void stoploader() {
		try {
			loader_of_image.cancel(true);
		} catch (Exception e) {
			Log.e("Culture_image", "Caught exception in stopping");
		}
	}

	private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

		@Override
		protected void onPreExecute() {
			Log.i("ImageLoadTask", "Loading image...");
		}

		// PARAM[0] IS IMG URL
		protected Bitmap doInBackground(String... param) {
			Log.i("ImageLoadTask", "Attempting to load image URL: " + param[0]);
			try {
				/* Bitmap b = ImageService.getBitmapFromURLWithScale(param[0]); */
				URL url = new URL(param[0]);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				Log.i("ImageLoadTask", "connection successfull");
				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onProgressUpdate(String... progress) {
			// NO OP
		}

		protected void onPostExecute(Bitmap ret) {
			if (ret != null) {
				Log.i("ImageLoadTask", "Successfully loaded " + name + " image");
				imagebitmap = ret;
				if (sta != null) {
					// WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
					sta.notifyDataSetChanged();
				}
			} else {
				Log.e("ImageLoadTask", "Failed to load " + name + " image");
			}
			Log.e("Culture", "images assync task finished");
		}
	}
}
