package com.qatestlab;

public class Random {
    private static int shortRandAdd = 0;
    private static int longRandAdd = 0;

	public static int genInt(int from, int to){
		int tmp = 0;
		if(to >= from)
			tmp = (int) (from + Math.round((Math.random() * (to - from))));
		return tmp;
	}

	public static float genFloat(double from, double to){
		float tmp = .0f;
		if(to >= from)
			tmp = (float) (from + (Math.random() * (to - from)));
		return tmp;
	}

    public static float genFloat(double from, double to, int precision){
        float number = genFloat(from, to);
        return (float) Math.round(number * Math.pow(10,precision)) / (float) Math.pow(10,precision);
    }

    public static synchronized long genRandNumber() {
        return System.currentTimeMillis() % 10_000_000_000L + longRandAdd++;
    }

    public static synchronized long genShortRandNumber() {
        return (genInt(1, 9) * 10_000_000) + (System.currentTimeMillis() % 10_000_000) + shortRandAdd++;
    }

    public static String genStreet() {
        return genRandNumber() + " Main str.";
    }

    public static String genPhone() {
        return "555" + genInt(1000000, 9999999);
    }

    public static String genEmail() {
        return "mail" + genInt(1000000, 9999999) + "@mail.com";
    }

    public static String genEmail(String emailPattern) {
        return emailPattern.substring(0, emailPattern.indexOf("@")) + "+" + genShortRandNumber()
                + emailPattern.substring(emailPattern.indexOf("@"), emailPattern.length());
    }
}
