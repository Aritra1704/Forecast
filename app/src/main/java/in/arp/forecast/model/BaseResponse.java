package in.arp.forecast.model;

public class BaseResponse {
    private String cod;
    private String message;
    private int cnt;
    private String list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}

