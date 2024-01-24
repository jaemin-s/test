package com.castis.pvs.pay.model;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.util.CiDateUtil;
import com.castis.common.util.CiStringUtil;
import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.entity.TransactionInfo;
import com.castis.pvs.pay.entity.PayLog;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;

public class PurchaseRequest  extends CiRequest {

    public PurchaseRequest(HttpServletRequest httpRequest, String requestBody) throws Exception {
        super(httpRequest, requestBody);
        parse();
    }

    @Override
    public void validate() throws Exception{

        if(CiStringUtil.is_empty(getStringValue("billing_id"))) {throw new CiException(CiResultCode.BAD_REQUEST,"billing_id가 유효하지 않습니다.");}

        if(CiStringUtil.is_empty(getStringValue("device_type"))) {throw new CiException(CiResultCode.BAD_REQUEST,"device_type이 유효하지 않습니다.");}

        if(CiStringUtil.is_empty(getStringValue("wifi_mac"))) {throw new CiException(CiResultCode.BAD_REQUEST,"wifi_mac가 유효하지 않습니다.");}

        if(CiStringUtil.is_empty(getStringValue("type"))) {throw new CiException(CiResultCode.BAD_REQUEST,"type이 유효하지 않습니다.");}

        if(CiStringUtil.is_empty(getStringValue("product_id"))) {throw new CiException(CiResultCode.BAD_REQUEST,"product_id가 유효하지 않습니다.");}

        if(CiStringUtil.is_empty(getStringValue("title"))) {throw new CiException(CiResultCode.BAD_REQUEST,"title이 유효하지 않습니다.");}

        if(CiStringUtil.is_not_Integer(getStringValue("price"),0)) {throw new CiException(CiResultCode.BAD_REQUEST,"price가 유효하지 않습니다.");}

        if(CiStringUtil.is_empty(getStringValue("app_code"))) {throw new CiException(CiResultCode.BAD_REQUEST,"app_code가 유효하지 않습니다.");}

        if(PVSConstants.Pay.TYPE.SIN.equalsIgnoreCase(getStringValue("type"))
                || PVSConstants.Pay.TYPE.SIN_CPX.equalsIgnoreCase(getStringValue("type"))){
            if(CiStringUtil.is_empty(getStringValue("period"))) {throw new CiException(CiResultCode.BAD_REQUEST,"period가 유효하지 않습니다.");}
        }

    }

    public PayLog makePayLog() {
        PayLog payLog = new PayLog();
        payLog.setPurchase_billing_id(getStringValue("billing_id"));
        payLog.setPurchase_type(getStringValue("type"));
        payLog.setDevice_type(getStringValue("device_type"));
        payLog.setWifi_mac(getStringValue("wifi_mac"));

        payLog.setAccount_id(getStringValue("account_id"));
        payLog.setAccount_type(PVSConstants.ACCOUNT.TYPE.MEMBER);

        payLog.setProduct_id(getStringValue("product_id"));
        payLog.setProduct_title(getStringValue("title"));
        payLog.setStatus(PVSConstants.Pay.STATUS.COMPLETE);
        payLog.setPrice(Long.parseLong(getStringValue("price")));
        if(!CiStringUtil.is_not_Integer(getStringValue("price_point"),0))
            payLog.setPrice_point(Long.parseLong(getStringValue("price_point")));
        payLog.setApp_code(getStringValue("app_code"));
        payLog.setModel(getStringValue("model"));
        payLog.setPay_sign_date(CiDateUtil.to_string(new Date(),"yyyy-MM-dd"));
        return payLog;
    }

    @Override
    public CiResponse makeCiResponse() {
        return super.makeCiResponse()
                .addAttribute("transaction_id", getParameter("transaction_id"))
                .addAttribute("billing_id", getStringValue("billing_id"))
                .addAttribute("account_id", getStringValue("account_id"))
                ;
    }

    @Override
    public CiResponse makeCiResponse(int code)  {
        return super.makeCiResponse(code)
                .addAttribute("transaction_id", getParameter("transaction_id"))
                .addAttribute("billing_id", getStringValue("billing_id"))
                .addAttribute("account_id", getStringValue("account_id"))
                ;
    }

    @Override
    public CiResponse makeCiResponse(int code, String message)  {
        return super.makeCiResponse(code, message)
                .addAttribute("transaction_id", getParameter("transaction_id"))
                .addAttribute("billing_id", getStringValue("billing_id"))
                .addAttribute("account_id", getStringValue("account_id"))
                ;
    }

    @Override
    public CiResponse makeCiResponse(CiResponse ciResponse) {
        return super.makeCiResponse(ciResponse)
                .addAttribute("transaction_id", getParameter("transaction_id"))
                .addAttribute("billing_id", getStringValue("billing_id"))
                .addAttribute("account_id", getStringValue("account_id"))
                ;
    }


    public TransactionInfo makeTransactionInfo() {

        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setUrl_code(createSaltStr());
        transactionInfo.setTransaction_id(getStringValue("billing_id"));
        transactionInfo.setType(getStringValue("type"));
        transactionInfo.setWifi_mac(getStringValue("wifi_mac"));
        transactionInfo.setStatus(PVSConstants.TRANSACTIONINFO.STATUS.CREATE);

        return transactionInfo;
    }


    public static String createSaltStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }



}
