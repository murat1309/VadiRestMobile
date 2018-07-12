package com.digikent.denetimyonetimi.entity;


import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 13.02.2018.
 */
@Entity
@Table(name="BDNTDENETIMTESPITLINE")
public class BDNTDenetimTespitLine extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bdntdenetimtespitline_seq", sequenceName = "BDNTDENETIMTESPITLINE_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bdntdenetimtespitline_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    //@Column(name = "BDNTDENETIMTESPIT_ID")
    //private Long denetimTespitId;

    @ManyToOne()
    @JoinColumn(name = "BDNTDENETIMTESPIT_ID")
    private BDNTDenetimTespit bdntDenetimTespit;

    @Column(name = "LDNTTESPIT_ID")
    private Long tespitId;

    @Column(name = "TUTARI")
    private Long tutari;

    @Column(name = "NUMBERVALUE")
    private Long numberValue;

    @Column(name = "STRINGVALUE")
    private String stringValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEVALUE")
    private Date dateValue;

    @Column(name = "TEXTVALUE")
    private String textValue;

    @Column(name = "IZAHAT")
    private String izahat;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public BDNTDenetimTespit getBdntDenetimTespit() {
        return bdntDenetimTespit;
    }

    public void setBdntDenetimTespit(BDNTDenetimTespit bdntDenetimTespit) {
        this.bdntDenetimTespit = bdntDenetimTespit;
    }

    public Long getTespitId() {
        return tespitId;
    }

    public void setTespitId(Long tespitId) {
        this.tespitId = tespitId;
    }

    public Long getTutari() {
        return tutari;
    }

    public void setTutari(Long tutari) {
        this.tutari = tutari;
    }

    public Long getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Long numberValue) {
        this.numberValue = numberValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }
}
