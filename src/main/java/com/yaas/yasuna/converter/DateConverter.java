package com.yaas.yasuna.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {

	public Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}


}
