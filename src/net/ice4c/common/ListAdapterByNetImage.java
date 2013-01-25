package net.ice4c.common;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.ice4c.tuer.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListAdapterByNetImage extends SimpleAdapter implements
		OnClickListener {
	private Context context;

	public ListAdapterByNetImage(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
	}

	public void setViewImage(ImageView v, int value) {
		v.setImageResource(value);
	}

	public void setViewImage(ImageView v, String value) {
		if (value != null && !"".equals(value)) {
			if (v.getId() == R.id.itm_userface)
				v.setImageResource(R.drawable.ic_launcher);
			ImageUtil.drawTheImageViewFromNet(v, value);
		} else {
			v.setImageBitmap(null);
		}
		v.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(arg0.getContext(), "被点击了", 10000).show();
	}

}
