package com.xy.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;



public class MD5Dao {

	public static void main(String[] args) {
		MD5Dao md5dao=new MD5Dao();
		try {
	
			System.out.println(md5dao.EncoderByMd5("123456"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	  public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		    //ȷ�����㷽��
		    MessageDigest md5=MessageDigest.getInstance("MD5");
		    BASE64Encoder base64en = new BASE64Encoder();
		    //���ܺ���ַ���
		    String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
		    return newstr;
		  }
	  public boolean checkpassword(String newpasswd,String oldpasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		    if(EncoderByMd5(newpasswd).equals(oldpasswd))
		      return true;
		    else
		      return false;
		  }
}
