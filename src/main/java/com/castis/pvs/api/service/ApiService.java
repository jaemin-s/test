package com.castis.pvs.api.service;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.castis.common.model.CiRequest;
import com.castis.common.model.CiResponse;
import com.castis.common.model.mbs.CancelSvodResponse;
import com.castis.common.model.mbs.Purchase;
import com.castis.common.model.mbs.PurchaseSvodResponse;
import com.castis.common.util.CiDateUtil;
import com.castis.common.util.CiLogger;
import com.castis.common.util.CiStringUtil;
import com.castis.pvs.api.dao.ApiDao;
import com.castis.pvs.api.dto.SignUpDTO;
import com.castis.pvs.api.dto.SignUpSoDTO;
import com.castis.pvs.api.model.*;
import com.castis.pvs.connector.IMSConnector;
import com.castis.pvs.connector.MBSConnector;
import com.castis.pvs.connector.SoConnector;
import com.castis.pvs.constants.PVSConstants;
import com.castis.pvs.dao.AppDao;
import com.castis.pvs.dao.SiteInfoDao;
import com.castis.pvs.dao.TransactionInfoDao;
import com.castis.pvs.entity.SiteInfo;
import com.castis.pvs.entity.TransactionInfo;
import com.castis.pvs.member.dao.MemberDao;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.dto.MemberNotiDTO;
import com.castis.pvs.member.dto.NewDeviceDTO;
import com.castis.pvs.member.entity.BankRef;
import com.castis.pvs.member.entity.Member;
import com.castis.pvs.member.entity.MemberInactive;
import com.castis.pvs.member.entity.NewDevice;
import com.castis.pvs.pay.dao.PayLogDao;
import com.castis.pvs.pay.entity.PayLog;
import com.castis.pvs.pay.model.PurchaseRequest;
import com.castis.pvs.pay.service.PaymentService;
import com.castis.pvs.security.PasswordEncoding;
import com.castis.pvs.util.EmailValidator;

import org.apache.hc.client5.http.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ApiService {

    @Autowired
    private ApiDao apiDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private AppDao appDao;

    @Autowired
    private SiteInfoDao siteInfoDao;

    @Autowired
    private PayLogDao payLogDao;

    @Autowired
    private PasswordEncoding password;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MBSConnector mbsConnector;

    @Autowired
    private IMSConnector imsConnector;

    @Autowired
    private SoConnector soConnector;

    @Value("${pvs.password.adult.default}")
    private String adultDefaultPassword;

    @Autowired
    private TransactionInfoDao transactionInfoDao;

    /**
     * passwordCheckRequestV2
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public PasswordCheckResponse passwordCheckRequestV2(PasswordCheckRequest request) throws Exception {
        // CiResponse response = new CiResponse();
        PasswordCheckResponse response = new PasswordCheckResponse();
        response.setTransaction_id(request.getTransaction_id());
        if (PVSConstants.PASSCHECKTYPE.LOGIN.equalsIgnoreCase(request.getCheckType())) {
            try {
                Member member = apiDao.getMemberByPassword(request.getAccount_id(), request.getPassword());
                if (member != null) {
                    response.setActive(true);
                    response.setResult_code(CiResultCode.SUCCESS);
                } else {
                    MemberInactive memberInactive = null;
                    memberInactive = apiDao.getMemberInactiveByPassword(request.getAccount_id(), request.getPassword());

                    if (memberInactive != null) {
                        response.setResult_code(CiResultCode.SUCCESS);
                        response.setActive(false);

                        Member tmpMember = new Member(new MemberDTO(memberInactive));
                        Long id = memberDao.saveMember(tmpMember);
                        tmpMember.setId(id);

                        memberDao.deleteMemberInactive(memberInactive);
                    } else {
                        response.setResult_code(CiResultCode.code_101);
                        response.setResult_msg(CiResultCode.MSG.code_101);
                    }
                }
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        } else if (PVSConstants.PASSCHECKTYPE.ADULT.equalsIgnoreCase(request.getCheckType())) {
            try {
                Member member = memberDao.getMemberbyMemberId(request.getAccount_id());

                if (Objects.nonNull(member.getAdult_pass()) && member.getAdult_pass().equals(request.getPassword())) {
                    response.setResult_code(CiResultCode.SUCCESS);
                } else {
                    response.setResult_code(CiResultCode.code_101);
                    response.setResult_msg(CiResultCode.MSG.code_101);
                }

            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        } else if (PVSConstants.PASSCHECKTYPE.ADULT_STB.equalsIgnoreCase(request.getCheckType())) {
            try {
                Member member = apiDao.getMemberbySTB(request.getWifi_mac());
                if (Objects.nonNull(member.getAdult_pass()) && member.getAdult_pass().equals(request.getPassword())) {
                    response.setResult_code(CiResultCode.SUCCESS);
                } else {
                    response.setResult_code(CiResultCode.code_101);
                    response.setResult_msg(CiResultCode.MSG.code_101);
                }
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        }
        return response;
    }

    /**
     * unregistMember
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = { Exception.class })
    public CiResponse unregistMember(ApiRequest request) throws Exception {
        CiResponse response = new CiResponse();
        response.addAttribute("transaction_id", request.getTransaction_id());

        try {
            Member member = memberDao.getMemberbyMemberId(request.getAccount_id());

            if (member != null) {
                memberDao.deleteMember(member);
                paymentService.memberUnregister(member);
                response.addAttribute("result_code", CiResultCode.SUCCESS);
            } else {
                response.addAttribute("result_code", CiResultCode.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    /**
     * adultCheckStb
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public CiResponse adultCheckStb(ApiRequest request) throws Exception {
        CiResponse response = new CiResponse();
        response.addAttribute("transaction_id", request.getTransaction_id());

        Member member = apiDao.getMemberbySTB(request.getWifi_mac());
        if (member != null) {
            if (member.isAdult_cert()) {
                response.addAttribute("adult_certi_yn", "Y");
            } else {
                response.addAttribute("adult_certi_yn", "N");
            }
        } else {
            response.addAttribute("adult_certi_yn", "N");
        }
        response.addAttribute("result_code", CiResultCode.SUCCESS);
        return response;
    }

    /**
     * passwordSet
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public CiResponse passwordSet(ApiRequest request) throws Exception {
        CiResponse response = new CiResponse();
        response.addAttribute("transaction_id", request.getTransaction_id());
        try {
            Member member = apiDao.getMemberbySTB(request.getWifi_mac());
            if (member != null) {
                if (PVSConstants.PASSSETTYPE.INIT.equalsIgnoreCase(request.getType())) {
                    CiLogger.info("passwordSet INIT : " + request.getNew_password());
                    member.setAdult_cert(true);
                    member.setAdult_pass(request.getNew_password());
                    String result = memberDao.saveOrUpdateMember(member);
                    if (result.equalsIgnoreCase("success")) {
                        response.addAttribute("result_code", CiResultCode.SUCCESS);
                    } else {
                        response.addAttribute("result_code", CiResultCode.INTERNAL_SERVER_ERROR);
                        response.addAttribute("result_msg", CiResultCode.MSG.code_999);
                    }
                } else if (PVSConstants.PASSSETTYPE.CHAN.equalsIgnoreCase(request.getType())) {
                    if (Objects.nonNull(member.getAdult_pass())
                            && member.getAdult_pass().equals(request.getPassword())) {
                        member.setAdult_pass(request.getNew_password());
                        String result = memberDao.saveOrUpdateMember(member);
                        if (result.equalsIgnoreCase("success")) {
                            response.addAttribute("result_code", CiResultCode.SUCCESS);
                        } else {
                            response.addAttribute("result_code", CiResultCode.INTERNAL_SERVER_ERROR);
                            response.addAttribute("result_msg", CiResultCode.MSG.code_999);
                        }
                    } else {
                        response.addAttribute("result_code", CiResultCode.code_101);
                        response.addAttribute("result_msg", CiResultCode.MSG.code_101);
                    }
                }
            } else {
                CiLogger.info("member is null");
                response.addAttribute("result_code", CiResultCode.code_101);
                response.addAttribute("result_msg", CiResultCode.MSG.code_101);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    /**
     * inactiveMember
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public CiResponse inactiveMember(ApiRequest request) throws Exception {
        CiResponse response = new CiResponse();
        response.addAttribute("transaction_id", request.getTransaction_id());

        if (request.getType().equalsIgnoreCase(PVSConstants.MEMBERACTIVETYPE.INACTIVE)) {
            try {
                Member member = memberDao.getMemberbyMemberId(request.getAccount_id());
                if (member != null) {
                    MemberDTO memberDTO = new MemberDTO(member);
                    MemberInactive inactive = new MemberInactive(memberDTO);
                    memberDao.saveMemberInactive(inactive);

                    memberDao.deleteMember(member);
                    // memberDao.deleteCardByCardId(member.getCard().getCard_id());

                    response.addAttribute("result_code", CiResultCode.SUCCESS);
                } else {
                    response.addAttribute("result_code", CiResultCode.code_101);
                }
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        } else if (request.getType().equalsIgnoreCase(PVSConstants.MEMBERACTIVETYPE.ACTIVE)) {
            try {
                MemberInactive inactive = memberDao.getMemberInactivebyMemberId(request.getAccount_id());
                if (inactive != null) {
                    Member newMember = new Member(new MemberDTO(inactive));
                    Long id = memberDao.saveMember(newMember);
                    newMember.setId(id);
                    memberDao.deleteMemberInactive(inactive);

                    response.addAttribute("result_code", CiResultCode.SUCCESS);
                } else {
                    response.addAttribute("result_code", CiResultCode.code_101);
                }
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        }

        return response;
    }

    /**
     * 
     * @param memberDTO
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = { Exception.class })
    public UserSignUpResponse saveMember(MemberDTO memberDTO) throws Exception {
        // CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        UserSignUpResponse response = new UserSignUpResponse(CiResultCode.SUCCESS, "");
        try {
            Member member = memberDao.getMemberbyMemberId(memberDTO.getMember_id());
            if (member != null) {
                throw new CiException(CiResultCode.code_134, CiResultCode.MSG.code_134);
            }

            List<Member> memberList = memberDao.getMemberbyTel(memberDTO.getTel());
            if (memberList != null && memberList.isEmpty() == false) {
                // HCN 요구사항 : phone 중복 X
                throw new CiException(CiResultCode.code_137, CiResultCode.MSG.code_137);
            }
        } catch (CiException e) {
            throw e;
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        memberDTO.setPassword(password.encode(memberDTO.getPassword()));
        Member member = new Member(memberDTO);
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

        if (member.getMso_name().equalsIgnoreCase("HCN")) {
            try {
                SignUpDTO signUpDTO = new SignUpDTO(memberDTO);
                SignUpResponse signUpResponse = imsConnector.sign_up(signUpDTO, "Hcnottrestfulapiservice20221111s");

                if (signUpResponse.getCode() == 1) {
                    throw new CiException(CiResultCode.code_134, CiResultCode.MSG.code_134);
                } else if (signUpResponse.getCode() == 2) {
                    throw new CiException(CiResultCode.code_198, CiResultCode.MSG.code_198);
                } else if (signUpResponse.getCode() == 3) {
                    throw new CiException(CiResultCode.code_199, CiResultCode.MSG.code_199);
                } else if (signUpResponse.getCode() == -1) {
                    throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_503);
                }
            } catch (Exception e) {
                CiLogger.error(e, "Bad GateWay");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_503);
            }
        }

        return response;
    }

    /**
     * saveMemberV2
     * 
     * @param memberDTO
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = { Exception.class })
    public UserSignUpResponse saveMemberV2(MemberDTO memberDTO) throws Exception {
        UserSignUpResponse response = new UserSignUpResponse(CiResultCode.SUCCESS, "");
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

        // Version 2 does not encrypt passwords.
        Member member = new Member(memberDTO);
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

    /**
     * so 별 회원 인증 후 저장 한다.
     * saveMemberV3
     * 
     * @param memberDTO
     * @param siteInfo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = { Exception.class })
    public CiResponse saveMemberV3(MemberDTO memberDTO, SiteInfo siteInfo) throws Exception {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, " ");
        // 기존 멤머 아이디가 있는지 확인
        try {
            Member member = memberDao.getMemberbyMemberId(memberDTO.getMember_id());
            if (member != null) {// 이미 등록되어 있는 ID입니다.
                CiLogger.info("오초이스에 이미 등록된 (%s) (%s)회원입니다.", memberDTO.getMember_id(), memberDTO.getTel());
                throw new CiException(CiResultCode.code_134, " 오초이스에 이미 등록된 (%s) (%s)회원입니다.", memberDTO.getMember_id(),
                        memberDTO.getTel());
            }
            // 전화번호중복 확인
            List<Member> memberList = memberDao.getMemberbyTel(memberDTO.getTel());
            if (memberList != null && memberList.isEmpty() == false) {
                // 이미 등록되어 있는 전화번호 입니다.
                throw new CiException(CiResultCode.code_137, "오초이스에 이미 등록된 전화번호(%s)입니다.", memberDTO.getTel());
            }

        } catch (CiException e) {
            throw e;
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            // DB 접근오류가 발생하였으니 다시 수행하여 주시기 바랍니다.
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        // 각 so별 케이블 tv 가입 회원 인증서버 구축 후 인증 처리
        if (Objects.nonNull(siteInfo) && siteInfo.getSign_up_use()) {
            SignUpResponse signUpResponse = null;
            try {
                // HCN 방식 - 데이터 형태가 다름
                if (siteInfo.getSo_id().equals("4012")) {
                    SignUpDTO signUpDTO = new SignUpDTO(memberDTO);
                    signUpResponse = imsConnector.sign_up_v3(signUpDTO, siteInfo);
                } else { // 타 so 는 연동 방식이 정해져 있음
                    SignUpSoDTO signUpSoDTO = new SignUpSoDTO(memberDTO);
                    signUpResponse = soConnector.sign_up_to_so(signUpSoDTO, siteInfo);
                }

                if (signUpResponse.getCode() == 0) { // SO 회원인증- 각SO 가입자이면 pvs에 저장한다.

                    // adult_pass default 추가
                    if (memberDTO.getAdult_pass() == null)
                        memberDTO.setAdult_pass(adultDefaultPassword);
                    // 해피콜 대상자 아님 - 정상 가입자 변경
                    memberDTO.setSo_happycall_auth(0);// 해피콜 데이터 처리
                    memberDTO.setMso_name(siteInfo.getMso_name());

                    // 회원등록
                    Member member = new Member(memberDTO);
                    try {
                        Long id = memberDao.saveMember(member);
                        member.setId(id);
                    } catch (Exception e) {
                        CiLogger.error(e, "DB General Error.");
                        throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
                    }

                    // 회원 mbs에 noti
                    MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());
                    try {
                        mbsConnector.noti_join_member(noti);
                    } catch (Exception e) {
                        CiLogger.error(e, "MBS Failed to Connect");
                        // MBS 서비스 연동에 실패 했습니다.
                        throw new CiException(CiResultCode.FAIL_CONNECT_MBS_SERVER, CiResultCode.MSG.code_518);
                    }

                    CiLogger.info("(%s) 케이블TV 가입자입니다.(%s)회원 저장합니다.", siteInfo.getMso_name(), memberDTO.getTel());
                    response = new CiResponse(CiResultCode.SUCCESS,
                            siteInfo.getMso_name() + "케이블TV 가입자입니다. " + memberDTO.getTel() + "회원 저장합니다.")
                            .addAttribute("happycall", 0);
                    ;

                }

            } catch (ConnectTimeoutException e) {// so 인증서버 접속 불량 - 네트웍 불안정
                // throw new CiException(CiResultCode.DB_GENERAL_ERROR,
                // CiResultCode.MSG.code_503);
                CiLogger.error("External System(%s) Connection Failure(%s)", siteInfo.getSign_up_url(), e.getMessage());
                throw new CiException(CiResultCode.FAIL_CONNECT_SO_SERVER, "External System Connection Failure(%s)",
                        siteInfo.getSign_up_url(), e.getMessage());
            }

            if (signUpResponse.getCode() == 1) {// 이미등록된 아이디 입니다.
                CiLogger.info("(%s)케이블TV 등록된 (%s) 회원입니다.", siteInfo.getMso_name(), memberDTO.getTel());
                throw new CiException(CiResultCode.code_134, CiResultCode.MSG.code_134);
            } else if (signUpResponse.getCode() == 2) {// 요청 정보가 없거나 유효하지 않습니다.
                throw new CiException(CiResultCode.code_198, CiResultCode.MSG.code_198);

            } else if (signUpResponse.getCode() == 3) {// 케이블 TV 미가입자입니다.
                // 미가입자 처리 - 해피콜 대상으로 처리 하고 정상값 리턴 에러 처리 아님
                // throw new CiException(CiResultCode.code_199, CiResultCode.MSG.code_199);
                // adult_pass default 추가
                if (memberDTO.getAdult_pass() == null)
                    memberDTO.setAdult_pass(adultDefaultPassword);
                // 해피콜 대상자 아님 - 정상 가입자 변경
                memberDTO.setSo_happycall_auth(1);
                memberDTO.setMso_name(siteInfo.getMso_name());

                // 회원등록
                Member member = new Member(memberDTO);
                try {
                    Long id = memberDao.saveMember(member);
                    member.setId(id);
                } catch (Exception e) {
                    CiLogger.error(e, "DB General Error.");
                    throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
                }

                // 회원 mbs에 noti
                MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());
                try {
                    mbsConnector.noti_join_member(noti);
                } catch (Exception e) {
                    CiLogger.error(e, "MBS Failed to Connect");
                    // MBS 서비스 연동에 실패 했습니다.
                    throw new CiException(CiResultCode.FAIL_CONNECT_MBS_SERVER, CiResultCode.MSG.code_518);
                }
                CiLogger.info("(%s) 케이블TV 미가입자입니다.(%s)회원 해피콜 대상자로 저장합니다.", siteInfo.getMso_name(), memberDTO.getTel());
                response = new CiResponse(CiResultCode.SUCCESS,
                        siteInfo.getMso_name() + " 케이블 TV 미가입자입니다." + memberDTO.getTel() + "해피콜 대상자로 저장합니다.")
                        .addAttribute("happycall", 1);

            } else if (signUpResponse.getCode() == -1) {// SO 인증서버 접속이 원할 하지 않습니다.
                // throw new CiException(CiResultCode.DB_GENERAL_ERROR,
                // CiResultCode.MSG.code_503);
                CiLogger.error("External System(%s) Connection Failure(%s)", siteInfo.getIp(),
                        siteInfo.getSign_up_url());
                throw new CiException(CiResultCode.FAIL_CONNECT_SO_SERVER, CiResultCode.MSG.code_519);

            } else if (signUpResponse.getCode() == -2) {

                if (memberDTO.getAdult_pass() == null)
                    memberDTO.setAdult_pass(adultDefaultPassword);
                // 해피콜 대상자 아님 - 정상 가입자 변경
                memberDTO.setSo_happycall_auth(1);// 해피콜 대상
                memberDTO.setMso_name(siteInfo.getMso_name());

                // 회원등록
                Member member = new Member(memberDTO);
                try {
                    Long id = memberDao.saveMember(member);
                    member.setId(id);
                } catch (Exception e) {
                    CiLogger.error(e, "DB General Error.");
                    throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
                }

                // 회원 mbs에 noti
                MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());
                try {
                    mbsConnector.noti_join_member(noti);
                } catch (Exception e) {
                    CiLogger.error(e, "MBS Failed to Connect");
                    // MBS 서비스 연동에 실패 했습니다.
                    throw new CiException(CiResultCode.FAIL_CONNECT_MBS_SERVER, CiResultCode.MSG.code_518);
                }

                CiLogger.info("회원인증을 사용하지 않는 SO(%s) 입니다. 단 해피콜 대상 가입자로 처리 합니다. ", siteInfo.getIp());
                // throw new CiException(CiResultCode.CHECK_SO_API_URL,
                // CiResultCode.MSG.code_520);
                response = new CiResponse(CiResultCode.SUCCESS,
                        "회원인증을 사용하지 않는 SO(" + siteInfo.getIp() + ")입니다. 해피콜 대상 가입자로 처리 합니다. ")
                        .addAttribute("happycall", 1);

            }

            // //HCN 회원 상품 연동 스펙 향후 정의에 따라 수정
            // if(Objects.nonNull(signUpResponse) &&
            // Objects.nonNull(signUpResponse.getOfferId())&&
            // !signUpResponse.getOfferId().equalsIgnoreCase("") ){
            // //MBS요청.. 트랜젝션에 포함시키지 말아야함.
            // CiLogger.info( "MBS Connection Start(offerId :%s , memberId : %s)",
            // signUpResponse.getOfferId() , member.getMember_id());
            // PurchaseSvodResponse purchaseSvodResponse = mbsConnector.purchaseSvod(
            // signUpResponse.getOfferId() , member.getMember_id());
            // if(purchaseSvodResponse == null) {
            // CiLogger.error( "MBS Connection Failure(offerId :%s , memberId : %s)",
            // signUpResponse.getOfferId() , member.getMember_id());
            // }else{
            // List<Purchase> purchaseList = purchaseSvodResponse.getPayLogs();
            // CiLogger.info( "MBS Connection Success(offerId :%s , memberId : %s ,
            // purchaseList length : %d)", signUpResponse.getOfferId() ,
            // member.getMember_id(), purchaseList.size());
            // try {
            // for (Purchase purchase : purchaseList) {
            // PayLog payLog = makePayLog(purchase);
            // payLogDao.save(payLog);

            // }
            // } catch (Exception e) {
            // CiLogger.error(e, "User pay_log Save Error.(offerId :%s , memberId : %s)",
            // signUpResponse.getOfferId() , member.getMember_id());
            // }
            // }

            // }
        } else { // 회원인증서버 구축 전 회원 저장 서비스 진행
                 // adult_pass default 추가
            if (memberDTO.getAdult_pass() == null)
                memberDTO.setAdult_pass(adultDefaultPassword);
            // 해피콜 대상자 아님 - 정상 가입자 변경
            memberDTO.setSo_happycall_auth(1);// 해피콜 대상자 처리
            memberDTO.setMso_name(siteInfo.getMso_name());

            // 회원등록
            Member member = new Member(memberDTO);
            try {
                Long id = memberDao.saveMember(member);
                member.setId(id);
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }

            // 회원 mbs에 noti
            MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());
            try {
                mbsConnector.noti_join_member(noti);
            } catch (Exception e) {
                CiLogger.error(e, "MBS Failed to Connect");
                // MBS 서비스 연동에 실패 했습니다.
                throw new CiException(CiResultCode.FAIL_CONNECT_MBS_SERVER, CiResultCode.MSG.code_518);
            }
            CiLogger.info("(%s) 케이블TV 회원인증서버 구축 전 가입자입니다. 해피콜 대상자로(%s)회원 저장합니다.", siteInfo.getMso_name(),
                    memberDTO.getTel());
            response = new CiResponse(CiResultCode.SUCCESS,
                    "회원인증 서버 구축 전 회원인증 사용하지 않는 SO(" + siteInfo.getIp() + ")입니다. 해피콜 대상 가입자로 처리 합니다. ")
                    .addAttribute("happycall", 1);

        }
        return response;

    }

    /**
     * updateMember
     * 
     * @param memberDTO
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = { Exception.class })
    public CiResponse updateMember(MemberDTO memberDTO) throws Exception {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(memberDTO.getMember_id());
            if (member == null) {
                response.addAttribute("result_code", CiResultCode.code_101);
                response.addAttribute("result_msg", CiResultCode.MSG.code_101);
                return response;
            }

        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }

        try {

            member.setPassword(memberDTO.getPassword());
            member.setTel(memberDTO.getTel());
            member.setZipcode(memberDTO.getZipcode());
            member.setAddress_city(memberDTO.getAddress_city());
            member.setAddress_dist(memberDTO.getAddress_dist());
            member.setApp_token(memberDTO.getApp_token());
            member.setSo_id(memberDTO.getSo_id());
            member.setApp_version(memberDTO.getApp_version());

            Long id = memberDao.saveMember(member);
            member.setId(id);
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }

        MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());

        try {
            mbsConnector.noti_join_member(noti);
        } catch (Exception e) {
            CiLogger.error(e, "Bad GateWay");
            response.addAttribute("result_code", CiResultCode.BAD_GATEWAY);
            response.addAttribute("result_msg", CiResultCode.MSG.code_503);
            return response;
        }

        return response;
    }

    // 추천인이다
    @Transactional(rollbackFor = { Exception.class })
    public CiResponse recommendMember(MemberDTO memberDTO) throws Exception {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");

        Member member = null;

        try {
            member = memberDao.getMemberbyMemberId(memberDTO.getMember_id());

            if (member.getRecommender_id() != null) {
                response.addAttribute("result_code", CiResultCode.code_0);
                response.addAttribute("result_msg", CiResultCode.MSG.code_0);
                return response;
            }

        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }

        try {
            member = memberDao.getMemberbyRecommenderId(memberDTO.getRecommender_id());

            if (member == null) {
                response.addAttribute("result_code", CiResultCode.code_1);
                response.addAttribute("result_msg", CiResultCode.MSG.code_1);
                return response;
            }

        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }

        try {
            // 업데이트 하기
            member = memberDao.getMemberbyMemberId(memberDTO.getMember_id());
            memberDao.updateRecommenderId(member.getId(), memberDTO.getRecommender_id()); 

            response.addAttribute("result_code", CiResultCode.code_2);
            response.addAttribute("result_msg", CiResultCode.MSG.code_2);
            return response;

        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }
    }

    /**
     * getMemberbyMemberId
     * 
     * @param apiRequest
     * @return
     * @throws Exception
     */
    public CiResponse getMemberbyMemberId(UserSignUpRequest apiRequest) throws Exception {
        CiResponse response = new CiResponse();
        Member member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
        response.addAttribute("result_code", CiResultCode.SUCCESS);
        response.addAttribute("result_msg", CiResultCode.MSG.code_100);
        response.addAttribute("user_id", member.getMember_id());
        response.addAttribute("user_pass", password.encode(member.getPassword()));
        response.addAttribute("user_name", member.getUser_name());
        response.addAttribute("social_number", member.getSocial_number());
        response.addAttribute("phone_number", member.getTel());
        response.addAttribute("zipcode", member.getZipcode());
        response.addAttribute("address", member.getAddress_city());
        response.addAttribute("detail_adrdess", member.getAddress_dist());
        response.addAttribute("adult_cert", member.isAdult_cert() == true ? "1" : "0");
        response.addAttribute("mso_name", member.getMso_name());
        response.addAttribute("ci", member.getCi());
        response.addAttribute("di", member.getDi());
        response.addAttribute("app_token", member.getApp_token());

        // response.addAttribute("app_code", member.getApp_code());
        return response;
    }

    /**
     * signOutMember
     * 
     * @param apiRequest
     * @return
     */
    public ModelMap signOutMember(UserSignOutRequest apiRequest) {
        ModelMap response = new ModelMap();
        try {
            Member member = memberDao.getMemberbyMemberId(apiRequest.getUserId());
            if (member != null) {
                memberDao.deleteMember(member);
                paymentService.memberUnregister(member);
                response.addAttribute("result", "success");
            } else {
                response.addAttribute("result", "failure");
                response.addAttribute("details", "02");
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result", "failure");
            response.addAttribute("details", "03");
            return response;
        }
        return response;
    }

    /**
     * MBS 호출용
     * 
     * @param apiRequest
     * @return
     */
    public CiResponse signOutMemberVer3(UserSignOutV2Request apiRequest) {
        // App app = null;
        CiResponse response = new CiResponse();
        try {
            Member member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            if (member != null) {
                memberDao.deleteMember(member);
                paymentService.memberUnregister(member);
                response.addAttribute("result_code", CiResultCode.SUCCESS);
                response.addAttribute("result_msg", CiResultCode.MSG.code_100);
            } else {
                response.addAttribute("result_code", CiResultCode.code_101);
                response.addAttribute("result_msg", CiResultCode.MSG.code_101);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }

        return response;
    }

    /**
     * 
     * @param apiRequest
     * @return
     */
    public CiResponse signOutMemberVer2(UserSignOutV2Request apiRequest) {
        // App app = null;
        CiResponse response = new CiResponse();
        try {
            Member member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            if (member != null) {
                memberDao.deleteMember(member);
                paymentService.memberUnregister(member);
                MemberNotiDTO noti = new MemberNotiDTO(member, new BankRef());
                mbsConnector.noti_delete_member(noti);
                response.addAttribute("result_code", CiResultCode.SUCCESS);
                response.addAttribute("result_msg", CiResultCode.MSG.code_100);
            } else {
                response.addAttribute("result_code", CiResultCode.code_101);
                response.addAttribute("result_msg", CiResultCode.MSG.code_101);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }

        return response;
    }

    /**
     * 
     * @param apiRequest
     * @param previewPeriodInMinute
     * @return
     * @throws Exception
     * @throws CiException
     */
    public CiResponse previewPeriod(UserSignUpRequest apiRequest, int previewPeriodInMinute)
            throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            if (member == null) {
                throw new CiException(CiResultCode.code_101, CiResultCode.MSG.code_101);
            }

            boolean isPreviewPeriod = false;
            long now = System.currentTimeMillis();
            long then = member.getCreate_time().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now - then);

            if (previewPeriodInMinute > minutes) {
                isPreviewPeriod = true;
            }

            response.addAttribute("result_code", CiResultCode.SUCCESS);
            response.addAttribute("result_msg", CiResultCode.MSG.code_100);
            response.addAttribute("is_preview_period", isPreviewPeriod);

        } catch (CiException e) {
            throw e;
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    /**
     * passwordMatch
     * 
     * @param apiRequest
     * @return
     * @throws Exception
     * @throws CiException
     */
    public CiResponse passwordMatch(UserSignUpRequest apiRequest) throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            if (member == null) {
                throw new CiException(CiResultCode.code_101, CiResultCode.MSG.code_101);
            }

            boolean isPasswordMatch = apiRequest.getUser_pass().equalsIgnoreCase(member.getPassword());

            response.addAttribute("result_code", CiResultCode.SUCCESS);
            response.addAttribute("result_msg", CiResultCode.MSG.code_100);
            response.addAttribute("is_password_match", isPasswordMatch);

        } catch (CiException e) {
            throw e;
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    /**
     * userExist
     * 
     * @param apiRequest
     * @return
     * @throws Exception
     * @throws CiException
     */
    public CiResponse userExist(UserFindByIdNPhoneRequest apiRequest) throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getMemberbyIdTel(apiRequest.getUser_id(), apiRequest.getPhone_number());
            response.addAttribute("result_code", CiResultCode.SUCCESS);
            response.addAttribute("result_msg", CiResultCode.MSG.code_100);

            if (member == null) {
                response.addAttribute("is_user_exist", false);
            } else {
                response.addAttribute("is_user_exist", true);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    public CiResponse passwordChange(UserPassRequest apiRequest) throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            if (member == null) {
                throw new CiException(CiResultCode.code_101, CiResultCode.MSG.code_101);
            }

            // HCN app 요청시 비밀번호를 암호화 하여 전달해준다.
            member.setPassword(apiRequest.getUser_pass());

            String result = memberDao.saveOrUpdateMember(member);
            if (result.equalsIgnoreCase("success")) {
                response.addAttribute("result_code", CiResultCode.SUCCESS);
            } else {
                response.addAttribute("result_code", CiResultCode.INTERNAL_SERVER_ERROR);
                response.addAttribute("result_msg", CiResultCode.MSG.code_999);
            }
        } catch (CiException e) {
            throw e;
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    public CiResponse findUserId(UserFindByPhoneRequest apiRequest) throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getUniqueMemberbyTel(apiRequest.getPhone_number());
            if (member == null) {
                throw new CiException(CiResultCode.code_101, CiResultCode.MSG.code_101);
            }

            response.addAttribute("result_code", CiResultCode.SUCCESS);
            response.addAttribute("result_msg", CiResultCode.MSG.code_100);
            response.addAttribute("user_id", member.getMember_id());
        } catch (CiException e) {
            throw e;
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    public CiResponse idDuplicateCheck(IsDuplicateCheckRequest apiRequest) throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            response.addAttribute("result_code", CiResultCode.SUCCESS);
            response.addAttribute("result_msg", CiResultCode.MSG.code_100);

            if (member == null) {
                response.addAttribute("is_duplicate", false);
            } else {
                response.addAttribute("is_duplicate", true);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return response;
    }

    public CiResponse checkDuplicateEmail(CheckDuplicateEmailRequest apiRequest) throws Exception, CiException {
        CiResponse response = new CiResponse(CiResultCode.SUCCESS, "");
        Member member = null;
        if (EmailValidator.validateEmail(apiRequest.getEmail())) {
            try {
                member = memberDao.getMemberbyEmail(apiRequest.getEmail());
                response.addAttribute("result_code", CiResultCode.SUCCESS);
                response.addAttribute("result_msg", CiResultCode.MSG.code_100);

                if (member == null) {
                    response.addAttribute("is_duplicate", false);
                } else {
                    response.addAttribute("is_duplicate", true);
                }
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
                throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        } else {
            response.addAttribute("result_code", CiResultCode.code_138);
            response.addAttribute("result_msg", CiResultCode.MSG.code_138);
        }

        return response;
    }

    public SiteInfo getSiteInfo(String msoName) {
        return siteInfoDao.getSiteInfobyMsoName(msoName);
    }

    public SiteInfo getSiteInfoBySoId(String soId) {
        return siteInfoDao.getSiteInfobySoId(soId);
    }

    private String makeExternalPurchaseId() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        // 1000 ~ 10000 숫자
        int num = (int) (random.nextInt(9000) + 1000);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        return num + now.format(pattern);
    }

    public CiResponse purchaseSvod(PurchaseSvodRequest request) throws Exception {
        // 1. 요청에 필요한 데이터를 추출합니다.
        String accountId = request.getUserId();
        String offerId = request.getOfferId().replaceAll(" ", "");
        PurchaseSvodResponse response = mbsConnector.purchaseSvod(offerId, accountId);
        if (response.getResultCode() == CiResultCode.SUCCESS) {
            CiLogger.info("purchaseSvodResponse size :  %s", response.getPayLogs().size());
            List<Purchase> purchaseList = response.getPayLogs();
            for (Purchase purchase : purchaseList) {
                PayLog payLog = makePayLog(purchase);
                payLogDao.save(payLog);
            }
        }
        return new CiResponse(response.getResultCode(), "");
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

    public CiResponse cancelPurchaseSvod(CancelPurchaseSvodRequest request) {
        String accountId = request.getUserId();
        String offerId = request.getOfferId().replaceAll(" ", "");
        CancelSvodResponse response = mbsConnector.cancelSvod(offerId, accountId);
        if (response.getResultCode() == CiResultCode.SUCCESS) {
            String[] billingIds = response.getBillingIds();
            try {
                paymentService.cancel_purchase_subscription(billingIds);
            } catch (Exception e) {
                return new CiResponse(CiResultCode.DB_GENERAL_ERROR, "");
            }
            return new CiResponse(CiResultCode.SUCCESS, "");
        } else {
            return new CiResponse(response.getResultCode(), "");
        }

    }

    public CiResponse getHappyCallbyMemberId(UserSignUpRequest apiRequest) throws Exception {
        CiResponse response = new CiResponse();
        Member member = null;
        try {
            member = memberDao.getMemberbyMemberId(apiRequest.getUser_id());
            if (member == null) {
                throw new CiException(CiResultCode.code_101, CiResultCode.MSG.code_101);
            }

            response.addAttribute("result_code", CiResultCode.SUCCESS);
            response.addAttribute("result_msg", CiResultCode.MSG.code_100);
            response.addAttribute("happy_call_auth", member.getSo_happycall_auth() == 0);
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
            response.addAttribute("result_code", CiResultCode.DB_GENERAL_ERROR);
            response.addAttribute("result_msg", CiResultCode.MSG.code_501);
            return response;
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CiResponse purchase(CiRequest ciRequest) throws Exception {
        if (ciRequest instanceof PurchaseRequest) {
            PurchaseRequest request = (PurchaseRequest) ciRequest;
            {
                TransactionInfo oldProcessing = transactionInfoDao
                        .selectOne(new ModelMap("transaction_id", request.getStringValue("billing_id")));
                if (oldProcessing != null) {
                    throw new CiException(CiResultCode.IN_PROGRESS_ANOTHER, "");
                }
            }

            {
                PayLog payLog = payLogDao
                        .selectOne(new ModelMap("purchase_billing_id", request.getStringValue("billing_id")));
                if (payLog != null) {
                    if (payLog.getStatus() == PVSConstants.Pay.STATUS.COMPLETE) {
                        if (PVSConstants.Pay.TYPE.MON.equalsIgnoreCase(payLog.getPurchase_type())) {
                            throw new CiException(CiResultCode.ALREADY_PAY_SVOD, "");
                        } else {
                            throw new CiException(CiResultCode.ALREADY_PAY_TVOD, "");
                        }
                    } else if (payLog.getStatus() == PVSConstants.Pay.STATUS.CANCEL) {
                        throw new CiException(CiResultCode.PAY_CANCELLED, "취소된 결제id입니다.");
                    } else {
                        throw new CiException(CiResultCode.BAD_REQUEST, "billing_id가 이미 사용중입니다.");
                    }
                }
            }

            TransactionInfo transactionInfo = request.makeTransactionInfo();
            transactionInfoDao.save(transactionInfo);

            String memberId = request.getStringValue("account_id");
            CiLogger.info("account_id : " + memberId);

            try {
                Member member = null;
                {
                    // member 유효성 검사
                    member = memberDao.getMemberbyMemberId(memberId);

                    if (member == null) {
                        throw new CiException(CiResultCode.INVALID_ACCOUNT_ID);
                    } else if (member.isUnregister()) {
                        throw new CiException(CiResultCode.INVALID_ACCOUNT_ID);
                    }
                }

                PayLog payLog = request.makePayLog();
                payLog.setTel(member.getTel());
                payLogDao.save(payLog);
                return new CiResponse(CiResultCode.SUCCESS, "");
            } finally {
                try {
                    if (transactionInfo != null) {
                        transactionInfoDao.delete(transactionInfo);
                    }
                } catch (Exception e) {
                    CiLogger.error(e, "Fail to delete transactionInfo");
                }
            }
        }

        throw new CiException(CiResultCode.UNSUPPORTED_REQUEST, "Unsupported request!");
    }

    
    public CiResponse selectAllDeviceWithAccountId(NewDeviceDTO newDeviceDTO) throws CiException {
		NewDevice newDevice = new NewDevice(newDeviceDTO);
        boolean flag = false;
        List<NewDevice> deviceList;
		try {
			deviceList = memberDao.selectAllDeviceWithAccountId(newDevice);
            if (deviceList.size() == 0) {
                memberDao.saveNewDevice(newDevice);
                return new CiResponse(CiResultCode.SUCCESS);
            }
            for(NewDevice nd : deviceList) {
                if (nd.getDevice_info().equals(newDevice.getDevice_info())) {
                    flag = true;
                }
            }
		} catch (Exception e) {
			CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
		}
        // 새기기에서 로그인
        if(!flag) {
            // 등록된 기기가 4개 이상이면 기존 기기 지울 수 있도록 예외 던지기(나중에 기능 활성화)
            // if(deviceList.size() >= 4) {
                // return new CiResponse(CiResultCode.code_121);
            // }
            try {
                // 새기기 추가
                memberDao.saveNewDevice(newDevice);
                return new CiResponse(CiResultCode.SUCCESS);
            } catch (Exception e) {
                CiLogger.error(e, "DB General Error.");
			    throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
            }
        }
        // 이미 등록되어 있는 기기
        return new CiResponse(CiResultCode.code_134);
    }

    public CiResponse getDeviceList(NewDeviceDTO newDeviceDTO) throws CiException {
        NewDevice newDevice = new NewDevice(newDeviceDTO);
        List<NewDevice> deviceList;
        try {
           deviceList = memberDao.selectAllDeviceWithAccountId(newDevice);
           if (deviceList.size() == 0) {
                return new CiResponse(CiResultCode.code_198);
            }
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }

        return new CiResponse(CiResultCode.SUCCESS).addAttribute("deviceList",deviceList);
    }

    public CiResponse deleteDevice(NewDeviceDTO newDeviceDTO) throws CiException {
        NewDevice newDevice = new NewDevice(newDeviceDTO);
        try {
            memberDao.deleteDevice(newDevice);
        } catch (Exception e) {
            CiLogger.error(e, "DB General Error.");
			throw new CiException(CiResultCode.DB_GENERAL_ERROR, CiResultCode.MSG.code_501);
        }
        return new CiResponse(CiResultCode.SUCCESS);
    }
}
