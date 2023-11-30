package com.example.demoapi.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserDeviceBusiness implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "businessCode", referencedColumnName = "businessCode")
	private Business business;
	
	private String deviceCode;
	private String deviceName;
	
	@CreatedDate
	@Column
    private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column
    private LocalDateTime updatedAt;
}
