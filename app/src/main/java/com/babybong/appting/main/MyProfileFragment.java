package com.babybong.appting.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.common.ApiAddress;
import com.babybong.appting.login.LoginActivity;
import com.babybong.appting.login.LoginInfoActivity;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.profile.ProfileEditActivity;
import com.babybong.appting.util.LruBitmapCache;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public final class MyProfileFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";



    public static MyProfileFragment newInstance(String content) {
        MyProfileFragment fragment = new MyProfileFragment();

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

    private NetworkImageView ivImage1, ivImage2, ivImage3, ivImage4;
    private ImageLoader imageLoader;

    private EditText inputNickNmae;
    private EditText inputJob;
    private EditText inputHobby;
    private EditText inputArea1;
    private EditText inputCharacter;
    private EditText inputBloodtype, inputReligion;
    private EditText inputHeight, inputBodyType;

    private Button editBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);

        inputNickNmae = (EditText) view.findViewById(R.id.inputNickNmae);
        inputArea1 = (EditText) view.findViewById(R.id.inputArea1);
        inputJob = (EditText) view.findViewById(R.id.inputJob);
        inputHobby = (EditText) view.findViewById(R.id.inputHobby);
        inputCharacter = (EditText) view.findViewById(R.id.inputCharacter);
        inputBloodtype = (EditText) view.findViewById(R.id.inputBloodtype);
        inputReligion = (EditText) view.findViewById(R.id.inputReligion);
        inputHeight = (EditText) view.findViewById(R.id.inputHeight);
        inputBodyType = (EditText) view.findViewById(R.id.inputBodyType);

        ivImage1 = (NetworkImageView)view.findViewById(R.id.ivImage1);
        ivImage2 = (NetworkImageView)view.findViewById(R.id.ivImage2);
        ivImage3 = (NetworkImageView)view.findViewById(R.id.ivImage3);
        ivImage4 = (NetworkImageView)view.findViewById(R.id.ivImage4);

        ImageLoader.ImageCache imageCache = new LruBitmapCache();
        imageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), imageCache);

        editBtn = (Button)view.findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ProfileEditActivity.class);
            }
        });

        getProfileDataFromServer();
        return view;
    }

    private String[] profileImages = new String[4];

    private void startActivity(Class zlass) {
        Intent intent = new Intent(getActivity(), zlass);
        intent.putExtra("profileImages", profileImages);
        startActivity(intent);
    }

    private void getProfileDataFromServer() {
        final String mail = DataStoredService.getStoredData(getActivity(), DataStoredService.STORE_MAIL);
        String url = AppController.API_URL + "/members/findMember";

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mail", mail);
        } catch (JSONException e) {
            Log.e("error", e.getMessage());
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    boolean isApiSuccess = response.getBoolean("apiSuccess");
                    String apiMessage = response.getString("apiMessage");
                    Log.d("isMember", "isApiSuccess : " + isApiSuccess);
                    Log.d("isMember", "apiMessage : " + apiMessage);
                    if (isApiSuccess) {
                        JSONObject memberDto = response.getJSONObject("dto");
                        String nickName = memberDto.getString("nickName");
                        String job = memberDto.getString("job");
                        String hobby = memberDto.getString("hobby");
                        String character = memberDto.getString("character");
                        String religion = memberDto.getString("religion");
                        String bloodType = memberDto.getString("bloodType");
                        String address1 = memberDto.getString("address1");
                        String height = memberDto.getString("height");
                        String bodyType = memberDto.getString("bodyType");

                        inputNickNmae.setText(nickName);
                        inputArea1.setText(address1);
                        inputJob.setText(job);
                        inputHobby.setText(hobby);
                        inputCharacter.setText(character);
                        inputBloodtype.setText(bloodType);
                        inputReligion.setText(religion);
                        inputHeight.setText(height);
                        inputBodyType.setText(bodyType);

                        imageUpload(ivImage1, memberDto.getString("image1"));
                        imageUpload(ivImage2, memberDto.getString("image2"));
                        imageUpload(ivImage3, memberDto.getString("image3"));
                        imageUpload(ivImage4, memberDto.getString("image4"));

                        profileImages[0] = memberDto.getString("image1");
                        profileImages[1] = memberDto.getString("image2");
                        profileImages[2] = memberDto.getString("image3");
                        profileImages[3] = memberDto.getString("image4");
                    } else {
                        alertMessage("잠시후 다시 시도하세요.");
                    }
                } catch (JSONException e) {
                    alertMessage("담당자에게 문의하세요.");
                    Log.e("login", "JSONException error : " + e.toString());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                alertMessage("잠시후 다시 시도하세요.");
                Log.e("login", "VolleyError error : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try {
                    Log.i("json", jsonObject.toString());
                    return jsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        AppController.getInstance().addToRequestQueue(jsObjRequest);
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

    private void imageUpload(NetworkImageView nImageView, String imageName) {
        String url = ApiAddress.IMAGE_URL + imageName;
        nImageView.setImageUrl(url, imageLoader);
        nImageView.setDefaultImageResId(R.drawable.ic_launcher);
        //ivImage1.setErrorImageResId(..);
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
}
