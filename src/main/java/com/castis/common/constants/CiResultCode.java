package com.castis.common.constants;


public final class CiResultCode {

	// section 10 of RFC 2616
	//정보 응답
	/*
	100 Continue
	101 Switching Protocol
	102 Processing (WebDAV)
	103 Early Hints
	*/

	//성공 응답
	/*
	200 OK
	201 Created
	202 Accepted
	203 Non-Authoritative Information
	204 No Content
	205 Reset Content
	206 Partial Content
	207 Multi-Status (WebDAV)
	208 Multi-Status (WebDAV)
	226 IM Used (HTTP Delta encoding)
	*/

	//리다이렉션 메시지
	/*
	300 Multiple Choice
	301 Moved Permanently
	302 Found
	303 See Other
	304 Not Modified
	305 Use Proxy
	306 unused
	307 Temporary Redirect
	308 Permanent Redirect
	 */

	//클라이언트 에러 응답
	/*
	400 Bad Request
	401 Unauthorized
	402 Payment Required
	403 Forbidden
	404 Not Found
	405 Method Not Allowed
	406 Not Acceptable
	407 Proxy Authentication Required
	408 Request Timeout
	409 Conflict
	410 Gone
	411 Length Required
	412 Precondition Failed
	413 Payload Too Large
	414 URI Too Long
	415 Unsupported Media Type
	416 Requested Range Not Satisfiable
	417 Expectation Failed
	418 I'm a teapot
	421 Misdirected Request
	422 Unprocessable Entity (WebDAV)
	423 Locked (WebDAV)
	424 Failed Dependency (WebDAV)
	426 Upgrade Required
	428 Precondition Required
	429 Too Many Requests
	431 Request Header Fields Too Large
	451 Unavailable For Legal Reasons
	*/

	//서버 에러 응답
	/*
	500 Internal Server Error
	501 Not Implemented
	502 Bad Gateway
	503 Service Unavailable
	504 Gateway Timeout
	505 HTTP Version Not Supported
	506 Variant Also Negotiates
	507 Insufficient Storage
	508 Loop Detected (WebDAV)
	510 Not Extended
	511 Network Authentication Required
	*/

	public static final int code_100 = 100;
	public static final int OK 		 = code_100;
	public static final int SUCCESS  = code_100;
	public static final int code_101 = 101;//"등록되지 않은 아이디 이거나 아이디 또는 비밀번호를 잘못 입력하셨습니다.";
	public static final int code_102 = 102;//"번호당 3개의 회원가입만 가능합니다 (3개 초과 된 ID)";
	public static final int code_104 = 104;//"문자전송이 실패했습니다.";
	public static final int code_105 = 105;//"입력하신 전화번호가 유효하지 않습니다.";
	public static final int code_106 = 106;//"transaction_id 가 유효하지 않습니다.";
	public static final int code_107 = 107;//"device_type 이 유효하지 않습니다.";
	public static final int code_108 = 108;//"wifi_mac 이 유효하지 않습니다.";
	public static final int code_109 = 109;//"model 이 유효하지 않습니다.";
	public static final int code_110 = 110;//"url_type 이 유효하지 않습니다.";
	public static final int code_111 = 111;//"[ 플러스 셋톱박스]의  최초 한번 로그인을 해야 모바일 딜라이브i 사용이 가능함.";
	public static final int code_112 = 112;//"입력하신 아이디와 전화번호가 등록된 정보와 일치하지 않습니다. <br> 아이디와 전화번호를 확인하시고 다시 입력하시기 바랍니다";
	public static final int code_113 = 113;//"시스템오류 - URL_TYPE 값이 유효하지 않습니다.";
	public static final int code_114 = 114;//"account_id 가 유효하지 않습니다.";
	public static final int INVALID_ACCOUNT_ID = code_114;//"account_id 가 유효하지 않습니다.";

	public static final int code_115 = 115;//"content_id 가 유효하지 않습니다.";
	public static final int code_116 = 116;//"content_title 가 유효하지 않습니다.";
	public static final int code_117 = 117;//"content_price 가 유효하지 않습니다.";
	public static final int code_118 = 118;//"content_period 가 유효하지 않습니다.";
	public static final int code_119 = 119;//"account_id  는 존재하지 않은 값입니다.";
	public static final int code_120 = 120;//"Product_id 가 유효하지 않습니다.";
	public static final int code_121 = 121;//"현재 셋탑에 이미 4개의 아이디가 등록되어 있기 때문에 새로운 아이디로 로그인이 불가합니다.";
	public static final int code_122 = 122;//"Apple_id 가 유효하지 않습니다.";
	public static final int code_123 = 123;//"Pay_date 가 유효하지 않습니다.";

	/**
	 * "해당 TVOD상품은 이미 구매한 상품입니다.";
	 */
	public static final int code_124 = 124;// = "해당 TVOD상품은 이미 구매한 상품입니다.";
	public static final int ALREADY_PAY_TVOD = code_124;
	public static final int code_125 = 125;// = "해당 SVOD상품은 이미 가입한 상품입니다.";
	public static final int ALREADY_PAY_SVOD = code_125;
	public static final int code_126 = 126;// = "체험용 DLivePlus 인 경우 Dlivei 회원가입을 하실 수 없습니다.";
	public static final int code_127 = 127;// = "Language가 유효하지 않습니다.";
	public static final int code_128 = 128;// = "app_code 가 유효하지 않습니다.";
	public static final int code_129 = 129;// = "bill_gubn_code 가 유효하지 않습니다.";
	public static final int code_130 = 130;// = "bill_case 가 유효하지 않습니다.";
	public static final int code_131 = 131;// = "가격정보가 유효하지 않습니다.";
	public static final int code_132 = 132;// = "등록되지 않은 비밀번호이거나 잘못 입력하셨습니다.";
	public static final int code_133 = 133;// = "ios_transaction_id 가 유효하지 않습니다.";
	public static final int code_134 = 134;// = "이미 등록되어 있는 ID 입니다.";
	public static final int code_135 = 135;// = "SiteName이 유효 하지 않습니다.";
	public static final int code_136 = 136;// = "AuthNumber가 존재 하지 않습니다.";
	public static final int code_137 = 137;// = "이미 등록되어 있는 phone_number 입니다.";

	public static final int code_138 = 138;// = "email이 유효하지 않습니다.";
	public static final int code_198 = 198;//"회원인증 요청 정보가 없거나 유효하지 않습니다.";
	public static final int code_199 = 199;
	public static final int PARSING_ERROR 			= code_198;
	public static final int BAD_REQUEST 			= code_198;
	public static final int code_200 = 200;//"잘못된 접근입니다.";
	public static final int code_201 = 201;//"지원되지 않는 서비스입니다.";
	public static final int UNSUPPORTED_REQUEST 			= code_201;
	public static final int code_202 = 202;//"로그인이 필요합니다.";
	public static final int code_203 = 203;//"사용 권한이 없습니다.";
	public static final int code_204 = 204;//"카드 정보가 유효하지 않습니다.";
	public static final int INVALID_CARD = code_204;//"카드 정보가 유효하지 않습니다.";
	public static final int code_205 = 205;//"잔액이 부족합니다.";
	public static final int code_206 = 206;//"결제가 불가한 카드입니다.";

	public static final int code_207 = 207;//"결제 비밀번호가 유효하지 않습니다.";
	public static final int INVALID_PAY_PWD = code_207;//"결제 비밀번호가 유효하지 않습니다.";


	public static final int code_501				= 501; //"DB 접근오류가 발생하였으니 다시 수행하여 주시기 바랍니다.";
	public static final int DB_GENERAL_ERROR		= code_501; //"DB 접근오류가 발생하였으니 다시 수행하여 주시기 바랍니다.";
	public static final int code_502				= 502;//"요청이 처리 중입니다. 잠시 후 다시 진행해주시길 바랍니다.";
	public static final int IN_PROGRESS				= code_502;
	public static final int code_503				= 503;//"외부 연동에 실패했습니다.";
	public static final int BAD_GATEWAY 			= code_503;
	public static final int code_504				= 504;//"다른 요청이 처리 중입니다. 잘못된 접근입니다.";
	public static final int IN_PROGRESS_ANOTHER		= code_504;
	public static final int code_505				= 505;//"취소 요청이 처리 중입니다. 잠시 후 다시 진행해주시길 바랍니다.";
	public static final int IN_PROGRESS_CANCEL		= code_505;
	public static final int code_506				= 506;//"결제가 이미 최소되었습니다. 결제가 불가능합니다.";
	public static final int PAY_CANCELLED			= code_506;
	public static final int code_507				= 507;//"결제가 이미 승인되었습니다. 취소가 불가능합니다."
	public static final int CANNOT_CANCEL_COMPLETE	= code_507;
	public static final int code_508				= 508;//"외부 notify에 실패했습니다.";
	public static final int BAD_NOTIFY 				= code_508;
	public static final int code_509				= 509;//"결제가 이미 최소되었습니다. 결제가 불가능합니다.";
	public static final int PAY_FAILED				= code_509;
	public static final int code_510				= 510;//"Pin 번호가 존재 하지 않습니다.";
	public static final int NOT_EXIST_PIN_NO		= code_510;
	public static final int code_511				= 511;//"등록된 Pin 번호 입니다.";
	public static final int REGISTERED_PIN_NO		= code_511;
	public static final int code_512				= 512;//"Point Event ID 가 존재 하지 않습니다.";
	public static final int NOT_EXIST_POINT_EVENT	= code_512;
	public static final int code_513				= 513;//"Point Produt ID 가 존재 하지 않습니다.";
	public static final int NOT_EXIST_POINT_PRODUCT	= code_513;
	public static final int code_514				= 514;//"만료된 Point 입니다.";
	public static final int EXPIRED_POINT			= code_514;
	public static final int code_515				= 515;//"포인트 잔액이 부족 합니다. ";
	public static final int NOT_ENOUGH_BALANCE		= code_515;
	public static final int code_516				= 516;//"신용카드 정보 확인/변경 : 홈→MY→카드변경\n※ 5회 오류 시, 신용카드 이용제한";
	public static final int PASSWORD_FAULT			= code_516;
	public static final int code_517				= 517;//"카드사에 문의하여, 변경된 카드 비밀번호를 회원 정보에 등록해 주세요.\n(신용카드 정보 확인/변경 : 홈→MY→카드변경)";
	public static final int EXCEEDED_COUNT_AUTH		= code_517;

	public static final int code_518 				= 518;//"MBS 서비스 연동에 실패 했습니다."; 
	public static final int FAIL_CONNECT_MBS_SERVER = code_518;

	public static final int code_519				= 519;// "SO별 인증서버 연동이 원할하지 않습니다.";
	public static final int FAIL_CONNECT_SO_SERVER = code_519;

	public static final int code_520				= 520;// "회원인증을 사용하지 않는 SO 입니다. API Url을 확인해주세요.";;
	public static final int CHECK_SO_API_URL = code_520;

	//
	public static final int BAD_CONFIG					= 700;
	public static final int PAY_NO_RETRY				= 701;
	public static final int PAY_NOTHING_CHANG			= 702;
	public static final int PAY_RETRY					= 703;

	public static final int code_999 				= 999; //"요청한 작업에 오류가 있습니다.";
	public static final int INTERNAL_SERVER_ERROR 	= code_999;
	public static final int GENERAL_ERROR 			= code_999;



	public static final class MSG {
		public static final String code_100 = "요청 처리 성공";
		public static final String code_101 = "등록되지 않은 아이디 이거나 아이디 또는 비밀번호를 잘못 입력하셨습니다.";
		public static final String code_102 = "번호당 3개의 회원가입만 가능합니다 (3개 초과 된 ID)";
		public static final String code_104 = "문자전송이 실패했습니다.";
		public static final String code_105 = "입력하신 전화번호가 유효하지 않습니다.";
		public static final String code_106 = "transaction_id 가 유효하지 않습니다.";
		public static final String code_107 = "device_type 이 유효하지 않습니다.";
		public static final String code_108 = "wifi_mac 이 유효하지 않습니다.";
		public static final String code_109 = "model 이 유효하지 않습니다.";
		public static final String code_110 = "url_type 이 유효하지 않습니다.";
		public static final String code_111 = "[딜라이브 플러스 셋톱박스]의 딜라이브i에서 최초 한번 로그인을 해야 모바일 딜라이브i 사용이 가능함.";
		public static final String code_112 = "입력하신 아이디와 전화번호가 등록된 정보와 일치하지 않습니다. <br> 아이디와 전화번호를 확인하시고 다시 입력하시기 바랍니다";
		public static final String code_113 = "시스템오류 - URL_TYPE 값이 유효하지 않습니다.";
		public static final String code_114 = "account_id 가 유효하지 않습니다.";
		public static final String code_115 = "content_id 가 유효하지 않습니다.";
		public static final String code_116 = "content_title 가 유효하지 않습니다.";
		public static final String code_117 = "content_price 가 유효하지 않습니다.";
		public static final String code_118 = "content_period 가 유효하지 않습니다.";
		public static final String code_119 = "account_id  는 존재하지 않은 값입니다.";
		public static final String code_120 = "Product_id 가 유효하지 않습니다.";
		public static final String code_121 = "현재 셋탑에 이미 4개의 아이디가 등록되어 있기 때문에 새로운 아이디로 로그인이 불가합니다.";
		public static final String code_122 = "Apple_id 가 유효하지 않습니다.";
		public static final String code_123 = "Pay_date 가 유효하지 않습니다.";

		public static final String code_124 = "해당 TVOD상품은 이미 구매한 상품입니다.";
		public static final String code_125 = "해당 SVOD상품은 이미 가입한 상품입니다.";

		public static final String code_126 = "체험용 DLivePlus 인 경우 Dlivei 회원가입을 하실 수 없습니다.";
		public static final String code_127 = "Language가 유효하지 않습니다.";
		public static final String code_128 = "app_code 가 유효하지 않습니다.";
		public static final String code_129 = "bill_gubn_code 가 유효하지 않습니다.";
		public static final String code_130 = "bill_case 가 유효하지 않습니다.";
		public static final String code_131 = "가격정보가 유효하지 않습니다.";
		public static final String code_132 = "등록되지 않은 비밀번호이거나 잘못 입력하셨습니다.";
		public static final String code_133 = "ios_transaction_id 가 유효하지 않습니다.";
		public static final String code_134 = "이미 등록되어 있는 ID 입니다.";
		public static final String code_135 = "SiteName이 유효 하지 않습니다.";
		public static final String code_136 = "AuthNumber가 존재 하지 않습니다.";
		public static final String code_137 = "이미 등록되어 있는 전화번호 입니다.";

		public static final String code_138 = "email이 유효하지 않습니다.";
		public static final String code_198 = "요청 정보가 없거나 유효하지 않습니다.";

		public static final String code_199 = "케이블 TV 미가입자입니다.";
		public static final String code_200 = "잘못된 접근입니다.";
		public static final String code_201 = "지원되지 않는 서비스입니다.";
		public static final String code_202 = "로그인이 필요합니다.";
		public static final String code_203 = "사용 권한이 없습니다.";
		public static final String code_204 = "카드 정보가 유효하지 않습니다.";
		public static final String code_205 = "잔액이 부족합니다.";
		public static final String code_206 = "결제가 불가한 카드입니다.";
		public static final String code_207 = "결제 비밀번호가 유효하지 않습니다.";

		public static final String code_501 = "DB 접근오류가 발생하였으니 다시 수행하여 주시기 바랍니다.";
		public static final String code_502 = "요청이 처리 중입니다. 잠시 후 다시 진행해주시길 바랍니다.";
		public static final String code_502_OK = "접수되었습니다. 모바일에서 진행해주시길 바랍니다.";
		public static final String code_503 = "외부 연동에 실패했습니다.";
		public static final String code_504 = "다른 요청이 처리 중입니다. 잘못된 접근입니다.";
		public static final String code_505 = "취소 요청이 처리 중입니다.";
		public static final String code_506	= "결제가 이미 취소되었습니다.";
		public static final String code_507	= "결제가 이미 승인되었습니다. 취소가 불가능합니다.";
		public static final String code_508	= "네트워크 오류로 결제를 실패했습니다. 잠시 후 다시 시도해 주세요.";
		public static final String code_509	= "결제가 이미 실패되었습니다.";
		public static final String code_510	= "Pin 번호가 존재 하지 않습니다.";
		public static final String code_511	= "등록된 Pin 번호 입니다.";
		public static final String code_512	= "Point Event ID 가 존재 하지 않습니다.";
		public static final String code_513	= "포인트 상품 ID 가 존재 하지 않습니다.";
		public static final String code_514	= "만료된 포인트(상품) 입니다.";
		public static final String code_515	= "포인트 잔액이 부족 합니다.";
		public static final String code_516	= "신용카드 정보 확인/변경 : 홈→MY→카드변경\n※ 5회 오류 시, 신용카드 이용제한";
		public static final String code_517	= "카드사에 문의하여, 변경된 카드 비밀번호를 회원 정보에 등록해 주세요.\n(신용카드 정보 확인/변경 : 홈→MY→카드변경)";
		public static final String code_518 = "MBS 서비스 연동에 실패 했습니다.";
		public static final String code_519 = "SO별 인증서버 연동이 원할하지 않습니다.";
		public static final String code_520 = "회원인증을 사용하지 않는 SO 입니다. API Url을 확인해주세요.";

		public static final String code_999 = "요청한 작업에 오류가 있습니다.";
		public static final String GENERAL_ERROR = code_999;
	}


	public static String convert(int resultCode) {
		switch(resultCode) {
			case code_100: return MSG.code_100;//"성공"
			case code_101: return MSG.code_101;//"등록되지 않은 아이디 이거나 아이디 또는 비밀번호를 잘못 입력하셨습니다.";
			case code_102: return MSG.code_102;//"STB당 4개의 회원가입만 가능합니다 (4개 초과 된 ID)";
			case code_104: return MSG.code_104;//"문자전송이 실패했습니다.";
			case code_105: return MSG.code_105;//"입력하신 전화번호가 유효하지 않습니다.";
			case code_106: return MSG.code_106;//"transaction_id 가 유효하지 않습니다.";
			case code_107: return MSG.code_107;//"device_type 이 유효하지 않습니다.";
			case code_108: return MSG.code_108;//"wifi_mac 이 유효하지 않습니다.";
			case code_109: return MSG.code_109;//"model 이 유효하지 않습니다.";
			case code_110: return MSG.code_110;//"url_type 이 유효하지 않습니다.";
			case code_111: return MSG.code_111;//"[딜라이브 플러스 셋톱박스]의 딜라이브i에서 최초 한번 로그인을 해야 모바일 딜라이브i 사용이 가능함.";
			case code_112: return MSG.code_112;//"입력하신 아이디와 전화번호가 등록된 정보와 일치하지 않습니다. <br> 아이디와 전화번호를 확인하시고 다시 입력하시기 바랍니다";
			case code_113: return MSG.code_113;//"시스템오류 - URL_TYPE 값이 유효하지 않습니다.";
			case code_114: return MSG.code_114;//"account_id 가 유효하지 않습니다.";
			case code_115: return MSG.code_115;//"content_id 가 유효하지 않습니다.";
			case code_116: return MSG.code_116;//"content_title 가 유효하지 않습니다.";
			case code_117: return MSG.code_117;//"content_price 가 유효하지 않습니다.";
			case code_118: return MSG.code_118;//"content_period 가 유효하지 않습니다.";
			case code_119: return MSG.code_119;//"account_id  는 존재하지 않은 값입니다.";
			case code_120: return MSG.code_120;//"Product_id 가 유효하지 않습니다.";
			case code_121: return MSG.code_121;//"현재 셋탑에 이미 4개의 아이디가 등록되어 있기 때문에 새로운 아이디로 로그인이 불가합니다.";
			case code_122: return MSG.code_122;//"Apple_id 가 유효하지 않습니다.";
			case code_123: return MSG.code_123;//"Pay_date 가 유효하지 않습니다.";
			case code_124: return MSG.code_124;//"해당 TVOD상품은 이미 구매한 상품입니다.";
			case code_125: return MSG.code_125;//"해당 SVOD상품은 이미 가입한 상품입니다.";
			case code_126: return MSG.code_126;//"체험용 DLivePlus 인 경우 Dlivei 회원가입을 하실 수 없습니다.";
			case code_127: return MSG.code_127;//"Language가 유효하지 않습니다.";
			case code_128: return MSG.code_128;//"app_code 가 유효하지 않습니다.";
			case code_129: return MSG.code_129;//"bill_gubn_code 가 유효하지 않습니다.";
			case code_130: return MSG.code_130;//"bill_case 가 유효하지 않습니다.";
			case code_131: return MSG.code_131;//"가격정보가 유효하지 않습니다.";
			case code_132: return MSG.code_132;//"등록되지 않은 비밀번호이거나 잘못 입력하셨습니다.";
			case code_133: return MSG.code_133;//"ios_transaction_id 가 유효하지 않습니다.";
			case code_137: return MSG.code_137;

			case code_198: return MSG.code_198;//"요청 정보가 유효하지 않습니다.";
			case code_200: return MSG.code_200;//"잘못된 접근입니다.";
			case code_201: return MSG.code_201;//"지원되지 않는 서비스입니다.";
			case code_202: return MSG.code_202;//"로그인이 필요합니다.";
			case code_203: return MSG.code_203;//"사용 권한이 없습니다.";
			case code_204: return MSG.code_204;//"카드 정보가 유효하지 않습니다.";
			case code_205: return MSG.code_205;//"잔액이 부족합니다.";
			case code_206: return MSG.code_206;//"결제가 불가한 카드입니다.";
			case code_207: return MSG.code_207;//"결제 비밀번호가 유효하지 않습니다.";

			case code_501: return MSG.code_501; //"DB 접근오류가 발생하였으니 다시 수행하여 주시기 바랍니다.";
			case code_502: return MSG.code_502;//"요청이 처리 중입니다. 잠시 후 다시 진행해주시길 바랍니다.";
			case code_503: return MSG.code_503;//"외부 연동에 실패했습니다.";
			case code_504: return MSG.code_504;// "다른 요청이 처리 중입니다. 잘못된 접근입니다.";
			case code_505: return MSG.code_505;//"취소 요청이 처리 중입니다. 잠시 후 다시 진행해주시길 바랍니다.";
			case code_506: return MSG.code_506;
			case code_507: return MSG.code_507;
			case code_508: return MSG.code_508;//"네트워크 오류로 결제를 실패했습니다. 잠시 후 다시 시도해 주세요.";
			case code_509: return MSG.code_509;

		}
		return MSG.code_999;
	}


	public static String convert(int code, String message) {
		if(message==null || message.isEmpty()) {return convert(code);}

		return message;
	}


}
