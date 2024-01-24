package com.castis.pvs.service;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.pvs.dao.TransactionInfoDao;
import com.castis.pvs.entity.TransactionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service("homeService")
public class HomeService {

	@Autowired
	private TransactionInfoDao transactionInfoDao;
	
	public ModelMap getMobileRequestInfo(String urlCode, ModelMap model) throws Exception {
		if(urlCode!=null && urlCode.isEmpty()==false) {
			TransactionInfo transactionInfo = transactionInfoDao.selectOne(new ModelMap("url_code", urlCode));
			if(transactionInfo==null) {throw new CiException(CiResultCode.BAD_REQUEST,"링크 정보가 없습니다.");}
			
			model.addAttribute("appCode", transactionInfo.getApp_code());
			model.addAttribute("appName", transactionInfo.getApp_name());
			model.addAttribute("wifiMac", transactionInfo.getWifi_mac());
			model.addAttribute("urlCode", urlCode);
			
			return model;
		} else {
			throw new CiException(CiResultCode.BAD_REQUEST,"링크 정보가 없습니다.");
		}
	}
}
