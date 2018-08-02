package Culture;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class Images_of_Culture {
	private String imgUrl;
	private String Text;
	private Bitmap image;
	private CultureAdapter sta;
	private ImageLoadTask loader_of_image;

	public Images_of_Culture(String url, String Text) {
		this.imgUrl = url;
		this.Text = Text;
	}

	public Images_of_Culture() {

	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public CultureAdapter getSta() {
		return sta;
	}

	public void setSta(CultureAdapter sta) {
		this.sta = sta;
	}

	public void loadImage(CultureAdapter sta) {
		// HOLD A REFERENCE TO THE ADAPTER
		this.sta = sta;
		if (imgUrl != null && !imgUrl.equals("")) {
			loader_of_image = new ImageLoadTask();
			loader_of_image.execute(imgUrl);
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
				Log.i("ImageLoadTask", "Successfully loaded " + Text + " image");
				image = ret;
				if (sta != null) {
					// WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
					sta.notifyDataSetChanged();
				}
			} else {
				Log.e("ImageLoadTask", "Failed to load " + Text + " image");
			}
			Log.e("Culture", "images assync task finished");
		}
	}
}
