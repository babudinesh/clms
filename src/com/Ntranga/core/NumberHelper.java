package com.Ntranga.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.IntegerValidator;

public class NumberHelper {

	public static int integerValue(String number) {
		IntegerValidator validator = IntegerValidator.getInstance();
		if (validator.validate(number) != null)
			return Integer.parseInt(number);
		return -1;

	}

	public static boolean isValidInteger(String number) {
		IntegerValidator validator = IntegerValidator.getInstance();
		return validator.validate(number) != null;
	}

	public static boolean isValidDouble(String number) {
		DoubleValidator validator = DoubleValidator.getInstance();
		return validator.validate(number) != null;
	}

	public static boolean isValidDouble(String number, int zero) {
		DoubleValidator validator = DoubleValidator.getInstance();
		if (validator.validate(number) != null)
			if (validator.validate(number) > 0)
				return true;
		return false;
	}

	public static boolean isValidPositiveInteger(String number) {
		IntegerValidator validator = IntegerValidator.getInstance();
		if (validator.validate(number) != null)
			if (Integer.parseInt(number) > 0)
				return true;
		return false;
	}

	public static boolean isValidPositiveInteger(String number, int zero) {
		IntegerValidator validator = IntegerValidator.getInstance();
		if (validator.validate(number) != null)
			if (Integer.parseInt(number) > -1)
				return true;
		return false;
	}
	
	public static BigDecimal roundUp(BigDecimal val){
		return val.setScale(0, RoundingMode.HALF_UP);
	}

	public static String convertNumberToRomanLetter(String number) {
		IntegerValidator validator = IntegerValidator.getInstance();
		String romanLetter = "";

		if (validator.validate(number) != null) {
			switch (Integer.parseInt(number)) {

			case 1:
				romanLetter = "I";
				break;
			case 2:
				romanLetter = "II";
				break;
			case 3:
				romanLetter = "III";
				break;
			case 4:
				romanLetter = "IV";
				break;
			case 5:
				romanLetter = "V";
				break;
			case 6:
				romanLetter = "VI";
				break;
			case 7:
				romanLetter = "VII";
				break;
			case 8:
				romanLetter = "VIII";
				break;
			case 9:
				romanLetter = "IX";
				break;
			case 10:
				romanLetter = "X";
			}
		}
		return romanLetter;
	}

	/*
	 * This method will convert the number into string format with the help of
	 * getWordForNumber method.
	 */
	public String convertNumberToWords(long number) {
		String numberInWordsFormat = "Enter an integer number";
		if (number <= 0)
			numberInWordsFormat = "Enter numbers greater than 0";
		else {
			numberInWordsFormat = getWordForNumber((number / 1000000000), " Hundred ") + getWordForNumber((number / 10000000) % 100, " crore ") + getWordForNumber(((number / 100000) % 100), " lakh ")
					+ getWordForNumber(((number / 1000) % 100), " thousand ") + getWordForNumber(((number / 100) % 10), " hundred ") + getWordForNumber((number % 100), " ");
		}
		return numberInWordsFormat;
	}

	public String getWordForNumber(long n, String ch) {
		String numberInWords = "";
		int x = (int) n;

		String one[] = { " ", " one", " two", " three", " four", " five", " six", " seven", " eight", " Nine", " ten", " eleven", " twelve", " thirteen", " fourteen", "fifteen", " sixteen",
				" seventeen", " eighteen", " nineteen" };
		String ten[] = { " ", " ", " twenty", " thirty", " forty", " fifty", " sixty", "seventy", " eighty", " ninety" };
		if (n > 19) {
			numberInWords += (ten[(x / 10)]) + " " + one[x % 10];
		} else {
			numberInWords += one[x];
		}
		if (n > 0)
			numberInWords += ch;
		return numberInWords;
	}

	public static int count(int[] res, int num) {
		int count=0;
		for(int i=0;i<res.length;i++)
			if(res[i]==num)
				count++;
		return count;
	}
}
