package com.castis.external.common.util;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DataTableObject {
	private int draw;

	private long recordsTotal;

	private long recordsFiltered;

	private BigDecimal revenue;
	private List<?> data;

	private String error;

	public String toString() {

		return String.format(
				"{draw:'%d', recordsTotal: '%s', recordsFiltered: '%s', data: '%s', error: '%s', revenue:'%s' }", draw,
				recordsTotal, recordsFiltered, data, error, revenue);
	}
}
