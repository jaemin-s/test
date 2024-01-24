package com.castis.pvs.api.service;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.mbs.CancelSvodResponse;
import com.castis.common.model.mbs.Purchase;
import com.castis.common.model.mbs.PurchaseSvodResponse;
import com.castis.common.util.CiDateUtil;
import com.castis.common.util.CiLogger;
import com.castis.common.util.CiStringUtil;
import com.castis.pvs.api.model.v2.PatchHappyCallUserByCiRequest;
import com.castis.pvs.api.dao.ApiDao;
import com.castis.pvs.api.model.v2.Info;
import com.castis.pvs.api.model.v2.request.*;
import com.castis.pvs.api.model.v2.response.*;
import com.castis.pvs.api.repository.MemberRepository;
import com.castis.pvs.connector.MBSConnector;
import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.dao.SiteInfoDao;
import com.castis.pvs.entity.SiteInfo;
import com.castis.pvs.member.dao.MemberDao;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.dto.MemberNotiDTO;
import com.castis.pvs.member.entity.BankRef;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.pay.dao.PayLogDao;
import com.castis.pvs.pay.entity.PayLog;
import com.castis.pvs.pay.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OpenApiService {

    @Value("${mbs.scheme:http}")
    private String mbs_scheme;

    @Value("${mbs.ip:127.0.0.1}")
    private String mbs_ip;

    @Value("${mbs.port:8080}")
    private int mbs_port;

    @Value("${mbs.product_list_url:/mbs/getProductList}")
    private String product_list_url;

    private final MemberDao memberDao;
    private final MBSConnector mbsConnector;

    private final ApiDao apiDao;

    private final PayLogDao payLogDao;

    private final PaymentService paymentService;


    @Value("${pvs.password.adult.default}")
    private String adultDefaultPassword;


    private final MemberRepository memberRepository;
    private final SiteInfoDao siteInfoDao;

    public OpenApiService(MemberDao memberDao, MBSConnector mbsConnector, SiteInfoDao siteInfoDao, ApiDao apiDao, PayLogDao payLogDao, MemberRepository memberRepository, PaymentService paymentService) {
        this.memberDao = memberDao;
        this.mbsConnector = mbsConnector;
        this.siteInfoDao = siteInfoDao;
        this.apiDao = apiDao;
        this.payLogDao = payLogDao;
        this.memberRepository = memberRepository;
        this.paymentService = paymentService;
    }

    public SiteInfo getSiteInfo(String soId) {
        return siteInfoDao.getSiteInfobySoId(soId);
    }

    @Transactional(rollbackFor = {Exception.class})
    public OpenApiResponse saveMember(MemberDTO memberDTO) throws Exception {
        OpenApiResponse response = new OpenApiResponse(HttpStatus.CREATED.value(), "회원가입에 성공하였습니다.");
        try {
            Member member = memberDao.getMemberbyMemberId(memberDTO.getMember_id());
            if (member != null) {
                throw new CiException(CiResultCode.code_134, CiResultCode.MSG.code_134);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        // adult_pass default 처리
        if (memberDTO.getAdult_pass() == null)
            memberDTO.setAdult_pass(adultDefaultPassword);

        Member member = new Member(memberDTO);
        member.setMember_id(memberDTO.getTel());    // 앞으로 회원가입 되는 계정은 아이디를 전화번호로 강제주입
        try {
            Long id = memberDao.saveMember(member);
            member.setId(id);
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());

        try {
            mbsConnector.noti_join_member(noti);
        } catch (Exception e) {
            CiLogger.error(e, "Bad GateWay");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_503);
        }

        return response;
    }

    @Transactional(rollbackFor = {Exception.class})
    public OpenApiResponse closeAccount(CloseAccountRequest apiRequest) throws Exception {
        // App app = null;
        OpenApiResponse response = new OpenApiResponse();

        Member member = memberDao.getMemberbyMemberIdAndCi(apiRequest.getMember_id(), apiRequest.getCi());
        if (member != null) {
            memberDao.deleteMember(member);
            MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());
            mbsConnector.noti_delete_member(noti);
            response.setResultCode(HttpStatus.OK.value());
            response.setResultMessage("성공");
        } else {
            response.setResultCode(HttpStatus.NOT_FOUND.value());
            response.setResultMessage("회원정보가 없습니다.");
        }
        return response;
    }

    public OpenApiResponse passwordcheck(PasswordcheckRequest request) throws Exception {
        OpenApiResponse response = new OpenApiResponse();
        try {
            Member member = apiDao.getMemberByPassword(request.getMember_id(), request.getCheck_password());
            if (member != null) {
                response.setResultCode(HttpStatus.OK.value());
                response.setResultMessage("비밀번호가 확인되었습니다.");
            } else {
                response.setResultCode(HttpStatus.UNAUTHORIZED.value());
                response.setResultMessage("권한이 없습니다.");

            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }
        return response;
    }

    public OpenApiResponse passwordChange(PasswordchangeRequest apiRequest) throws Exception, CiException {
        OpenApiResponse response = new OpenApiResponse();
        Member member = null;
        try {

            member = memberDao.getMemberbyMemberIdAndCi(apiRequest.getMember_id(), apiRequest.getCi());
            if (member == null) {
                //throw new CiException(CiResultCode.code_101, CiResultCode.MSG.code_101);
                response.setResultCode(HttpStatus.NOT_FOUND.value());
                response.setResultMessage("회원정보를 찾을 수 없습니다.");
            } else {
                if (Objects.equals(member.getPassword(), apiRequest.getPassword())) {
                    member.setPassword(apiRequest.getNew_password());
                    memberDao.saveOrUpdateMember(member);
                    response.setResultCode(HttpStatus.OK.value());
                    response.setResultMessage("비밀번호 변경에 성공하였습니다.");
                } else {
                    response.setResultCode(HttpStatus.UNAUTHORIZED.value());
                    response.setResultMessage("기존 비밀번호가 일치하지 않습니다.");
                    return response;
                }
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }
        return response;
    }

    public ProductListResponse getProductList(ProductListRequest apiRequest) {
        String url = String.format("%s://%s:%d", mbs_scheme, mbs_ip, mbs_port);
        WebClient webClient = WebClient.create(url);
        Mono<ProductListResponse> result = webClient.get()
                .uri(String.format("%s?pageNum=%d&pageSize=%d&packageType=package&productType=subscription&status=%d", product_list_url, apiRequest.getCurrentPage(), apiRequest.getPageSize(),apiRequest.getStatus()))
                .retrieve()
                .bodyToMono(ProductListResponse.class);
        ProductListResponse response = result.block();
        return response;
    }

    public HappyCallListResponse getHappyCallList(String soId , HappyCallListRequest apiRequest) {
        Page<Member> memberList = memberRepository.findAllBySoHappycallAuthAndCreateTimeBetween(Timestamp.valueOf(apiRequest.getSearch_startdate().atStartOfDay()),Timestamp.valueOf(apiRequest.getSearch_enddate().atStartOfDay()),1, soId , PageRequest.of(apiRequest.getCurrent_page()-1, apiRequest.getPer_page()));
        List<HappyCallMember> happyCallMembers = memberList.stream().map(HappyCallMember::new).collect(Collectors.toList());
        JoinListResult joinListResult = JoinListResult.builder().joinList(happyCallMembers)
                .page(PageInfo.builder().current_page(memberList.getPageable().getPageNumber()+1).per_page(memberList.getPageable().getPageSize()).last_page(memberList.getTotalPages()).total(memberList.getTotalElements()).build()).build();
        return HappyCallListResponse.builder().resultCode(HttpStatus.OK.value()).info(Info.builder().status(true).reason("가져오기 성공").build()).result(joinListResult).build();
    }

    public OpenApiResponse idDuplicateCheck(IdDuplicateCheckRequest apiRequest) throws Exception, CiException {
        OpenApiResponse response = new OpenApiResponse();
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(apiRequest.getMember_id());
            if (member == null) {
                response.setResultCode(HttpStatus.OK.value());
                response.setResultMessage(apiRequest.getMember_id() + "은(는) 사용가능한 아이디입니다.");
            } else {
                response.setResultCode(HttpStatus.CONFLICT.value());
                response.setResultMessage(apiRequest.getMember_id() + "은(는) 사용할 수 없는 아이디입니다.");
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    @Transactional
    public OpenApiResponse patchHappyCallUserByCi(PatchHappyCallUserByCiRequest apiRequest) throws CiException {
        OpenApiResponse response = new OpenApiResponse();
        CiLogger.info("patchHappyCallUserByCi : " + apiRequest.toString());
        Member member = memberRepository.findByMemberIdAndCi(apiRequest.getMember_id() , apiRequest.getCi()).orElseThrow(() -> new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501));
        if(member == null){
            CiLogger.info("not found member");
            response.setResultCode(HttpStatus.NOT_FOUND.value());
            response.setResultMessage("회원정보를 찾을 수 없습니다.");
        }else{
            member.setSo_happycall_auth(apiRequest.getHappycall_auth());
            //memberRepository.save(member);
            response.setResultCode(HttpStatus.OK.value());
            response.setResultMessage("성공");
        }
        return response;
    }

    public OpenApiResponse productPurchase(ProductPurchaseRequest request) throws Exception {
        // 1. 요청에 필요한 데이터를 추출합니다.
        String accountId = request.getMember_id();
        String offerId = request.getOffer_id().replaceAll(" ", "");
        PurchaseSvodResponse response = mbsConnector.purchaseSvod(offerId, accountId);
        OpenApiResponse openApiResponse;
        if (response.getResultCode() == CiResultCode.SUCCESS) {
            CiLogger.info("purchaseSvodResponse size :  %s", response.getPayLogs().size());
            List<Purchase> purchaseList = response.getPayLogs();
            for (Purchase purchase : purchaseList) {
                PayLog payLog = makePayLog(purchase);
                payLogDao.save(payLog);
            }
            openApiResponse = new OpenApiResponse(HttpStatus.OK.value(), "");
        } else {
            openApiResponse = new OpenApiResponse(response.getResultCode(), response.getResultMessage());
        }
        return openApiResponse; //new CiResponse(response.getResultCode(),"");
    }

    @Transactional
    public OpenApiResponse productPurchaseCancel(ProductPurchaseRequest request) throws Exception {
        // 1. 요청에 필요한 데이터를 추출합니다.
        String accountId = request.getMember_id();
        String offerId = request.getOffer_id().replaceAll(" ", "");
        CancelSvodResponse response = mbsConnector.cancelSvod(offerId , accountId);
        OpenApiResponse openApiResponse;
        if (response.getResultCode() == CiResultCode.SUCCESS) {
            String[] billingIds = response.getBillingIds();
            paymentService.cancel_purchase_subscription(billingIds);
            openApiResponse = new OpenApiResponse(HttpStatus.OK.value(), "성공");
        } else {
            openApiResponse = new OpenApiResponse(response.getResultCode(), response.getResultMessage());
        }
        return openApiResponse; //new CiResponse(response.getResultCode(),"");
    }

    public PayLog makePayLog(Purchase response) {
        PayLog payLog = new PayLog();
        payLog.setPurchase_billing_id(response.getBilling_id());
        payLog.setPurchase_type(response.getType());
        payLog.setDevice_type(response.getDevice_type());
        payLog.setWifi_mac(response.getWifi_mac());
        payLog.setTel(response.getTel());
        payLog.setAccount_id(response.getAccount_id());
        payLog.setAccount_type(PVSConstants.ACCOUNT.TYPE.MEMBER);

        payLog.setProduct_id(response.getProduct_id());
        payLog.setProduct_title(response.getTitle());
        payLog.setStatus(PVSConstants.Pay.STATUS.COMPLETE);
        payLog.setPrice(response.getPrice());
        if (!CiStringUtil.is_not_Integer(response.getPrice_point()))
            payLog.setPrice_point(response.getPrice_point());
        payLog.setApp_code(response.getApp_code());
        payLog.setModel(response.getModel());
        payLog.setPay_sign_date(CiDateUtil.to_string(new Date(), "yyyy-MM-dd"));
        return payLog;
    }

}
