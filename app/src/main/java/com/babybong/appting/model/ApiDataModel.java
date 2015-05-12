package com.babybong.appting.model;

/**
 * Created by hoon on 2015-05-02.
 */
public class ApiDataModel<T> {
    private boolean isApiSuccess = true;
    private String apiMessage;

    private T dto;

    public T getDto() {
        return dto;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

    public boolean isApiSuccess() {
        return isApiSuccess;
    }

    public void setApiSuccess(boolean isApiSuccess) {
        this.isApiSuccess = isApiSuccess;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }
}
