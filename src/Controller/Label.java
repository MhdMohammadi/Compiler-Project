package Controller;

import java.util.ArrayList;

public class Label {

    private String name;
    static int number = 0;


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void creatLabel(){
        setName("L" + number);
        number += 1;
    }
}
