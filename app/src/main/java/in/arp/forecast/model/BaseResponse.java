package in.arp.forecast.model;

import java.util.ArrayList;

public class BaseResponse {
    private String cod;
    private double message;
    private int cnt;
    private ArrayList<WeatherDetail> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<WeatherDetail> getList() {
        return list;
    }

    public void setList(ArrayList<WeatherDetail> list) {
        this.list = list;
    }
}

