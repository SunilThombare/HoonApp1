package com.babybong.appting.adater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.common.ApiAddress;
import com.babybong.appting.model.MemberDto;

public class MemberListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<MemberDto> movieItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public MemberListAdapter(Activity activity, List<MemberDto> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		if (imageLoader == null) {
			imageLoader = AppController.getInstance().getImageLoader();
		}
		NetworkImageView image1 = (NetworkImageView) convertView.findViewById(R.id.image1);
		TextView nickName = (TextView) convertView.findViewById(R.id.nickName);
		TextView age = (TextView) convertView.findViewById(R.id.age);
		TextView hobby = (TextView) convertView.findViewById(R.id.hobby);
		TextView address1 = (TextView) convertView.findViewById(R.id.address1);
		TextView address2 = (TextView) convertView.findViewById(R.id.address2);
		TextView date = (TextView) convertView.findViewById(R.id.date);

		// getting movie data for the row
		MemberDto m = movieItems.get(position);

		image1.setImageUrl(ApiAddress.IMAGE_URL + m.getImage1(), imageLoader);
		nickName.setText(m.getNickName());
		age.setText("( " + m.getAge() + " )");
		hobby.setText(m.getHobby());
		address1.setText(m.getAddress1());
		address2.setText(m.getAddress2());
		date.setText(m.getWantDate());

		return convertView;
	}

}