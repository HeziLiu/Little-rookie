package com.example.he.testexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by he on 2016/5/15.
 */
public class CalculatorTest {
   private  Calculator  calculator;

    @Before//在测试之前调用
    public void setUp() throws Exception {
         calculator=new Calculator();
    }

    @After
    public void tearDown() throws Exception {

    }

 @Test
 public void testSum() throws Exception {
  //expected: 6, sum of 1 and 5
  assertEquals(6d, calculator.sum(1d, 5d), 0);
 }

 @Test
 public void testSubstract() throws Exception {
  assertEquals(1d, calculator.substract(5d, 4d), 0);
 }

 @Test
 public void testDivide() throws Exception {
  assertEquals(4d, calculator.divide(20d, 5d), 0);
 }

 @Test
 public void testMultiply() throws Exception {
  assertEquals(10d, calculator.multiply(2d, 5d), 0);
 }
}