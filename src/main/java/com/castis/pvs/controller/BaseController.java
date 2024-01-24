package com.castis.pvs.controller;

import com.castis.external.common.util.CustomMessageSourceAccessor;
import com.castis.external.common.util.DataTableRequestArgs;
import com.castis.external.common.util.DateUtil;
import com.castis.pvs.dto.CommonSearchDTO;
import com.castis.pvs.exception.PopupException;
import com.castis.pvs.user.entity.CMSUserDetails;
import com.castis.pvs.util.Constants.UserType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Objects;

@Controller
public class BaseController {

	protected static final String ERROR_DB_NOT_FOUND = "error.dbNotFound";

	protected static final String ERROR_DB_CONSTRANT_VIOLENT = "error.constrant.violent";
	protected static final String EXCEPTIONS_PARAM_REQUIRED = "exceptions.param.required";

	protected static final String LOG_POPUP_ERROR = "{}: {}";

	protected static final String ERROR_GENERAL = "error.general";
	protected static final String ERROR_EMPTY_INPUT = "error.emptyInput";

	protected static final String ERROR_CREATE = "error.create";
	protected static final String RESULTCODE = "resultCode";

	protected static final String SUCCESS = "Success";
	protected static final String ACTION = "action";
	protected static final String EDIT = "edit";
	protected static final String ADD = "add";
	protected static final String QC = "qc";

	protected static final String CP_ID = "cpId";
	protected static final String EXTERNAL_ID = "externalId";
	protected static final String ASSET_TYPE = "assetType";
	protected static final String CONTENTS = "contents";
	protected static final String TITLE = "title";
	protected static final String SEARCH_TYPE = "searchType";
	protected static final String START_TIME = "startTime";
	protected static final String END_TIME = "endTime";
	protected static final String STAGE_CODE = "stageCode";
	protected static final String IS_NULL = "isNull";
	protected static final String IS_QC_REJECT = "isQcReject";
	protected static final String PROVIDER_ID = "providerId";

	protected static final String STAGE_CODE_LIST = "stageCodeList";
	protected static final String EXTERNAL_CODE_LIST = "externalCodeList";
	protected static final String ASSET_TYPE_LIST = "assetTypeList";
	protected static final String RENTAL_TYPE_LIST = "rentalTypeList";
	protected static final String PACKAGE_TYPE_LIST = "packageTypeList";
	protected static final String GENRE_LIST = "genreList";
	protected static final String RATING_CODE_LIST = "ratingCodeList";
	protected static final String IS_ADMIN = "isAdmin";
	protected static final String PROVIDER_LIST = "contentProviderList";
	protected static final String MBS_ACTION_LIST = "mbsActionList";
	protected static final String DEPLOY_STATUS_TYPE_LIST = "deployStatusTypeList";
	protected static final String ADS_STATUS_TYPE_LIST = "adsStatusTypeList";
	protected static final String PROMO_HISTORY_STATUS = "promoHistoryStatus";

	protected static final String PROCESS_OK = "0";
	protected static final String SYSTEM_FAIL = "100";
	protected static final String DUPLICATED_ID = "200";

	@Autowired
	protected CustomMessageSourceAccessor wmSource;

	@Autowired
	private LocaleResolver localeResolver;

	@ExceptionHandler(value = {PopupException.class})
	public ModelAndView generalExceptionHandler(Exception ex, Locale locale) {
		String errorMessage = ex.getMessage();
		if (StringUtils.isBlank(errorMessage)) {
			errorMessage = wmSource.getMessage(ERROR_EMPTY_INPUT);
		}
		ModelAndView modelAndView = new ModelAndView("common/exception/exception_popup");
		modelAndView.getModel().put("errorMessage", errorMessage);

		return modelAndView;
	}

	protected DataTableRequestArgs getDataTableRequestObject(HttpServletRequest request) {
		DataTableRequestArgs tableArgs = new DataTableRequestArgs();
		String orderCloumnNum = request.getParameter("order[0][column]");
		String orderDirection = "";
		String orderColumnName = "";
		String searchValue = request.getParameter("search[value]");
		if (BooleanUtils.toBoolean(request.getParameter(String.format("columns[%s][orderable]", orderCloumnNum)))) {
			orderColumnName = request.getParameter(String.format("columns[%s][data]", orderCloumnNum));
			orderDirection = request.getParameter("order[0][dir]");
		}
		tableArgs.setOrderColumnName(orderColumnName);
		tableArgs.setOrderDirection(orderDirection);
		tableArgs.setFirstResult(NumberUtils.toInt(request.getParameter("start"), 0));
		tableArgs.setFetchSize(NumberUtils.toInt(request.getParameter("length"), 10));
		tableArgs.setSearchValue(searchValue);
		tableArgs.setLanguage(getUserLocalLanguage(request));
		return tableArgs;
	}

	protected String createCustomTableIdx(String id, String tableName) {
		StringBuffer code = new StringBuffer();
		if (!StringUtils.isBlank(tableName) && tableName.length() > 3) {
			code = new StringBuffer(tableName.substring(0, 4).toUpperCase());
		} else if (!StringUtils.isBlank(tableName) && tableName.length() <= 3) {
			code = new StringBuffer(tableName.substring(0, 1).toUpperCase());
		}
		code.append("-").append(StringUtils.leftPad(id, 10, "0"));
		return code.toString();
	}

	protected Short[] getArrayShortFromRequestString(String requestString) {
		if (StringUtils.isBlank(requestString)) {
			return new Short[]{};
		}
		String[] split = requestString.split(",");
		Short[] stage = new Short[split.length];
		for (int i = 0; i < split.length; i++) {
			stage[i] = Short.parseShort(split[i]);
		}
		return stage;
	}

	protected String[] getArrayStringFromRequestString(String requestString) {
		if (StringUtils.isBlank(requestString)) {
			return new String[]{};
		}
		String[] split = requestString.split(",");
		String[] stage = new String[split.length];
		System.arraycopy(split, 0, stage, 0, split.length);
		return stage;
	}


	private void setFieldValueByType(Object targetObject, Field field, Class<?> typeField, Object fieldValue) throws IllegalAccessException {
		String fieldValueStr = String.valueOf(fieldValue);
		if ((Objects.equals(typeField, Integer.class) || Objects.equals(typeField, Integer.TYPE))
				&& NumberUtils.isNumber(fieldValueStr)) {
			field.set(targetObject, Integer.parseInt(fieldValueStr));
		} else if ((Objects.equals(typeField, Byte.class) || Objects.equals(typeField, Byte.TYPE))
				&& NumberUtils.isNumber(fieldValueStr)) {
			field.set(targetObject, Byte.parseByte(fieldValueStr));
		} else if (Objects.equals(typeField, Boolean.class) || Objects.equals(typeField, Boolean.TYPE)) {
			field.set(targetObject, BooleanUtils.toBoolean(fieldValueStr));
		} else if (Objects.equals(typeField, Character.class) || Objects.equals(typeField, Character.TYPE)) {
			field.set(targetObject, fieldValueStr.charAt(0));
		} else if ((Objects.equals(typeField, Double.class) || Objects.equals(typeField, Double.TYPE))
				&& NumberUtils.isNumber(fieldValueStr)) {
			field.set(targetObject, Double.parseDouble(fieldValueStr));
		} else if ((Objects.equals(typeField, Float.class) || Objects.equals(typeField, Float.TYPE))
				&& NumberUtils.isNumber(fieldValueStr)) {
			field.set(targetObject, Float.parseFloat(fieldValueStr));
		} else if ((Objects.equals(typeField, Long.class) || Objects.equals(typeField, Long.TYPE))
				&& NumberUtils.isNumber(fieldValueStr)) {
			field.set(targetObject, Long.parseLong(fieldValueStr));
		} else if ((Objects.equals(typeField, Short.class) || Objects.equals(typeField, Short.TYPE))
				&& NumberUtils.isNumber(fieldValueStr)) {
			field.set(targetObject, Short.parseShort(fieldValueStr));
		} else if (Objects.equals(typeField, String.class)) {
			field.set(targetObject, fieldValue);
		}
	}

	private boolean setDynamicValueByField(Object targetObject, String fieldName, Object fieldValue) {
		Field field;
		try {
			field = targetObject.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			field = null;
		}
		Class<?> superClass = targetObject.getClass().getSuperclass();
		while (field == null && superClass != null) {
			try {
				field = superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				superClass = superClass.getSuperclass();
			}
		}
		if (field == null) {
			return false;
		}
		field.setAccessible(true);

		try {
			setFieldValueByType(targetObject, field, field.getType(), fieldValue);
			return true;
		} catch (IllegalAccessException e) {
			return false;
		}
	}

	protected void getDataSearchDTO(CommonSearchDTO commonSearchDTO, HttpServletRequest request) {
		String searchType = request.getParameter(SEARCH_TYPE);
		String searchValue = request.getParameter(TITLE);
		String stageCodeStr = request.getParameter(STAGE_CODE);
		String startTime = request.getParameter(START_TIME);
		String endTime = request.getParameter(END_TIME);
		String requestStartTime = request.getParameter("requestStartTime");
		String requestEndTime = request.getParameter("requestEndTime");
		String isNull = request.getParameter(IS_NULL);
		String statusStr = request.getParameter("status");
		String exportResult = request.getParameter("exportResult");
		String importResult = request.getParameter("importResult");
		String providerId = request.getParameter(PROVIDER_ID);
		String masterSeriesId = request.getParameter("masterSeriesId");
		String seriesId = request.getParameter("seriesId");

		Short[] status = getArrayShortFromRequestString(statusStr);
		if (ArrayUtils.isNotEmpty(status)) {
			commonSearchDTO.setStatus(status);
		}

		Short[] stageCode = getArrayShortFromRequestString(stageCodeStr);
		if (ArrayUtils.isNotEmpty(stageCode)) {
			commonSearchDTO.setStageCode(stageCode);
		}

		if (isNull != null) {
			commonSearchDTO.setNull(Boolean.parseBoolean(isNull));
		}


		if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchValue)) {
			setDynamicValueByField(commonSearchDTO, searchType, searchValue);
		}

		if (StringUtils.isNotBlank(startTime)) {
			commonSearchDTO.setLicenseStartDateTime(DateUtil.convertStrToDate(startTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			commonSearchDTO.setLicenseEndDateTime(DateUtil.convertStrToDate(endTime));
		}

		if (StringUtils.isNotBlank(requestStartTime)) {
			commonSearchDTO.setRequestStartDateTime(DateUtil.convertStrToDate(requestStartTime));
		}
		if (StringUtils.isNotBlank(requestEndTime)) {
			commonSearchDTO.setRequestEndDateTime(DateUtil.convertStrToDate(requestEndTime));
		}

		if (StringUtils.isNotBlank(exportResult)) {
			commonSearchDTO.setExportResult(Short.valueOf(exportResult));
		}

		if (StringUtils.isNotBlank(importResult)) {
			commonSearchDTO.setImportResult(Short.valueOf(importResult));
		}

		if(StringUtils.isNotBlank(providerId)){
			commonSearchDTO.setCpId(providerId);
		}

		commonSearchDTO.setLanguage(getUserLocalLanguage(request));

		if (StringUtils.isNotBlank(masterSeriesId)) {
			try {
				commonSearchDTO.setMasterSeriesId(Long.parseLong(masterSeriesId));
			} catch (Exception e) {
				commonSearchDTO.setMasterSeriesId(null);
			}
		}

		if (StringUtils.isNotBlank(seriesId)) {
			try {
				commonSearchDTO.setSeriesId(Long.parseLong(seriesId));
			} catch (Exception e) {
				commonSearchDTO.setSeriesId(null);
			}
		}
	}

	protected String getUserLocalLanguage(HttpServletRequest request) {
		Locale local = localeResolver.resolveLocale(request);
		return StringUtils.defaultIfEmpty(local.getLanguage(), "th");
	}

	public static boolean isAdmin(CMSUserDetails userDetail) {
		for (GrantedAuthority authority : userDetail.getAuthorities()) {
			if (UserType.SYSADMIN.getValue().equalsIgnoreCase(authority.getAuthority())
					|| UserType.SO.getValue().equalsIgnoreCase(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
}
