package com.digikent.denetimyonetimi.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kadir on 26.01.2018.
 */
@Entity
@Table(name = "BDNTDENETIM")
public class BDNTDenetim extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bdntdenetim_seq", sequenceName = "BDNTDENETIM_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bdntdenetim_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DENETIMTARIHI", nullable = false)
    private Date denetimTarihi = new Date();

    //@OneToOne
    //@JoinColumn(name = "BISLISLETME_ID")
    @Column(name = "BISLISLETME_ID")
    private Long bislIsletmeId;

    @Column(name = "MPI1PAYDAS_ID")
    private Long mpi1PaydasId;

    @Column(name = "TCKIMLIKNO")
    private Long tcKimlikNo;

    @Column(name = "RRE1ILCE_OLAYYERI")
    private Long rre1IlceOlayYeri;

    @Column(name = "DRE1MAHALLE_OLAYYERI")
    private Long dre1MahalleOlayYeri;

    @Column(name = "SRE1SOKAK_OLAYYERI")
    private Long sre1SokakOlayYeri;

    @Column(name = "ERE1YAPI_OLAYYERI")
    private Long ere1YapiOlayYeri;

    @Column(name = "IRE1PARSEL_OLAYYERI")
    private Long ire1ParselOlayYeri;

    @Column(name = "DRE1BAGBOLUM_OLAYYERI")
    private Long dre1BagBolumOlayYeri;

    @Column(name = "PAFTANO_OLAYYERI")
    private String paftaOlayYeri;

    @Column(name = "ADANO_OLAYYERI")
    private String adaNoOlayYeri;

    @Column(name = "PARSELNO_OLAYYERI")
    private String parselOlayYeri;

    @Column(name = "SITEADI_OLAYYERI")
    private String siteAdiOlayYeri;

    @Column(name = "BLOKNO_OLAYYERI")
    private String blokNoOlayYeri;

    @Column(name = "KAPINOHARF_OLAYYERI")
    private String kapiNoHarfOlayYeri;

    @Column(name = "DAIRENOHARF_OLAYYERI")
    private String daireNoHarfOlayYeri;

    @Column(name = "SITEADI_TEBLIGAT")
    private String siteAdiTebligat;

    @Column(name = "BLOKNO_TEBLIGAT")
    private String blokNotebligat;

    @Column(name = "KAPINOHARF_TEBLIGAT")
    private String kapiHarfNoTebligat;

    @Column(name = "DAIRENOHARF_TEBLIGAT")
    private String daireNoHarfTebligat;

    @Column(name = "IZAHAT")
    private String izahat;

    @Column(name = "DENETIMTARAFTIPI")
    private String denetimTarafTipi;

    @Column(name = "KAPINOSAYI_OLAYYERI")
    private Long kapiNoSayiOlayYeri;

    @Column(name = "DAIRENOSAYI_OLAYYERI")
    private Long daireNoSayiOlayYeri;

    @Column(name = "RRE1ILCE_TEBLIGAT")
    private Long rre1ilceTebligat;

    @Column(name = "DRE1MAHALLE_TEBLIGAT")
    private Long dre1MahalleTebligat;

    @Column(name = "SRE1SOKAK_TEBLIGAT")
    private Long sre1SokakTebligat;

    @Column(name = "KAPINOSAYI_TEBLIGAT")
    private Long kapiNoSayiTebligat;

    @Column(name = "DAIRENOSAYI_TEBLIGAT")
    private Long daireNoSayiTebligat;

    @Column(name = "VSYNROLETEAM_ID")
    private Long vsynRoleTeamId;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getDenetimTarihi() {
        return denetimTarihi;
    }

    public void setDenetimTarihi(Date denetimTarihi) {
        this.denetimTarihi = denetimTarihi;
    }

    public Long getBislIsletmeId() {
        return bislIsletmeId;
    }

    public void setBislIsletmeId(Long bislIsletmeId) {
        this.bislIsletmeId = bislIsletmeId;
    }

    public Long getMpi1PaydasId() {
        return mpi1PaydasId;
    }

    public void setMpi1PaydasId(Long mpi1PaydasId) {
        this.mpi1PaydasId = mpi1PaydasId;
    }

    public Long getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(Long tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public Long getRre1IlceOlayYeri() {
        return rre1IlceOlayYeri;
    }

    public void setRre1IlceOlayYeri(Long rre1IlceOlayYeri) {
        this.rre1IlceOlayYeri = rre1IlceOlayYeri;
    }

    public Long getDre1MahalleOlayYeri() {
        return dre1MahalleOlayYeri;
    }

    public void setDre1MahalleOlayYeri(Long dre1MahalleOlayYeri) {
        this.dre1MahalleOlayYeri = dre1MahalleOlayYeri;
    }

    public Long getSre1SokakOlayYeri() {
        return sre1SokakOlayYeri;
    }

    public void setSre1SokakOlayYeri(Long sre1SokakOlayYeri) {
        this.sre1SokakOlayYeri = sre1SokakOlayYeri;
    }

    public Long getEre1YapiOlayYeri() {
        return ere1YapiOlayYeri;
    }

    public void setEre1YapiOlayYeri(Long ere1YapiOlayYeri) {
        this.ere1YapiOlayYeri = ere1YapiOlayYeri;
    }

    public Long getIre1ParselOlayYeri() {
        return ire1ParselOlayYeri;
    }

    public void setIre1ParselOlayYeri(Long ire1ParselOlayYeri) {
        this.ire1ParselOlayYeri = ire1ParselOlayYeri;
    }

    public Long getDre1BagBolumOlayYeri() {
        return dre1BagBolumOlayYeri;
    }

    public void setDre1BagBolumOlayYeri(Long dre1BagBolumOlayYeri) {
        this.dre1BagBolumOlayYeri = dre1BagBolumOlayYeri;
    }

    public String getPaftaOlayYeri() {
        return paftaOlayYeri;
    }

    public void setPaftaOlayYeri(String paftaOlayYeri) {
        this.paftaOlayYeri = paftaOlayYeri;
    }

    public String getAdaNoOlayYeri() {
        return adaNoOlayYeri;
    }

    public void setAdaNoOlayYeri(String adaNoOlayYeri) {
        this.adaNoOlayYeri = adaNoOlayYeri;
    }

    public String getParselOlayYeri() {
        return parselOlayYeri;
    }

    public void setParselOlayYeri(String parselOlayYeri) {
        this.parselOlayYeri = parselOlayYeri;
    }

    public String getSiteAdiOlayYeri() {
        return siteAdiOlayYeri;
    }

    public void setSiteAdiOlayYeri(String siteAdiOlayYeri) {
        this.siteAdiOlayYeri = siteAdiOlayYeri;
    }

    public String getBlokNoOlayYeri() {
        return blokNoOlayYeri;
    }

    public void setBlokNoOlayYeri(String blokNoOlayYeri) {
        this.blokNoOlayYeri = blokNoOlayYeri;
    }

    public String getKapiNoHarfOlayYeri() {
        return kapiNoHarfOlayYeri;
    }

    public void setKapiNoHarfOlayYeri(String kapiNoHarfOlayYeri) {
        this.kapiNoHarfOlayYeri = kapiNoHarfOlayYeri;
    }

    public String getDaireNoHarfOlayYeri() {
        return daireNoHarfOlayYeri;
    }

    public void setDaireNoHarfOlayYeri(String daireNoHarfOlayYeri) {
        this.daireNoHarfOlayYeri = daireNoHarfOlayYeri;
    }

    public String getSiteAdiTebligat() {
        return siteAdiTebligat;
    }

    public void setSiteAdiTebligat(String siteAdiTebligat) {
        this.siteAdiTebligat = siteAdiTebligat;
    }

    public String getBlokNotebligat() {
        return blokNotebligat;
    }

    public void setBlokNotebligat(String blokNotebligat) {
        this.blokNotebligat = blokNotebligat;
    }

    public String getKapiHarfNoTebligat() {
        return kapiHarfNoTebligat;
    }

    public void setKapiHarfNoTebligat(String kapiHarfNoTebligat) {
        this.kapiHarfNoTebligat = kapiHarfNoTebligat;
    }

    public String getDaireNoHarfTebligat() {
        return daireNoHarfTebligat;
    }

    public void setDaireNoHarfTebligat(String daireNoHarfTebligat) {
        this.daireNoHarfTebligat = daireNoHarfTebligat;
    }

    public String getIzahat() {
        return izahat;
    }

    public void setIzahat(String izahat) {
        this.izahat = izahat;
    }

    public String getDenetimTarafTipi() {
        return denetimTarafTipi;
    }

    public void setDenetimTarafTipi(String denetimTarafTipi) {
        this.denetimTarafTipi = denetimTarafTipi;
    }

    public Long getKapiNoSayiOlayYeri() {
        return kapiNoSayiOlayYeri;
    }

    public void setKapiNoSayiOlayYeri(Long kapiNoSayiOlayYeri) {
        this.kapiNoSayiOlayYeri = kapiNoSayiOlayYeri;
    }

    public Long getDaireNoSayiOlayYeri() {
        return daireNoSayiOlayYeri;
    }

    public void setDaireNoSayiOlayYeri(Long daireNoSayiOlayYeri) {
        this.daireNoSayiOlayYeri = daireNoSayiOlayYeri;
    }

    public Long getRre1ilceTebligat() {
        return rre1ilceTebligat;
    }

    public void setRre1ilceTebligat(Long rre1ilceTebligat) {
        this.rre1ilceTebligat = rre1ilceTebligat;
    }

    public Long getDre1MahalleTebligat() {
        return dre1MahalleTebligat;
    }

    public void setDre1MahalleTebligat(Long dre1MahalleTebligat) {
        this.dre1MahalleTebligat = dre1MahalleTebligat;
    }

    public Long getSre1SokakTebligat() {
        return sre1SokakTebligat;
    }

    public void setSre1SokakTebligat(Long sre1SokakTebligat) {
        this.sre1SokakTebligat = sre1SokakTebligat;
    }

    public Long getKapiNoSayiTebligat() {
        return kapiNoSayiTebligat;
    }

    public void setKapiNoSayiTebligat(Long kapiNoSayiTebligat) {
        this.kapiNoSayiTebligat = kapiNoSayiTebligat;
    }

    public Long getDaireNoSayiTebligat() {
        return daireNoSayiTebligat;
    }

    public void setDaireNoSayiTebligat(Long daireNoSayiTebligat) {
        this.daireNoSayiTebligat = daireNoSayiTebligat;
    }

    public Long getVsynRoleTeamId() {
        return vsynRoleTeamId;
    }

    public void setVsynRoleTeamId(Long vsynRoleTeamId) {
        this.vsynRoleTeamId = vsynRoleTeamId;
    }
}
