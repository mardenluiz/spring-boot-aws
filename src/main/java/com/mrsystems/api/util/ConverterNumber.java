package com.mrsystems.api.util;

public class ConverterNumber {

	public static Double convertToDouble(String strNumber) {

		if (strNumber == null)
			return 0.0;
		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number))
			return Double.parseDouble(number);

		return 0.0;
	}

	public static boolean isNumeric(String strNumber) {
		if (strNumber == null)
			return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]");
	}

}
