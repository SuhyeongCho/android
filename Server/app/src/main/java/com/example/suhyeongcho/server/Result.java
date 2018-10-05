package com.example.suhyeongcho.server;

import java.io.Serializable;

public class Result implements Serializable {

    private String totalResult;
    private String aResult;
    private String bResult;
    private String cResult;

    public void setResult(String result){
        String[] arr = result.split(",");
        totalResult = arr[0];
        aResult = arr[1];
        bResult = arr[2];
        cResult = arr[3];
    }

    public String getTotalResult(){ return totalResult; }
    public String getAResult(){ return aResult; }
    public String getBResult(){ return bResult; }
    public String getCResult(){ return cResult; }

}
