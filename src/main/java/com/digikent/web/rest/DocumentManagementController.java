package com.digikent.web.rest;

import com.digikent.vadirest.dto.*;
import com.digikent.vadirest.service.DocumentManagementService;
import com.digikent.web.rest.dto.DocumentRejectDTO;
import com.vadi.digikent.sistem.syn.model.SM1Roles;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpStatus.OK;


@RestController
//@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/belgeYonetim")
@PropertySources({ @PropertySource(value = { "file:${DIGIKENT_PATH}/services/baseUrl.properties" }) })
public class DocumentManagementController {

	private final Logger LOG = LoggerFactory.getLogger(DocumentManagementController.class);

	@Autowired(required=true)
	private DocumentManagementService documentManagementService;

	@Autowired
	private Environment environment;

    private static RestTemplate restTemplate = new RestTemplate();

	
	//belge yonetim rol listesi
	@RequestMapping(value = "" +
			"/{persid}",method = RequestMethod.GET)
	public List<Rol> getEBYSRollList(@PathVariable("persid") long persid){
		System.out.println("--------get roll list---------");
		System.out.println(persid);

		List<SM1Roles> retval = documentManagementService.getEBYSRollList(persid);
		List<Rol> rolList= new ArrayList();
		for(int i=0 ; i<retval.size();i++){
			Rol rol = new Rol();
			rol.setId(retval.get(i).getId());
			rol.setTanim(retval.get(i).getTanim());
			rolList.add(rol);
			System.out.println(retval.get(i).getTanim());
		}
		return rolList;
	}

	/**
	 * Ebys paraf onay bekleyen paraflar
	 * Bu method ile paraflanabilir elektronik belgeler icin onay durumu onaybekliyor olan belgeler getirilir.
	 *
	 * @param persid IHR1PERSONEL personel tablosundaki ilgili id'ye sahip kisiyi belirtir.
	 * @param rolid FSM1ROLES tablosundaki rol id'yi belirtir.
	 * @param startDate paraflanabilir dokumanlar icin belirtilen baslangıc tarihine gore arama islemi gerceklestir
	 * @param endDate  paraflanabilir dokumanlar icin belirtilen bitis tarihine gore arama islemi gerceklestirir
	 * @return List<EBYS> objesi cevap olarak gonderilir
	 */
	@RequestMapping(value = "ebys/paraf/onaybekliyor/{persid}/{rolid}/{startDate}/{endDate}")
    public List<EBYS> getWaitingEBYSParaf(
    		@PathVariable("persid") long persid,
			@PathVariable("rolid") long rolid,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {

		LOG.debug("Rest Request to get ebys paraf onay bekleyen documents persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYSParaf("ONAYBEKLIYOR", persid, rolid, startDate, endDate);
	}

	/**
	 * Ebys paraf onay bekleyen paraflar detay
	 * Bu method ile paraflanabilir elektronik belgeler icin onay durumu onaybekliyor olan belgelerin detayi getirilir
	 *
	 * @param documentId EBYSDOCUMENT tablosundaki id'yi belirtir
	 * @return List<EBYSParafDetailDTO>
	 */
	@RequestMapping(value = "ebys/paraf/onaybekliyor/detay/{documentId}")
	public List<EBYSParafDetailDTO> getWaitingEBYSParafDetail(@PathVariable("documentId") long documentId) {
		LOG.debug("Rest Request to get ebys paraf onaybekliyor documents detail with documentId: " + documentId);
		return documentManagementService.getEBYSParafDetail("ONAYBEKLIYOR", documentId);
	}

	/**
	 *  Ebys paraf onaybekliyor ek bilgi ve belge
	 * Bu method ile paraflanabilir elektronik belgeler icin onay durumu onaybekliyor olan belgelere ait ek bilgi ve belgeler getirilir
	 *
	 * @param documentId EBYSDOCUMENT tablosundaki id'yi belirtir
	 * @return List<EBYSParafDetailDTO>
	 */
	@RequestMapping(value = "ebys/paraf/onaybekliyor/ek/detay/{documentId}")
	public List<EBYSParafDetailDTO> getWaitingEBYSParafEkDetail(@PathVariable("documentId") long documentId) {
		LOG.debug("Rest Request to get ebys paraf onaybekliyor ek belge document detail with documentId: " + documentId);
		return documentManagementService.getWaitingEBYSParafEkDetail(documentId);
	}

	/**
	 * Ebys paraf onaylanmis paraflar
	 * Bu method ile paraflanabilir elektronik belgeler icin onay durumu onaylandi olan belgeler getirilir.
	 * @param persid IHR1PERSONEL personel tablosundaki ilgili id'ye sahip kisiyi belirtir.
	 * @param rolid FSM1ROLES tablosundaki rol id'yi belirtir.
	 * @param startDate paraflanabilir dokumanlar icin belirtilen baslangıc tarihine gore arama islemi gerceklestir
	 * @param endDate  paraflanabilir dokumanlar icin belirtilen bitis tarihine gore arama islemi gerceklestirir
	 * @return EBYS objesi cevap olarak gonderilir
	 */
	@RequestMapping(value = "ebys/paraf/onaylandi/{persid}/{rolid}/{startDate}/{endDate}")
	public List<EBYS> getCompletedEBYSParaf(
			@PathVariable("persid") long persid,
			@PathVariable("rolid") long rolid,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {

		LOG.debug("Rest Request to get ebys paraf onaylanan documents persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYSParaf("ONAYLANDI", persid, rolid, startDate, endDate);
	}

	/**
	 * Ebys paraf onaylanmis paraflar detay
	 * Method paraflanabilir elektronik belgeler icin onay durumu onaylandi olan belgelerin detayini icereb bilgileri getirir
	 * @param documentId
	 * @return List<EBYSParafDetailDTO>
	 */
	@RequestMapping(value = "ebys/paraf/onaylandi/detay/{documentId}")
	public List<EBYSParafDetailDTO> getCompletedEBYSParafDetail(@PathVariable("documentId") long documentId) {
		LOG.debug("Rest Request to get ebys paraf onaylanmis documents detail with documentId: " + documentId);
		return documentManagementService.getEBYSParafDetail("ONAYLANDI", documentId);
	}

	//EBYS bekleyen belge
	@RequestMapping(value="EBYSBekleyen/{persid}/{rolid}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<EBYS> getWaitingEBYS(@PathVariable("persid") long persid,
									 @PathVariable("rolid") long rolid,
									 @PathVariable("startDate") String startDate,
									 @PathVariable("endDate") String endDate){

		LOG.debug("Rest Request to get ebys onay bekleyen persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYS("ONAYBEKLEYEN", persid, rolid, startDate, endDate);
	}

	//EBYS tamamlanan belge
	@RequestMapping(value="EBYSTamamlanan/{persid}/{rolid}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<EBYS> getCompletedEBYS(@PathVariable("persid") long persid,
									   @PathVariable("rolid") long rolid,
									   @PathVariable("startDate") String startDate,
									   @PathVariable("endDate") String endDate){

		LOG.debug("Rest Request to get ebys tamamlanan persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYS("TAMAMLANAN", persid, rolid, startDate, endDate);
	}

	//EBYS Gonderim bekleyen belge
	@RequestMapping(value="EBYSGonderimBekleyen/{persid}/{rolid}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<EBYS> getWaitingToSendEBYS(@PathVariable("persid") long persid,
										   @PathVariable("rolid") long rolid,
										   @PathVariable("startDate") String startDate,
										   @PathVariable("endDate") String endDate){

		LOG.debug("Rest Request to get ebys gonderim bekleyen persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYS("GONDERIMBEKLEYEN", persid, rolid, startDate, endDate);
	}

	//EBYS iade edilen belge
	@RequestMapping(value="EBYSIadeEdilen/{persid}/{rolid}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<EBYS> getReturnedEBYS(@PathVariable("persid") long persid,
									  @PathVariable("rolid") long rolid,
									  @PathVariable("startDate") String startDate,
									  @PathVariable("endDate") String endDate){

		LOG.debug("Rest Request to get ebys iade edilen persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYS("IADEEDILEN", persid, rolid, startDate, endDate);
	}

	//EBYS kisiye atanan belge
	@RequestMapping(value="EBYSKisiyeAtanan/{persid}/{rolid}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<EBYS> getAssignedEBYS(@PathVariable("persid") long persid,
									  @PathVariable("rolid") long rolid,
									  @PathVariable("startDate") String startDate,
									  @PathVariable("endDate") String endDate){

		LOG.debug("Rest Request to get ebys kisiye atanan persid, rolid, startDate, endDate: {}", persid, rolid, startDate, endDate);
		return documentManagementService.getEBYS("KISIYEATANAN", persid, rolid, startDate, endDate);
	}

	@RequestMapping(value="EBYSDokumanDetay/{documentId}",method = RequestMethod.GET)
	public List<EBYSDetail> getEbysDocumentDetail(@PathVariable("documentId") long documentId){

		LOG.debug("Rest Request to get dokuman detay documentId: {}", documentId);
		return documentManagementService.getEbysDocumentDetail(documentId);
	}

	@RequestMapping(value="EBYSDokumanDetay/imzasizEk/{documentId}",method = RequestMethod.GET)
	public List<EBYSDetail> getEbysUnsignableAdditionDocument(@PathVariable("documentId") long documentId){

		LOG.debug("Rest Request to get imzasiz ekler documentId: {}", documentId);
		return documentManagementService.getEbysUnsignableAdditionDocument(documentId);
	}

	@RequestMapping(value="EBYSDokumanDetay/EBYSTamamlanan/{documentId}",method = RequestMethod.GET)
	public List<EBYSDetail> getEbysDocumentDetailForCompletedAddition(@PathVariable("documentId") long documentId){

		LOG.debug("Rest Request to get dokuman detay for completed DOC, documentId: {}", documentId);
		return documentManagementService.getEBYSAddition(documentId);
	}

	@RequestMapping(value="dokuman/{documentId}",method = RequestMethod.GET)
	public EBYSContent getDocument(@PathVariable("documentId") long documentId){

		LOG.debug("Rest Request to get dokuman documentId: {}", documentId);
		RestTemplate restTemplate = new RestTemplate();
		String url = environment.getProperty("eyazisma") + "paket/" + documentId + "/dokuman";
		byte[] bytes = restTemplate.getForObject(url, byte[].class);
		byte[] encoded = Base64.getEncoder().encode(bytes);
		EBYSContent ebysContent = new EBYSContent();
		ebysContent.setContent(new String(encoded));
		return ebysContent;
	}
	
	//Belge Basvuru rollList
	@RequestMapping(value = "belgeBasvuruRol/{persid}/{mastid}",method = RequestMethod.GET)
	public List<Rol> getDocRollList(@PathVariable("persid") long persid,
									@PathVariable("mastid") long mastid){

		LOG.debug("Rest Request to get belge basvuru roll list persid, mastid: {}", persid, mastid);
		return documentManagementService.getDocRollList(persid, mastid);
		
	}
	
	//Belge Basuvuru
	@RequestMapping(value = "belgeBasvuru/{rolid}",method = RequestMethod.GET)
	public List<BelgeBasvuru> getApplyDoc(@PathVariable("rolid")long rolid){

		LOG.debug("Rest Request to get belge basvuru roll list rolid: {}", rolid);
		return documentManagementService.getApplyDoc(rolid);
	}
	
	//Belge Basuvuru detay
	@RequestMapping(value = "belgeBasvuruDetay/{docId}",method = RequestMethod.GET)
	public BelgeBasvuruDetay getApplyDocDetail(@PathVariable("docId") long docId){
		System.out.println("-------belge basvuru detay-------------");
		System.out.println(docId);
		return documentManagementService.getApplyDocDetail(docId);
	}
	
	//EBYS ture gore toplam degerler
	@RequestMapping(value = "ebysCount/{persid}/{rolid}/{tur}",method = RequestMethod.GET)
	public Long getEbysMenuCount(@PathVariable("persid") long persid,@PathVariable("rolid") long rolid,@PathVariable("tur") String tur){
		System.out.println("-------ebys count-------------");
		System.out.println(persid);
		return documentManagementService.getEbysMenuCount(persid,rolid,tur);
	}
	
	//Role gore cozum ortagi olup olmadigini doner
	@RequestMapping(value = "cozumOrtagi/{rolid}",method = RequestMethod.GET)
	public CozumOrtagi isSolutionPartner(@PathVariable("rolid") long rolid){
		System.out.println("------cozum Ortagi---------");
		System.out.println(rolid);
		return documentManagementService.isSolutionPartner(rolid);
	}
	
	//EBYS klasor Tree
	@RequestMapping(value = "klasor/{rolid}",method = RequestMethod.GET)
	public List<EBYSKlasorMenu> getEBYSFolderTree(@PathVariable("rolid") long rolid){
		System.out.println("------klasor agaci---------");
		System.out.println(rolid);
		return documentManagementService.getEBYSFolderTree(rolid);
	}
	
	//EBYS Birim Tree
	@RequestMapping(value = "birim",method = RequestMethod.GET)
	public List<EBYSBirimMenu> getEBYSUnitTree(){
		System.out.println("------birim agaci---------");
		return documentManagementService.getEBYSUnitTree();
	}
	
	
	@RequestMapping(value = "ebys",method = RequestMethod.GET)
	public List<EBYSBirimMenu> getEBYSEbysTree(){
		System.out.println("------EBYS agaci---------");
		return documentManagementService.getEBYSEbysTree();
	}
	
	@RequestMapping(value = "belgeYonetimKullanici/{persid}",method = RequestMethod.GET)
	public List<BelgeYonetimKullanici> getDocManagementUserList(@PathVariable("persid") long persid){
		System.out.println("------klasor agaci---------");
		System.out.println(persid);
		return documentManagementService.getDocManagementUserList(persid);
	}
	
	@RequestMapping(value = "belgeYonetimGrafikGelenBelge/{year}/{servisId}/{result}",method = RequestMethod.GET)
	public List<GraphGeneral> getDocManagementGraphIncomingDoc(@PathVariable("year") long year,
															   @PathVariable("servisId") long servisId,
															   @PathVariable("result") String result){
		System.out.println("------belge yonetim gelen belge grafik---------");
		System.out.println(result);
		return documentManagementService.getDocManagementGraphIncomingDoc(year, servisId, result);
	}
	
	@RequestMapping(value = "belgeYonetimGrafikGidenBelge/{year}/{servisId}/{result}",method = RequestMethod.GET)
	public List<GraphGeneral> getDocManagementGraphOutgoingDoc(@PathVariable("year") long year,
															   @PathVariable("servisId") long servisId,
															   @PathVariable("result") String result){
		System.out.println("------belge yonetim giden belge grafik---------");
		System.out.println(result);
		return documentManagementService.getDocManagementGraphOutgoingDoc(year, servisId, result);
	}
	
	@RequestMapping(value = "ebysIsGrafikleri/{rolid}/{servisMudurluk}/{action}",method = RequestMethod.GET)
	public List<GraphGeneral> getEbysBusinessGraph(@PathVariable("rolid")long rolid,
												   @PathVariable("servisMudurluk") long servisMudurluk,
												   @PathVariable("action")String action){
		System.out.println("------ebys is grafikleri---------");
		System.out.println(servisMudurluk);
		return documentManagementService.getEbysBusinessGraph(rolid,servisMudurluk,action);
	}
	
	@RequestMapping(value = "ebysIsGrafikleriDetay/{rolid}/{servisMudurluk}/{action}/{rolPerformerId}",method = RequestMethod.GET)
	public List<GraphGeneral> getEbysBusinessGraphDetail(@PathVariable("rolid") long rolid,
														 @PathVariable("servisMudurluk") long servisMudurluk,
														 @PathVariable("action")String action,@PathVariable("rolPerformerId") long rolPerformerId){
		System.out.println("------ebys is grafikleri---------");
		System.out.println(servisMudurluk);
		return documentManagementService.getEbysBusinessGraphDetail(rolid,servisMudurluk,action,rolPerformerId);
	}

	@RequestMapping(value = "gelenBasvuru/{organizationId}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<BasvuruOzet> getGelenBasvuruList(@PathVariable("organizationId") long organizationId,
												 @PathVariable("startDate")String startDate,
												 @PathVariable("endDate")String endDate){
		return documentManagementService.getGelenBasvuruList(organizationId,startDate,endDate);
	}

	@RequestMapping(value = "gidenBasvuru/{organizationId}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<BasvuruOzet> getGidenBasvuruList(@PathVariable("organizationId") long organizationId,
												 @PathVariable("startDate")String startDate,
												 @PathVariable("endDate")String endDate){
		return documentManagementService.getGidenBasvuruList(organizationId,startDate,endDate);
	}

	@RequestMapping(value = "urettiklerim/{organizationId}/{startDate}/{endDate}",method = RequestMethod.GET)
	public List<BasvuruOzet> getUrettiklerimList(@PathVariable("organizationId") long organizationId,
												 @PathVariable("startDate")String startDate,
												 @PathVariable("endDate")String endDate){
		return documentManagementService.getUrettiklerimList(organizationId,startDate,endDate);
	}

	@RequestMapping(value = "ebys/belge/red",method = RequestMethod.POST)
	public ResponseEntity<Boolean> documentReject(@RequestBody DocumentRejectDTO documentRejectDTO){
		LOG.debug("Belge red istegi geldi");
		LOG.debug("documentId=" + documentRejectDTO.getEbysdocument_id());
		LOG.debug("workFlowId=" + documentRejectDTO.getAbpmworkflow_id());
		LOG.debug("iptal nedeni=" + documentRejectDTO.getIptalAciklamasi());
		documentManagementService.rejectDocument(documentRejectDTO);
		return new ResponseEntity<Boolean>(true, OK);
	}


}
