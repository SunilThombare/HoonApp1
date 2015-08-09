package com.babybong.appting.common;

import com.babybong.appting.app.AppController;

/**
 * Created by hoon on 2015-06-13.
 */
public class ApiAddress {
    public final static String MEMBER_CREATE = AppController.API_URL + "/members/create"; //멤버등록 url
    public final static String BASIC_PROFILE_CREATE = AppController.API_URL + "/members/basicProfile";
    public final static String MEMBER_ADD_INFO = AppController.API_URL + "/members/"; //멤버개인정보 추가 url
    public final static String DEVICE_REG_ID_ADD = AppController.API_URL + "/members/deviceRegId";

    public final static String IMAGE_URL = AppController.API_URL + "/image/";
}
