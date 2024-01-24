package com.castis.external.common.util;

import lombok.Data;

@Data
public class DataTableRequestArgs {
	private int fetchSize;
	private int firstResult;
	private String orderDirection;
	private String orderColumnName;
	private String searchValue;
	private String language;
}
