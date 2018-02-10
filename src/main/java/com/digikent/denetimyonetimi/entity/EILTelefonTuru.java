package com.digikent.denetimyonetimi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.vadi.digikent.base.model.BaseEntity;

@Entity
@Table(name = "AEILTELEFONTURU")
public class EILTelefonTuru implements Serializable {
	
	@SequenceGenerator(name = "EILTelefonTuru", sequenceName = "AEILTELEFONTURU_ID", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EILTelefonTuru")
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@Id
	private Long id;
	
	@Column(nullable = false, length = 1, name="ADRESZORUNLU")
    private String adreszorunlu;
    
    @Column(length = 10, name="KAYITOZELISMI")
    private String kayitozelismi;
    
    @Column(length = 50, name="KISALTMA")
    private String kisaltma;
    
    @Column(nullable = false, length = 50, name="TANIM")
    private String tanim;

    @OneToMany(mappedBy = "EILTelefonTuru", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<EILTelefon> EILTelefonList;

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

    public EILTelefonTuru() {
    }


    public String getAdreszorunlu() {
        return adreszorunlu;
    }

    public void setAdreszorunlu(String adreszorunlu) {
        this.adreszorunlu = adreszorunlu;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKayitozelismi() {
        return kayitozelismi;
    }

    public void setKayitozelismi(String kayitozelismi) {
        this.kayitozelismi = kayitozelismi;
    }

    public String getKisaltma() {
        return kisaltma;
    }

    public void setKisaltma(String kisaltma) {
        this.kisaltma = kisaltma;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public List<EILTelefon> getEILTelefonList() {
        return EILTelefonList;
    }

    public void setEILTelefonList(List<EILTelefon> EILTelefonList) {
        this.EILTelefonList = EILTelefonList;
    }

    public EILTelefon addEILTelefon(EILTelefon EILTelefon) {
        getEILTelefonList().add(EILTelefon);
        EILTelefon.setEILTelefonTuru(this);
        return EILTelefon;
    }

    public EILTelefon removeEILTelefon(EILTelefon EILTelefon) {
        getEILTelefonList().remove(EILTelefon);
        EILTelefon.setEILTelefonTuru(null);
        return EILTelefon;
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
		EILTelefonTuru other = (EILTelefonTuru) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return tanim;
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
}
