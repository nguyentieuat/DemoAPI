package com.example.demoapi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
	public static LocalDateTime convertStringTimeToLocalTime(String stringTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(stringTime, formatter);
	}

	public static String convertLocalTimeToString(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return time.format(formatter);
	}

	public static int converStringtoInt(String numStr) {
		try {
			return Integer.parseInt(numStr);
		} catch (Exception e) {
			return 0;
		}
	}

	public static float converStringtoFloat(String numStr) {
		try {
			return Float.parseFloat(numStr);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getRank(float sumTotalSpent) {
		int result = 0;
		if (sumTotalSpent >= Constant.MOCK_GOLD && sumTotalSpent < Constant.MOCK_PLATINUM) {
			result = 1;
		} else if (sumTotalSpent >= Constant.MOCK_PLATINUM && sumTotalSpent < Constant.MOCK_DIAMOND) {
			result = 2;
		} else if (sumTotalSpent >= Constant.MOCK_DIAMOND) {
			result = 3;
		} else {
			result = 0;
		}
		return result;
	}
}
