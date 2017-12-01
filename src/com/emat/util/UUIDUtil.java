package com.emat.util;

import java.util.UUID;

/**
 * UUID相关操作
 */

public class UUIDUtil {

	private UUIDUtil() {
	}

	/**
	 * 获得一个36位的UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
