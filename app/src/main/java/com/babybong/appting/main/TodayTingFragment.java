package com.babybong.appting.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.babybong.appting.R;
import com.babybong.appting.adater.MemberListAdapter;
import com.babybong.appting.app.AppController;
import com.babybong.appting.common.ApiAddress;
import com.babybong.appting.model.MemberDto;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class TodayTingFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final String TAG = "TodayTingFragment";


    public static TodayTingFragment newInstance(String content) {
        TodayTingFragment fragment = new TodayTingFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateTracker();
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }


    private ProgressDialog pDialog;
    private List<MemberDto> connectedMembers = new ArrayList<MemberDto>();
    private ListView listView;
    private MemberListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todayting, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new MemberListAdapter(getActivity(), connectedMembers);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        dataLoading();
        return view;
    }

    private void dataLoading() {
        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(ApiAddress.CONNECTED_MEMBERS + "?myEmail=dokkl@naver.com",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                MemberDto memberDto = new MemberDto();
                                memberDto.setNickName(obj.getString("nickName"));
                                memberDto.setAge(obj.getInt("age"));
                                memberDto.setAddress1(obj.getString("address1"));
                                memberDto.setAddress2(obj.getString("address2"));
								memberDto.setImage1(obj.getString("image1"));
                                memberDto.setHobby(obj.getString("hobby"));
                                memberDto.setCharacter(obj.getString("character"));

								/*// Genre is json array
								JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
								}
								movie.setGenre(genre);*/

                                // adding movie to movies array
                                connectedMembers.add(memberDto);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    public void alertMessage(String message) {
        Log.d("alertMessage", "message : " + message);
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());
    }

    private void onCreateTracker() {
        Tracker tracker = ((AppController)getActivity().getApplication()).getTracker();
        // Builder parameters can overwrite the screen name set on the tracker.
        tracker.setScreenName("MyProfileFragment");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
