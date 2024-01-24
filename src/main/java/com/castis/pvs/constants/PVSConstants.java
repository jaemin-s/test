package com.castis.pvs.constants;

public final class PVSConstants {

	public static final class DEVICE {
		public static final class TYPE {
			public static final String STB = "STB";
			public static final String MOBILE = "MOBILE";
			public static final String PC = "PC";
			public static final String MNG = "MNG";
			public static final String MBS = "MBS";
		}
	}

	public static final class SMS{
//		public static final String pay_link_sms_text = "[딜라이브] 유료 콘텐츠 구매 이동";
//		public static final String pay_easy_month_sms_success_text = "[딜라이브] 유료 콘텐츠(%s)의 월 정기 결제가 정상적으로 처리되었습니다.";
//		public static final String pay_easy_month_sms_fail_text = "[딜라이브] 유료 콘텐츠(%s)의 월 정기 결제가 해지 처리되었습니다.";
	}

	public static final class ACCOUNT{
		public static final class TYPE {
			public static final String MEMBER = "MB";
			public static final String STB = "STB";
		}
	}

	public static final class TRANSACTIONINFO {
		public static final String CARD_SIN = "CARD_SIN";//(card 단품 결제)
		public static final String PHONE_SIN = "PHONE_SIN";//(phone 단품 결제)
		public static final String SIN = "SIN";//(회원 카드 단품 결제)
		public static final String SIN_CPX = "SIN_CPX";//(회원 복합 결제)
		public static final String MON = "MON";//(회원 카드 월정액 결제)
		public static final String REG = "REG";//(회원가입)
		public static final String MOD = "MOD";//(회원수정)
		public static final String CMOD = "CMOD";//(카드수정)
		public static final String FIND = "FIND";//(ID/PW찾기)
		public static final String CHK = "CHK";//(비회원 본인인증)
		public static final String PNT_CHRG = "PNT_CHRG";//(비회원 본인인증)


		public static final class STATUS {
			public static final int code_00 = 00;//생성:00
			public static final int CREATE = code_00;//생성:00
			public static final int CODE_11 = 11;
			public static final int MOBILE_START = CODE_11;
			public static final int CODE_12 = 12;
			public static final int PAYMENT_START = CODE_12;
			public static final int code_22 = 22;//결제취소:22
			public static final int CANCEL = code_22;//결제취소:22
			public static final int code_23 = 23;//결제실패:23
			public static final int FAIL = code_23;
		}
	}

	public static final class Processing {

		public static final class STATUS {
			public static final int code_502				= 502;//"요청이 처리 중입니다. 잠시 후 다시 진행해주시길 바랍니다.";
			public static final int IN_PROGRESS				= code_502;
		}
	}

	public static final class CONFIRM {
		public static final String SUCCESS = "SUC";//(확정)
		public static final String CANCEL = "CAN";//(취소)
	}

	public static final class Pay {

		public static final class TYPE {
			public static final String CARD_SIN = "CARD_SIN";//(card 단품 결제)
			public static final String PHONE_SIN = "PHONE_SIN";//(phone 단품 결제)
			public static final String CARD_MON = "CARD_MON";//(card 월정액 결제)
			public static final String PHONE_MON = "PHONE_MON";//(phone 월정액 결제)
			public static final String SIN = "SIN";//(회원 카드 단품 결제)
			public static final String SIN_CPX = "SIN_CPX";//(회원 복합 결제)
			public static final String MON = "MON";//(회원 카드 월정액 결제)
			public static final String PNT_CHRG = "PNT_CHRG";//(포인트 충전 결제)
		}

		public static final class STATUS {
		//생성:00,요청:01,요청타임오버:10,휴대폰결제:11,결제타임오버:20,결제완료:21,결제취소:22,결제실패:23,결제취소 실패:24

		public static final int code_00 = 00;//생성:00
		public static final int START = code_00;//생성:00
		public static final int code_01 = 01;//요청:01

		public static final int code_10 = 10;//요청타임오버:10
		public static final int code_11 = 11;//휴대폰결제:11
		public static final int MOBILE_PAY_START = code_11;
		public static final int code_20 = 20;//결제타임오버:20
		public static final int code_21 = 21;//결제완료:21
		public static final int COMPLETE = code_21;//결제완료:21
		public static final int code_22 = 22;//결제취소:22
		public static final int CANCEL = code_22;//결제취소:22
		public static final int code_23 = 23;//결제실패:23
		public static final int FAIL = code_23;
		public static final int code_24 = 24;//결제취소 실패:24
		public static final int CANCEL_FAIL = code_24;


		}

		public static final class NICEPAY {
			public static final class actionType {
				public static final String SUCCESS = "PY0";//(확정)
				public static final String CANCEL = "CL0";//(취소)
			}

		}

		public static final class MBS {
			public static final class STATE {
				public static final String SUCCESS = "success";//(확정)
				public static final String FAIL = "fail";//(실패)
				public static final String CANCEL = "cancel";//(취소)
				public static final String TERMINATION  = "termination";//(해지)
			}

		}

		public static final class IMS {
			public static final class STATE {
				public static final String SUCCESS = "success";//(확정)
				public static final String FAIL = "fail";//(실패)
				public static final String CANCEL = "cancel";//(취소)
				public static final String TERMINATION  = "termination";//(해지)
			}

		}

		public static final class XPAY {
			public static final class RESULT {

				//통신 장애
				public static final String code_C012	= "C012";//	VAN사 통신에러
				public static final String code_0010	= "0010";//	거래 TIME-OUT (잠시 후 다시 결제 바랍니다.)
				public static final String code_0110	= "0110";//	거래TIME-OUT(잠시 후 다시 결제)
				public static final String code_0121	= "0121";//	카드사 통신장애(잠시 후 다시 결제)
				public static final String code_0122	= "0122";//	카드사 수신장애(잠시 후 다시 결제)
				public static final String code_0123	= "0123";//	상점결과 송신장애
				public static final String code_0515	= "0515";//	세션키 생성 에러
				public static final String code_0570	= "0570";//	통신 에러
				public static final String code_0580	= "0580";//	DB 에러
				public static final String code_0590	= "0590";//	시간초과 오류
				public static final String code_8659	= "8659";//	카드사무응답 지연응답(Authorization System or issuer system inoperative)
				public static final String code_3000	= "3000";//	F2:카드사무응답/지연응답(Authorization System or issuer system inoperative)
				public static final String code_3001	= "3001";//	밴사 통신 타임아웃입니다.
				public static final String code_S002	= "S002";//죄송합니다. 잠시 후 다시 이용해 주시기 바랍니다.
				public static final String code_KCR3	= "KCR3";//Time-Out 또는 지연응답
				public static final String code_KCSE	= "KCSE";//거래TIME-OUT
				public static final String code_1007	= "1007";//DB 오류
				public static final String code_2041	= "2041";//카드사 타임 아웃
				public static final String code_2115	= "2115";//서버로 데이타를 송신하지 못했습니다.
				public static final String code_2199	= "2199";//통신오류, 잠시후 재시도 요망
				public static final String code_2200	= "2200";//통신오류, 잠시후 재시도 요망
				public static final String code_2201	= "2201";//통신오류, 잠시후 재시도 요망
				public static final String code_2202	= "2202";//통신오류, 잠시후 재시도 요망
				public static final String code_2203	= "2203";//통신오류, 잠시후 재시도 요망
				public static final String code_2204	= "2204";//통신오류, 잠시후 재시도 요망
				public static final String code_2213	= "2213";//통신오류, 잠시후 다시 시도하세요.
				public static final String code_2246	= "2246";//카드사로부터 응답을 받을 수 없습니다

				//서버 장애
				public static final String code_299		= "299";//	해당 카드사 사스템 점검중
				public static final String code_0299	= "0299";//	해당 카드사 사스템 점검중
				public static final String code_KCR2	= "KCR2";//회선장애
				public static final String code_KCR4	= "KCR4";//Merchant 시스템 장애
				public static final String code_2114	= "2114";//서버에 연결할 수 없습니다.

			}

			public static boolean is_bad_config(String code, String message) {

				if(code!=null && code.isEmpty()==false) {

					if(RESULT.code_299.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0299.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_KCR2.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_KCR4.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2114.equalsIgnoreCase(code)) {return true;}
				}

				return false;
			}

			public static boolean is_retry(String code, String message) {

				if(code!=null && code.isEmpty()==false) {

					if(RESULT.code_C012.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_S002.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0010.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0110.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0121.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0122.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0123.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0515.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0570.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0580.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_0590.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_3000.equalsIgnoreCase(code)) {
						if(message!=null && message.startsWith("F2")) {
							return true;
						}
					}
					else if(RESULT.code_3001.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_8659.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_KCR3.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_KCSE.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_1007.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2041.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2115.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2199.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2200.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2201.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2202.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2203.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2204.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2213.equalsIgnoreCase(code)) {return true;}
					else if(RESULT.code_2246.equalsIgnoreCase(code)) {return true;}
				}

				return false;
			}

		}

	}

	public static final class PASSCHECKTYPE {
		public static final String LOGIN = "LOGIN";//(로그인)
		public static final String ADULT = "ADULT";//(성인인증)
		public static final String ADULT_STB = "ADULT_STB";//(비회원 성인인증)
	}

	public static final class PASSSETTYPE {
		public static final String INIT = "INIT";//(초기설정)
		public static final String CHAN = "CHAN";//(변경)
	}

	public static final class MEMBERACTIVETYPE {
		public static final String ACTIVE = "ACTIVE";//일반
		public static final String INACTIVE = "INACTIVE";//휴면
	}

}
