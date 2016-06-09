package com.digikent.vadirest.dto;



public class BelgeBasvuruDetay {
	
	private long id ;
	private long referansNo ;
	private String tip;
	private long disaridanGelenBelgeNo;
	private long defterKaydiGirisNo;
	private String Tarih;
	private String tarihSaat;
	private String belgeTarih;
	private String uretimTipi;

	private long edm1IsAkisiAdimId;
	private String merciKurum;
	private String adi;
	private String soyadi;
	private String konusu;
	private String sdpKodu;
	private String ekBilgi;
	private String isAkisKonuOzeti;
	private String izahat;
	private String gizlilik;
	private String onemDerecesi;
	private long ekliSayfaSayisi;
	private String digerEkler;
	private String dosyaNo;
	private String beklenenBitisTarihi;
	private String belgeDurumu ;
	private String evrakiGetiren;
	
	private String mudurluk;
	private long bsm2ServisSeflik;
	private long msm2OrganizasyonId;
	private long msm2OrganizasyonIdPilot;
	private long ihr1PersonelId;
	
	private long telefonNumarasi ;
	private long isTelefonu ;
	private long cepTelefonu ;
	private String elektronikPosta ;
	private String geriDonusYapilsinMi;
	private String geriBildirimTuru ;
	private String geriBildirimYapildi;
	
	private String ilce;
	private String mahalle;
	
	private String babaAdi;
	private long tcKimlikNo;
	private String bildirimdeBulunan;
	private long adm1BildirimTuruId;
	
	private String bildirimTuru;
	private String bildirimNiteligi;
	
	public String getBildirimTuru() {
		return bildirimTuru;
	}
	public void setBildirimTuru(String bildirimTuru) {
		this.bildirimTuru = bildirimTuru;
	}
	public String getBildirimNiteligi() {
		return bildirimNiteligi;
	}
	public void setBildirimNiteligi(String bildirimNiteligi) {
		this.bildirimNiteligi = bildirimNiteligi;
	}
	public String getBabaAdi() {
		return babaAdi;
	}
	public void setBabaAdi(String babaAdi) {
		this.babaAdi = babaAdi;
	}
	public long getTcKimlikNo() {
		return tcKimlikNo;
	}
	public void setTcKimlikNo(long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}
	public String getBildirimdeBulunan() {
		return bildirimdeBulunan;
	}
	public void setBildirimdeBulunan(String bildirimdeBulunan) {
		this.bildirimdeBulunan = bildirimdeBulunan;
	}
	public long getAdm1BildirimTuruId() {
		return adm1BildirimTuruId;
	}
	public void setAdm1BildirimTuruId(long adm1BildirimTuruId) {
		this.adm1BildirimTuruId = adm1BildirimTuruId;
	}
	public String getIlce() {
		return ilce;
	}
	public void setIlce(String ilce) {
		this.ilce = ilce;
	}
	public String getMahalle() {
		return mahalle;
	}
	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}
	public long getTelefonNumarasi() {
		return telefonNumarasi;
	}
	public void setTelefonNumarasi(long telefonNumarasi) {
		this.telefonNumarasi = telefonNumarasi;
	}
	public long getIsTelefonu() {
		return isTelefonu;
	}
	public void setIsTelefonu(long isTelefonu) {
		this.isTelefonu = isTelefonu;
	}
	public long getCepTelefonu() {
		return cepTelefonu;
	}
	public void setCepTelefonu(long cepTelefonu) {
		this.cepTelefonu = cepTelefonu;
	}
	public String getElektronikPosta() {
		return elektronikPosta;
	}
	public void setElektronikPosta(String elektronikPosta) {
		this.elektronikPosta = elektronikPosta;
	}
	public String getGeriDonusYapilsinMi() {
		return geriDonusYapilsinMi;
	}
	public void setGeriDonusYapilsinMi(String geriDonusYapilsinMi) {
		this.geriDonusYapilsinMi = geriDonusYapilsinMi;
	}
	public String getGeriBildirimTuru() {
		return geriBildirimTuru;
	}
	public void setGeriBildirimTuru(String geriBildirimTuru) {
		this.geriBildirimTuru = geriBildirimTuru;
	}
	public String getGeriBildirimYapildi() {
		return geriBildirimYapildi;
	}
	public void setGeriBildirimYapildi(String geriBildirimYapildi) {
		this.geriBildirimYapildi = geriBildirimYapildi;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getReferansNo() {
		return referansNo;
	}
	public void setReferansNo(long referansNo) {
		this.referansNo = referansNo;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public long getDisaridanGelenBelgeNo() {
		return disaridanGelenBelgeNo;
	}
	public void setDisaridanGelenBelgeNo(long disaridanGelenBelgeNo) {
		this.disaridanGelenBelgeNo = disaridanGelenBelgeNo;
	}
	public long getDefterKaydiGirisNo() {
		return defterKaydiGirisNo;
	}
	public void setDefterKaydiGirisNo(long defterKaydiGirisNo) {
		this.defterKaydiGirisNo = defterKaydiGirisNo;
	}
	public String getTarih() {
		return Tarih;
	}
	public void setTarih(String tarih) {
		Tarih = tarih;
	}
	public String getTarihSaat() {
		return tarihSaat;
	}
	public void setTarihSaat(String tarihSaat) {
		this.tarihSaat = tarihSaat;
	}
	public String getBelgeTarih() {
		return belgeTarih;
	}
	public void setBelgeTarih(String belgeTarih) {
		this.belgeTarih = belgeTarih;
	}
	public String getUretimTipi() {
		return uretimTipi;
	}
	public void setUretimTipi(String uretimTipi) {
		this.uretimTipi = uretimTipi;
	}
	public long getEdm1IsAkisiAdimId() {
		return edm1IsAkisiAdimId;
	}

	public void setEdm1IsAkisiAdimId(long edm1IsAkisiAdimId) {
		this.edm1IsAkisiAdimId = edm1IsAkisiAdimId;
	}
	public String getMerciKurum() {
		return merciKurum;
	}
	public void setMerciKurum(String merciKurum) {
		this.merciKurum = merciKurum;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = adi;
	}
	public String getSoyadi() {
		return soyadi;
	}
	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}
	public String getKonusu() {
		return konusu;
	}
	public void setKonusu(String konusu) {
		this.konusu = konusu;
	}
	public String getSdpKodu() {
		return sdpKodu;
	}
	public void setSdpKodu(String sdpKodu) {
		this.sdpKodu = sdpKodu;
	}
	public String getEkBilgi() {
		return ekBilgi;
	}
	public void setEkBilgi(String ekBilgi) {
		this.ekBilgi = ekBilgi;
	}
	public String getIsAkisKonuOzeti() {
		return isAkisKonuOzeti;
	}
	public void setIsAkisKonuOzeti(String isAkisKonuOzeti) {
		this.isAkisKonuOzeti = isAkisKonuOzeti;
	}
	public String getIzahat() {
		return izahat;
	}
	public void setIzahat(String izahat) {
		this.izahat = izahat;
	}
	public String getGizlilik() {
		return gizlilik;
	}
	public void setGizlilik(String gizlilik) {
		this.gizlilik = gizlilik;
	}
	public String getOnemDerecesi() {
		return onemDerecesi;
	}
	public void setOnemDerecesi(String onemDerecesi) {
		this.onemDerecesi = onemDerecesi;
	}
	public long getEkliSayfaSayisi() {
		return ekliSayfaSayisi;
	}
	public void setEkliSayfaSayisi(long ekliSayfaSayisi) {
		this.ekliSayfaSayisi = ekliSayfaSayisi;
	}
	public String getDigerEkler() {
		return digerEkler;
	}
	public void setDigerEkler(String digerEkler) {
		this.digerEkler = digerEkler;
	}
	public String getDosyaNo() {
		return dosyaNo;
	}
	public void setDosyaNo(String dosyaNo) {
		this.dosyaNo = dosyaNo;
	}
	public String getBeklenenBitisTarihi() {
		return beklenenBitisTarihi;
	}
	public void setBeklenenBitisTarihi(String beklenenBitisTarihi) {
		this.beklenenBitisTarihi = beklenenBitisTarihi;
	}
	public String getBelgeDurumu() {
		return belgeDurumu;
	}
	public void setBelgeDurumu(String belgeDurumu) {
		this.belgeDurumu = belgeDurumu;
	}
	public String getEvrakiGetiren() {
		return evrakiGetiren;
	}
	public void setEvrakiGetiren(String evrakiGetiren) {
		this.evrakiGetiren = evrakiGetiren;
	}
	public String getMudurluk() {
		return mudurluk;
	}
	public void setMudurluk(String mudurluk) {
		this.mudurluk = mudurluk;
	}
	public long getBsm2ServisSeflik() {
		return bsm2ServisSeflik;
	}
	public void setBsm2ServisSeflik(long bsm2ServisSeflik) {
		this.bsm2ServisSeflik = bsm2ServisSeflik;
	}
	public long getMsm2OrganizasyonId() {
		return msm2OrganizasyonId;
	}
	public void setMsm2OrganizasyonId(long msm2OrganizasyonId) {
		this.msm2OrganizasyonId = msm2OrganizasyonId;
	}
	public long getMsm2OrganizasyonIdPilot() {
		return msm2OrganizasyonIdPilot;
	}
	public void setMsm2OrganizasyonIdPilot(long msm2OrganizasyonIdPilot) {
		this.msm2OrganizasyonIdPilot = msm2OrganizasyonIdPilot;
	}
	public long getIhr1PersonelId() {
		return ihr1PersonelId;
	}
	public void setIhr1PersonelId(long ihr1PersonelId) {
		this.ihr1PersonelId = ihr1PersonelId;
	}

}
