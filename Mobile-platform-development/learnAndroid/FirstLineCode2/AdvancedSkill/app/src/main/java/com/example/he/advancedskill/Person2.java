package com.example.he.advancedskill;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by he on 2016/5/14.
 */
public class Person2 implements Parcelable {
    String name;
    int age;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    //将数据写入
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);//写出name
        dest.writeInt(age);//写出age
    }

    public static  final  Parcelable.Creator<Person2> CREATOR= new Creator<Person2>() {
        @Override
        //读取存放的数据,注意这里读取的顺序一定要和刚才写入的顺序相同
        public Person2 createFromParcel(Parcel source) {
            Person2 person2=new Person2();
            person2.name=source.readString();//读取name
            person2.age=source.readInt();//读取age
            return  person2;
        }

        @Override
        public Person2[] newArray(int size) {
            return new Person2[size];
        }
    };
}
