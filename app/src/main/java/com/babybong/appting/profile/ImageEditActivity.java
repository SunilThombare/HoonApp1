package com.babybong.appting.profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.common.ApiAddress;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.profile.image.ImageDownloader;
import com.babybong.appting.profile.image.MultipartRequest;
import com.babybong.appting.profile.image.PhotoMultipartRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by hoon on 2015-05-16.
 */
public class ImageEditActivity extends ImageSelectHelperActivity {

    private ImageView ivImageSelected;
    private ImageView ivImageSelected2;
    private ImageView ivImageSelected3;
    private ImageView ivImageSelected4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        ivImageSelected  = (ImageView)findViewById(R.id.ivImageSelected);
        ivImageSelected2 = (ImageView)findViewById(R.id.ivImageSelected2);
        ivImageSelected3 = (ImageView)findViewById(R.id.ivImageSelected3);
        ivImageSelected4 = (ImageView)findViewById(R.id.ivImageSelected4);

        ivImageSelected.setOnClickListener(onClickListener);
        ivImageSelected2.setOnClickListener(onClickListener);
        ivImageSelected3.setOnClickListener(onClickListener);
        ivImageSelected4.setOnClickListener(onClickListener);

        /*findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //setImageSizeBoundary(400); // optional. default is 500.
                setCropOption(1, 1);  // optional. default is no crop.
                //setCustomButtons(btnGallery, btnCamera, btnCancel); // you can set these buttons.
                startSelectImage();
            }
        });*/
        //getSelectedImageFile(); // extract selected & saved image file.

        String[] profileImages = getIntent().getExtras().getStringArray("profileImages");
        Log.d("hoon", "profileImages : " + profileImages[0]);
        Log.d("hoon", "profileImages : " + profileImages[1]);
        Log.d("hoon", "profileImages : " + profileImages[2]);
        Log.d("hoon", "profileImages : " + profileImages[3]);
        if (profileImages[0] != null && !profileImages[0].equals("null")) {
            new ImageDownloader(ivImageSelected).execute(ApiAddress.IMAGE_URL + profileImages[0]);
        }
        if (profileImages[1] != null && !profileImages[1].equals("null")) {
            new ImageDownloader(ivImageSelected2).execute(ApiAddress.IMAGE_URL + profileImages[1]);
        }
        if (profileImages[2] != null && !profileImages[2].equals("null")) {
            new ImageDownloader(ivImageSelected3).execute(ApiAddress.IMAGE_URL + profileImages[2]);
        }
        if (profileImages[3] != null && !profileImages[3].equals("null")) {
            new ImageDownloader(ivImageSelected4).execute(ApiAddress.IMAGE_URL + profileImages[3]);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivImageSelected:
                    //setImageSizeBoundary(400); // optional. default is 500.
                    setCropOption(1, 1);  // optional. default is no crop.
                    //setCustomButtons(btnGallery, btnCamera, btnCancel); // you can set these buttons.
                    startSelectImage(R.id.ivImageSelected);
                    break;
                case R.id.ivImageSelected2:
                    setCropOption(1, 1);  // optional. default is no crop.
                    startSelectImage(R.id.ivImageSelected2);
                    break;
                case R.id.ivImageSelected3:
                    setCropOption(1, 1);  // optional. default is no crop.
                    startSelectImage(R.id.ivImageSelected3);
                    break;
                case R.id.ivImageSelected4:
                    setCropOption(1, 1);  // optional. default is no crop.
                    startSelectImage(R.id.ivImageSelected4);
                    break;
            }
        }
    };

    /*public void onClickSaveBtn(View view) {
        imageUpload();
    }*/

    @Override
    public void imageUploadToServer(int selectedImageResourceId) {
        Log.d("TAG", "selectedImageResourceId : " + selectedImageResourceId);
        imageUpload(selectedImageResourceId);
    }

    /**
     * 프로필 이미지 서버로 전송
     */
    private void imageUpload(int selectedImageResourceId) {
        String mail = DataStoredService.getStoredData(ImageEditActivity.this, DataStoredService.STORE_MAIL);
        String url = AppController.API_URL + "/members/image/upload";
        File file = getSelectedImageFile();
        Log.d("TAG", ">> getName : " + file.getName());
        PhotoMultipartRequest request = new PhotoMultipartRequest(url, errorListener, successListener, file);
        Log.d("TAG", ">> getName 2 ");
        request.addTextBody("mail", mail);
        request.addTextBody("imageNumber", getImageNumber(selectedImageResourceId));
        Log.d("TAG", ">> getName 3 ");
        AppController.getInstance().addToRequestQueue(request);
        Log.d("TAG", ">> getName 4 ");
    }



    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    Response.Listener successListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d("SUCCESS", "SUCCESS !!!!!");
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //alertMessage("잠시후 다시 시도해주세요.");
            Log.e("SUCCESS", "error !!!!!" + error.getMessage());
            VolleyLog.e("Error: ", "에러 : " + error.toString());
            error.printStackTrace();
        }
    };
}
