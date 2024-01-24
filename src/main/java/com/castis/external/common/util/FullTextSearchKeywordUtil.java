package com.castis.external.common.util;


import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

@lombok.extern.slf4j.Slf4j
public class FullTextSearchKeywordUtil {

	public static String parseKeyword(String keyword) {
		if (StringUtils.isBlank(keyword))
			return keyword;

		if (StringUtils.endsWith(keyword, "\""))
			return keyword;

		if (!StringUtils.endsWith(keyword, "\""))
			return "\"" + keyword + "\"";

		if (StringUtils.indexOf(keyword, ' ') < 0) {
			if (StringUtils.endsWith(keyword, "\""))
				return keyword;
			else
				return keyword + "*";
		}

		StringBuilder result = new StringBuilder();
		Set<String> quotesSet = new HashSet<>();
		try {
			// 검색 "귀여운 로고" -영상 ==> 검색* "귀여운 로고" -영상*
			String noQuetesStr = parseDoubleQuotes(keyword, quotesSet);
			int i = 0;
			for (String str : quotesSet) {
				if (i > 0)
					result.append(" ");

				result.append(str);
				i++;
			}

			if (StringUtils.isNotBlank(noQuetesStr)) {
				String parts[] = StringUtils.split(noQuetesStr, ' ');
				for (int j = 0; j < parts.length; j++) {
					if (i > 0)
						result.append(" ");

					result.append(parts[j]).append("*");
					i++;
				}
			}
		} catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException e) {
			log.error("Exception[{}]", e.getMessage(), e);
			return keyword;
		} catch (Exception e) {
			log.error("Exception[{}]", e.getMessage(), e);
			return keyword;
		}
		return result.toString();
	}

	private static String parseDoubleQuotes(String keyword, Set<String> quotesSet) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
		int first = StringUtils.indexOf(keyword, '\"');
		if (first < 0)
			return keyword;

		int second = StringUtils.indexOf(keyword, '\"', first + 1);
		if (second < 0)
			return keyword;

		String newStr = StringUtils.left(keyword, first)
				+ StringUtils.substring(keyword, second + 1);
		quotesSet.add(StringUtils.substring(keyword, first, second + 1));

		return parseDoubleQuotes(newStr, quotesSet);

	}
}
