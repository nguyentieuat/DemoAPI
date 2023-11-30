package com.example.demoapi.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Business implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String businessCode;
	
	private String businessName;
	
	private int status;
	
	@CreatedDate
	@Column
    private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column
    private LocalDateTime updatedAt;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Transaction> Transaction;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserBankBusiness> setUserBankBusinessBusiness;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserDeviceBusiness> setUserDeviceBusiness;
}
