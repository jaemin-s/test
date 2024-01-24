package com.castis.pvs.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "site_info")
@Getter
@Setter
@NoArgsConstructor
public class SiteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String mso_name;

    private String so_id;
    private String protocol;
    private String ip;
    private int port;
    private String sign_up_url;
    private String happy_call_url;
    private Boolean sign_up_use;
    private Boolean happy_call_use;

    private Boolean encrypt_use;

    private String encrypt_key;
    private String encrypt_128key;



}
