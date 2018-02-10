package com.digikent.denetimyonetimi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.vadi.digikent.base.model.BaseEntity;

@Entity
@Table(name = "AEILTELEFON")
public class EILTelefon implements Serializable {

	@SequenceGenerator(name = "EILTelefon", sequenceName = "AEILTELEFON_ID", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EILTelefon")
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@Id
	private Long id;

	@Basic
	@Column(name = "AHR1ADRES_ID", nullable = false)
	private Long ahr1adresId;

	@Basic
	@Column(name = "BPI1ADRES_ID")
	private Long bpi1adresId;

	@Basic
	@Column(name="DAHILI", length = 20)
	private String dahili;

	@Basic
	@Column(name = "IHR1PERSONEL_ID")
	private Long ihr1personelId;

	@Basic
	@Column(name="IZAHAT",length = 100)
	private String izahat;

	@Basic
	@Column(name = "MPI1PAYDAS_ID")
	private Long mpi1paydasId;

	@Basic
	@Column(nullable = false)
	private Long telefonnumarasi;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "AEILTELEFONTURU_ID")
	private EILTelefonTuru EILTelefonTuru;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDDATE")
	protected Date updDate;

	@Column(name = "UPDSEQ")
	protected Long updSeq;

	@Column(name = "UPDUSER")
	protected Long updUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRDATE")
	protected Date crDate = new Date();

	@Column(name = "CRUSER")
	protected Long crUser;

	@Column(name = "DELETEFLAG", length = 1)
	protected String deleteFlag;

	public EILTelefon() {
	}

	public Long getAhr1adresId() {
		return ahr1adresId;
	}

	public void setAhr1adresId(Long ahr1adresId) {
		this.ahr1adresId = ahr1adresId;
	}

	public Long getBpi1adresId() {
		return bpi1adresId;
	}

	public void setBpi1adresId(Long bpi1adresId) {
		this.bpi1adresId = bpi1adresId;
	}

	public String getDahili() {
		return dahili;
	}

	public void setDahili(String dahili) {
		this.dahili = dahili;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIhr1personelId() {
		return ihr1personelId;
	}

	public void setIhr1personelId(Long ihr1personelId) {
		this.ihr1personelId = ihr1personelId;
	}

	public String getIzahat() {
		return izahat;
	}

	public void setIzahat(String izahat) {
		this.izahat = izahat;
	}

	public Long getMpi1paydasId() {
		return mpi1paydasId;
	}

	public void setMpi1paydasId(Long mpi1paydasId) {
		this.mpi1paydasId = mpi1paydasId;
	}

	public Long getTelefonnumarasi() {
		return telefonnumarasi;
	}

	public void setTelefonnumarasi(Long telefonnumarasi) {
		this.telefonnumarasi = telefonnumarasi;
	}

	public EILTelefonTuru getEILTelefonTuru() {
		return EILTelefonTuru;
	}

	public void setEILTelefonTuru(EILTelefonTuru EILTelefonTuru) {
		this.EILTelefonTuru = EILTelefonTuru;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Long getUpdSeq() {
		return updSeq;
	}

	public void setUpdSeq(Long updSeq) {
		this.updSeq = updSeq;
	}

	public Long getUpdUser() {
		return updUser;
	}

	public void setUpdUser(Long updUser) {
		this.updUser = updUser;
	}

	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public Long getCrUser() {
		return crUser;
	}

	public void setCrUser(Long crUser) {
		this.crUser = crUser;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EILTelefon other = (EILTelefon) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
