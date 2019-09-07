package me.kumarrohit.syndicateadmin;

public class Profile {

    String name,dob,phone,account_number,balance,profile_image  ;

    public Profile(String name, String dob, String phone, String account_number, String balance, String profile_image) {
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.account_number = account_number;
        this.balance = balance;
        this.profile_image = profile_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
