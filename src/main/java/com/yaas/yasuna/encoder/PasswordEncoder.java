package com.yaas.yasuna.encoder;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {

	public String encodePassword(String value) {

		String encryptedValue = DigestUtils.sha256Hex(value);

		return encryptedValue;
	}
}
