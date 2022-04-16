package com.ishmaev.artur;

import junit.framework.TestCase;

public class MainTest extends TestCase {

    public void test1ArabicInRoman() {
        String expected = Main.arabicInRoman(12);
        String actual = "XI";
        assertEquals(expected,actual);
    }

    public void test2ArabicInRoman() {
        String expected = Main.arabicInRoman(14);
        String actual = "XIV";
        assertEquals(expected,actual);
    }

    public void test3ArabicInRoman() {
        String expected = Main.arabicInRoman(8);
        String actual = "VIII";
        assertEquals(expected,actual);
    }

    public void test4ArabicInRoman() {
        String expected = Main.arabicInRoman(20);
        String actual = "XX";
        assertEquals(expected,actual);
    }
}