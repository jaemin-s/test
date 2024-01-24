package com.castis.common.filter;



import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class SQLRequestWrapper extends HttpServletRequestWrapper {
	private Map<String, String[]> escapedParametersValuesMap = new HashMap<String, String[]>();

	public SQLRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String[] escapedParameterValues = escapedParametersValuesMap.get(name);
		String escapedParameterValue = null;
		if (escapedParameterValues != null) {
			escapedParameterValue = escapedParameterValues[0];
		} else {
			String parameterValue = super.getParameter(name);
			// SQL injection characters
			escapedParameterValue = StringUtils.replace(parameterValue, "'", "\'");
			escapedParametersValuesMap.put(name, new String[] { escapedParameterValue });
		}
		return escapedParameterValue;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] escapedParameterValues = escapedParametersValuesMap.get(name);
		if (escapedParameterValues == null) {
			String[] parametersValues = super.getParameterValues(name);
			if (parametersValues != null) {
				escapedParameterValues = new String[parametersValues.length];
				for (int i = 0; i < parametersValues.length; i++) {
					String parameterValue = parametersValues[i];
					String escapedParameterValue = "";
					// SQL injection characters
					escapedParameterValue = StringUtils.replace(parameterValue, "'", "\'");
					escapedParameterValues[i] = escapedParameterValue;
				}
			}
			escapedParametersValuesMap.put(name, escapedParameterValues);
		}
		return escapedParameterValues;
	}
}
