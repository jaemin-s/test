package com.castis.pvs.member.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.castis.pvs.member.dto.NewDeviceDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_new_device")
@Getter
@Setter
@NoArgsConstructor
public class NewDevice {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private long id;

    private String account_id;
    private String device_info;
    private String device_type;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @PrePersist 
    protected void onCreate() {
        create_time = LocalDateTime.now();
    }

    public NewDevice(NewDeviceDTO newDeviceDTO) {
        this.id = newDeviceDTO.getId();
        this.account_id = newDeviceDTO.getAccountId();
        this.device_info = newDeviceDTO.getDeviceInfo();
        this.device_type = newDeviceDTO.getDeviceType();
    }

}