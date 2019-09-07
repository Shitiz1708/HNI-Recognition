package me.kumarrohit.syndicateadmin;

public class request {
    String name,date,time,purpose,message ;

    public request(String name, String date, String time, String purpose, String message) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.purpose = purpose;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
