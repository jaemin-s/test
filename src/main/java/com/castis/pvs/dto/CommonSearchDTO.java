package com.castis.pvs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommonSearchDTO {
	protected Long id;
	protected String cpId;
	protected String title; 
	protected String language;
	protected Short[] stageCode;
	protected Date licenseStartDateTime;
	protected Date licenseEndDateTime;
	protected Date requestStartDateTime;
	protected Date requestEndDateTime;
	protected String menu;
	protected boolean isNull;
	protected Short type;
	protected String bannerPositionId;
	protected String brand;
	protected String serviceName;
	protected String serviceId;
	protected Short[] status;
	protected Short exportResult;
	protected Short importResult;
	protected Long productId;
	protected Long offerId;
	protected Long seriesId;
	protected Long masterSeriesId;

	public CommonSearchDTO() {

	}

}
