package com.xy.utils;

public class StringUtils {
	// 去除图片后缀
	public String removeSuffixOfProductName(String productName) {
		return productName.substring(0, productName.length() - 4);
	}
	
	public static void main(String[] args) {
		StringUtils su = new StringUtils();
		System.out.println(su.removeSuffixOfProductName("product1.jpg"));
	}
}
