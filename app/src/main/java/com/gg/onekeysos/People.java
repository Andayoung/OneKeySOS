package com.gg.onekeysos;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class People {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public People(String name,String number){
        this.name=name;
        this.number=number;
    }
}
