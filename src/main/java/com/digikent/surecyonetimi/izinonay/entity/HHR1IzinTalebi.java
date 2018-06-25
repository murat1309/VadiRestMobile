package com.digikent.surecyonetimi.izinonay.entity;

import com.digikent.denetimyonetimi.entity.IHR1Personel;
import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HHR1IZINTALEBI")
public class HHR1IzinTalebi extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "hhr1izintalebi_seq", sequenceName = "HHR1IZINTALEBI_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hhr1izintalebi_seq")
    @Column(name = "ID")
    private Long ID;

    @JoinColumn(name = "IHR1PERSONEL_ID")
    @OneToOne
    private IHR1Personel ihr1Personel;

    @Column(name = "BASLAMATARIHI")
    private Date baslamaTarihi;

    @Column(name = "BITISTARIHI")
    private Date bitisTarihi;

    @Column(name = "DONUSTARIHI")
    private Date donusTarihi;

    @Column(name = "IZINADRES")
    private String izinAdres;

    @Column(name = "OLAYUNVAN")
    private String olayUnvan;

    @Column(name = "TEKLIFUNVAN")
    private String teklifUnvan;

    @Column(name = "YERINEBAKACAKUNVAN")
    private String yerineBakacakUnvan;

    @Column(name = "IHR1PERSONEL_ARZEDEN")
    private Long ihr1PersonelArzeden;

    @Column(name = "ARZUNVAN")
    private String arzUnvan;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "VBPMPROCESSINSTANCE_ID")
    private Long vbpmProcessInstanceId;

    @Column(name = "SURECBASLAMATARIHI")
    private Date surecBaslamaTarihi;

    @Column(name = "SURECBITISTARIHI")
    private Date surecBitisTarihi;

    @Column(name = "CEPTELEFONU")
    private Long cepTelefonu;

    @Column(name = "THR1IZINTALEBIDURUMU_ID")
    private Long thr1IzinTalebiDurumuId;

    @Column(name = "THR1IZINSONUCDURUMU_ID")
    private Long thr1IzinSonucDurumuId;

    @Column(name = "THR1IZINSURECTURU_ID")
    private Long thr1IzinSurecTuruId;

    @JoinColumn(name = "BSM2SERVIS_GOREV")
    @OneToOne
    private BSM2Servis bsm2Servis;

    @Column(name = "BSM2SERVIS_KADRO")
    private Long bsm2ServisKadro;

    @Column(name = "IZINBASLAMADAKIKA")
    private Date izinBaslamaDakika;

    @Column(name = "IZINBITISDAKIKA")
    private Date izinBitisDakika;

    @JoinColumn(name = "YHR1IZINTURU_ID")
    @OneToOne
    private YHR1IzinTuru yhr1IzinTuru;

    @Column(name = "IZINBASLAMASAAT")
    private Date izinBaslamaSaat;

    @Column(name = "IZINBITISSAAT")
    private Date izinBitisSaat;

    @Column(name = "IPTALBITISTARIHI")
    private Date iptalBitisTarihi;

    @Column(name = "IPTALBASLAMATARIHI")
    private Date iptalBaslamaTarihi;

    @Column(name = "IPTALBASLAMASAATI")
    private Date iptalBaslamaSaati;

    @Column(name = "IPTALBITISSAATI")
    private Date iptalBitisSaati;

    @Column(name = "MASTERID")
    private Long masterId;

    @JoinColumn(name = "IHR1PERSONELYERINEBAKACAK_ID")
    @OneToOne
    private IHR1Personel ihr1PersonelYerineBakacak;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public IHR1Personel getIhr1Personel() {
        return ihr1Personel;
    }

    public void setIhr1Personel(IHR1Personel ihr1Personel) {
        this.ihr1Personel = ihr1Personel;
    }

    public Date getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(Date baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Date bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public Date getDonusTarihi() {
        return donusTarihi;
    }

    public void setDonusTarihi(Date donusTarihi) {
        this.donusTarihi = donusTarihi;
    }

    public String getIzinAdres() {
        return izinAdres;
    }

    public void setIzinAdres(String izinAdres) {
        this.izinAdres = izinAdres;
    }

    public String getOlayUnvan() {
        return olayUnvan;
    }

    public void setOlayUnvan(String olayUnvan) {
        this.olayUnvan = olayUnvan;
    }

    public String getTeklifUnvan() {
        return teklifUnvan;
    }

    public void setTeklifUnvan(String teklifUnvan) {
        this.teklifUnvan = teklifUnvan;
    }

    public String getYerineBakacakUnvan() {
        return yerineBakacakUnvan;
    }

    public void setYerineBakacakUnvan(String yerineBakacakUnvan) {
        this.yerineBakacakUnvan = yerineBakacakUnvan;
    }

    public Long getIhr1PersonelArzeden() {
        return ihr1PersonelArzeden;
    }

    public void setIhr1PersonelArzeden(Long ihr1PersonelArzeden) {
        this.ihr1PersonelArzeden = ihr1PersonelArzeden;
    }

    public String getArzUnvan() {
        return arzUnvan;
    }

    public void setArzUnvan(String arzUnvan) {
        this.arzUnvan = arzUnvan;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public Long getVbpmProcessInstanceId() {
        return vbpmProcessInstanceId;
    }

    public void setVbpmProcessInstanceId(Long vbpmProcessInstanceId) {
        this.vbpmProcessInstanceId = vbpmProcessInstanceId;
    }

    public Date getSurecBaslamaTarihi() {
        return surecBaslamaTarihi;
    }

    public void setSurecBaslamaTarihi(Date surecBaslamaTarihi) {
        this.surecBaslamaTarihi = surecBaslamaTarihi;
    }

    public Date getSurecBitisTarihi() {
        return surecBitisTarihi;
    }

    public void setSurecBitisTarihi(Date surecBitisTarihi) {
        this.surecBitisTarihi = surecBitisTarihi;
    }

    public Long getCepTelefonu() {
        return cepTelefonu;
    }

    public void setCepTelefonu(Long cepTelefonu) {
        this.cepTelefonu = cepTelefonu;
    }

    public Long getThr1IzinTalebiDurumuId() {
        return thr1IzinTalebiDurumuId;
    }

    public void setThr1IzinTalebiDurumuId(Long thr1IzinTalebiDurumuId) {
        this.thr1IzinTalebiDurumuId = thr1IzinTalebiDurumuId;
    }

    public Long getThr1IzinSonucDurumuId() {
        return thr1IzinSonucDurumuId;
    }

    public void setThr1IzinSonucDurumuId(Long thr1IzinSonucDurumuId) {
        this.thr1IzinSonucDurumuId = thr1IzinSonucDurumuId;
    }

    public Long getThr1IzinSurecTuruId() {
        return thr1IzinSurecTuruId;
    }

    public void setThr1IzinSurecTuruId(Long thr1IzinSurecTuruId) {
        this.thr1IzinSurecTuruId = thr1IzinSurecTuruId;
    }

    public BSM2Servis getBsm2Servis() {
        return bsm2Servis;
    }

    public void setBsm2Servis(BSM2Servis bsm2Servis) {
        this.bsm2Servis = bsm2Servis;
    }

    public Long getBsm2ServisKadro() {
        return bsm2ServisKadro;
    }

    public void setBsm2ServisKadro(Long bsm2ServisKadro) {
        this.bsm2ServisKadro = bsm2ServisKadro;
    }

    public Date getIzinBaslamaDakika() {
        return izinBaslamaDakika;
    }

    public void setIzinBaslamaDakika(Date izinBaslamaDakika) {
        this.izinBaslamaDakika = izinBaslamaDakika;
    }

    public Date getIzinBitisDakika() {
        return izinBitisDakika;
    }

    public void setIzinBitisDakika(Date izinBitisDakika) {
        this.izinBitisDakika = izinBitisDakika;
    }

    public YHR1IzinTuru getYhr1IzinTuru() {
        return yhr1IzinTuru;
    }

    public void setYhr1IzinTuru(YHR1IzinTuru yhr1IzinTuru) {
        this.yhr1IzinTuru = yhr1IzinTuru;
    }

    public Date getIzinBaslamaSaat() {
        return izinBaslamaSaat;
    }

    public void setIzinBaslamaSaat(Date izinBaslamaSaat) {
        this.izinBaslamaSaat = izinBaslamaSaat;
    }

    public Date getIzinBitisSaat() {
        return izinBitisSaat;
    }

    public void setIzinBitisSaat(Date izinBitisSaat) {
        this.izinBitisSaat = izinBitisSaat;
    }

    public Date getIptalBitisTarihi() {
        return iptalBitisTarihi;
    }

    public void setIptalBitisTarihi(Date iptalBitisTarihi) {
        this.iptalBitisTarihi = iptalBitisTarihi;
    }

    public Date getIptalBaslamaTarihi() {
        return iptalBaslamaTarihi;
    }

    public void setIptalBaslamaTarihi(Date iptalBaslamaTarihi) {
        this.iptalBaslamaTarihi = iptalBaslamaTarihi;
    }

    public Date getIptalBaslamaSaati() {
        return iptalBaslamaSaati;
    }

    public void setIptalBaslamaSaati(Date iptalBaslamaSaati) {
        this.iptalBaslamaSaati = iptalBaslamaSaati;
    }

    public Date getIptalBitisSaati() {
        return iptalBitisSaati;
    }

    public void setIptalBitisSaati(Date iptalBitisSaati) {
        this.iptalBitisSaati = iptalBitisSaati;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public IHR1Personel getIhr1PersonelYerineBakacak() {
        return ihr1PersonelYerineBakacak;
    }

    public void setIhr1PersonelYerineBakacak(IHR1Personel ihr1PersonelYerineBakacak) {
        this.ihr1PersonelYerineBakacak = ihr1PersonelYerineBakacak;
    }
}
