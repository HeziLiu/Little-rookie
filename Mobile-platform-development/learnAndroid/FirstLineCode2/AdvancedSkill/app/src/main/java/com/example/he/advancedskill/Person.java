package com.example.he.advancedskill;

import java.io.Serializable;

/**
 * 序列化
 * Created by he on 2016/5/14.
 */
public class Person implements Serializable {
    private String name;
    private int age;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }


    public String getName() {
        return name;
    }

}
