package com.digikent.denetimyonetimi.dto.denetimtespit;

/**
 * Created by Kadir on 23.02.2018.
 */
public class DenetimTespitDTO {

    private Long id;
    private Long denetimId;
    private Long denetimTuruId;
    private String denetimTuruAdi;
    private Long tespitGrubuId;
    private String tespitGrubuAdi;
    private Long roleTeamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDenetimId() {
        return denetimId;
    }

    public void setDenetimId(Long denetimId) {
        this.denetimId = denetimId;
    }

    public Long getDenetimTuruId() {
        return denetimTuruId;
    }

    public void setDenetimTuruId(Long denetimTuruId) {
        this.denetimTuruId = denetimTuruId;
    }

    public String getDenetimTuruAdi() {
        return denetimTuruAdi;
    }

    public void setDenetimTuruAdi(String denetimTuruAdi) {
        this.denetimTuruAdi = denetimTuruAdi;
    }

    public Long getTespitGrubuId() {
        return tespitGrubuId;
    }

    public void setTespitGrubuId(Long tespitGrubuId) {
        this.tespitGrubuId = tespitGrubuId;
    }

    public String getTespitGrubuAdi() {
        return tespitGrubuAdi;
    }

    public void setTespitGrubuAdi(String tespitGrubuAdi) {
        this.tespitGrubuAdi = tespitGrubuAdi;
    }

    public Long getRoleTeamId() {
        return roleTeamId;
    }

    public void setRoleTeamId(Long roleTeamId) {
        this.roleTeamId = roleTeamId;
    }
}
