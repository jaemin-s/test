package com.castis.pvs.api.model.v2.response;

import com.castis.pvs.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HappyCallMember {
    @Schema(description = "회원 아이디", example = "homechoiceUser1", required = true)
    private String member_id;
    @Schema(description = "본인확인기관이 주민등록번호를 암호화하여 생성한 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0")
    private String ci;
    @Schema(description = "회원이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String user_name;
    @Schema(description = "주민번호 앞자리 + 뒷자리 첫숫자", example = "900101-1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String social_number;
    @Schema(description = "주소", example = "서울시 강남구")
    private String address_city;
    @Schema(description = "상세주소", example = "역삼동 123-456")
    private String address_dist;
    @Schema(description = "회원의 핸드폰번호", example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tel;

    public HappyCallMember(Member member) {
        this.member_id = member.getMember_id();
        this.ci = member.getCi();
        this.user_name = member.getUser_name();
        this.social_number = member.getSocial_number();
        this.address_city = member.getAddress_city();
        this.address_dist = member.getAddress_dist();
        this.tel = member.getTel();
    }
}
