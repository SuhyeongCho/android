package com.example.suhyeongcho.server;

import android.content.Intent;

import java.io.Serializable;

public class Result implements Serializable {

    private String totalResult;
    private String aResult;
    private String bResult;
    private String cResult;
    private String dResult;

    public void setResult(String result){
        String[] arr = result.split(",");
        aResult = arr[0];
        bResult = arr[1];
        cResult = arr[2];
    }

    public void calculateTotalResult(){
        int a = Integer.parseInt(aResult);
        int b = Integer.parseInt(bResult);
        int c = Integer.parseInt(cResult);
        int d = Integer.parseInt(dResult);

        int total = (a+b+c+d)/4;

        totalResult = Integer.toString(total);

    }
    public void setDResult(String dResult){
        int d = Integer.parseInt(dResult);
        if(d >= 6){ this.dResult = "100"; }
        else{ this.dResult = "0";}
    }
    public String getTotalResult(){ return totalResult; }
    public String getAResult(){ return aResult; }
    public String getBResult(){ return bResult; }
    public String getCResult(){ return cResult; }
    public String getDResult(){ return dResult; }

}
