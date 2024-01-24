package com.castis.pvs.api.service;
import com.castis.pvs.api.model.v2.request.CloseAccountRequest;
import com.castis.pvs.api.model.v2.response.OpenApiResponse;
import com.castis.pvs.api.repository.MemberRepository;
import com.castis.pvs.connector.MBSConnector;
import com.castis.pvs.member.dao.MemberDao;
import com.castis.pvs.member.dto.MemberDTO;
import com.castis.pvs.member.entity.Member;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Disabled
class OpenApiServiceTest {
    @InjectMocks
    private OpenApiService openApiService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberDao memberDao;

    @Mock
    private MBSConnector mbsConnector;
    @Test
    @Disabled
    public void testSaveMember() throws Exception {
        MemberDTO member= MemberDTO.builder()
                .member_id("admin")
                .password("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                .user_name("장민성")
                .social_number("990814-1")
                .tel("010-1234-5678")
                .address_city("서울 관악구 남부순환로 1931")
                .address_dist("xxx-xxx")
                .adult_cert("1")
                .app_code("2")
                .mso_name("CMB")
                .email("test@gmail.com")
                .recommender_id("test")
                .ci("1234567890123456789012345678901234567890123456789012345678901234")
                .di("1234567890123456789012345678901234567890123456789012345678901234")
                .legal_name("초이스")
                .legal_social_number("990814-1")
                .legal_ci("1234567890123456789012345678901234567890123456789012345678901234")
                .legal_di("1234567890123456789012345678901234567890123456789012345678901234")
                .legal_tel("010-4567-1234")
                .so_happycall_auth(0)
                .so_happycall_update_date(LocalDateTime.now())
                .market_text_yn(0)
                .market_email_yn(0)
                .market_tm_yn(0)
                .push_yn(0).build();

        when(memberDao.saveMember(any(Member.class))).thenReturn(1234L);
        OpenApiResponse response = openApiService.saveMember(member);
        assertThat(response).isNotNull();
        assertThat(response.getResultCode()).isEqualTo(200);
        System.out.println("message : " + response.getResultMessage());
        verify(memberDao, times(1)).saveMember(any(Member.class));
    }

    @Test
    public void testCloseAccount() throws Exception {
        CloseAccountRequest request = CloseAccountRequest.builder().member_id("admin").ci("1234567890123456789012345678901234567890123456789012345678901234").build();
        Member member = new Member();
        member.setCi("1234567890123456789012345678901234567890123456789012345678901234");
        member.setMember_id("admin");
        when(memberDao.getMemberbyMemberId(any(String.class))).thenReturn(member);
        doNothing().when(memberDao).deleteMember(any(Member.class));
        OpenApiResponse response = openApiService.closeAccount(request);
        assertThat(response).isNotNull();
        assertThat(response.getResultCode()).isEqualTo(200);
    }
}
