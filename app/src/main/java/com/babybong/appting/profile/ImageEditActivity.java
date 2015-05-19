package com.babybong.appting.profile;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.login.service.DataStoredService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //setImageSizeBoundary(400); // optional. default is 500.
                setCropOption(1, 1);  // optional. default is no crop.
                //setCustomButtons(btnGallery, btnCamera, btnCancel); // you can set these buttons.
                startSelectImage();
            }
        });
        getSelectedImageFile(); // extract selected & saved image file.
    }

    public void onClickSaveBtn(View view) {
        test();
    }

    private void test() {
        String mail = DataStoredService.getStoredData(ImageEditActivity.this, DataStoredService.STORE_MAIL);
        String url = AppController.API_URL + "/members/image/upload";
        File file = getSelectedImageFile();
        PhotoMultipartRequest request = new PhotoMultipartRequest(url, errorListener, successListener, file);
        request.addTextBody("mail", mail);
        AppController.getInstance().addToRequestQueue(request);
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

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //alertMessage("잠시후 다시 시도해주세요.");
            VolleyLog.e("Error: ", "에러 : " + error.toString());
            error.printStackTrace();
        }
    };
}
