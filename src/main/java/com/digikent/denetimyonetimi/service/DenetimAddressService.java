package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimAddressRepository;
import com.digikent.denetimyonetimi.dto.adres.*;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Kadir on 2.03.2018.
 */
@Service
public class DenetimAddressService {

    private final Logger LOG = LoggerFactory.getLogger(DenetimAddressService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimAddressRepository denetimAddressRepository;

    @Autowired
    DenetimService denetimService;

    /**
     * belediye id bilgisine göre mahalle ve sokak bilgilerini getirir
     * @param belediyeId
     * @return
     */
    @Cacheable(value = "sokaklar", key = "#root.methodName.toString() + #belediyeId")
    public List<MahalleSokakDTO> getMahalleSokakListByBelediyeId(Long belediyeId) {
        LOG.debug("searching getMahalleSokakListByBelediyeId");
        return denetimAddressRepository.findMahalleAndSokakListByBelediyeId(belediyeId);
    }

    /**
     * mevcut belediyenin bulunduğu ildeki belediye listesini getirir
     * @return
     */
    @Cacheable(value="belediyeler", key = "#root.methodName")
    public List<BelediyeDTO> getBelediyeList(Long ilId) {
        LOG.debug("searching getBelediyeList");
        return denetimAddressRepository.findBelediyeList(ilId);
    }

    /**
     * Belediye Id bilgisine göre mahalleleri getirir
     * @param belediyeId
     * @return
     */
    @Cacheable(value="mahalleler", key = "#belediyeId")
    public List<MahalleDTO> getMahalleByBelediyeId(Long belediyeId) {
        LOG.debug("searching getMahalleByBelediyeId");
        return denetimAddressRepository.findMahalleListByBelediyeId(belediyeId);
    }

    /**
     * Mahalle Id bilgisine göre sokakları getirir
     * @param mahalleId
     * @return
     */
    @Cacheable(value = "sokaklar", key = "#mahalleId")
    public List<SokakDTO> getSokakByMahalleId(Long mahalleId) {
        LOG.debug("searching getSokakByMahalleId");
        return denetimAddressRepository.findSokakListByMahalleId(mahalleId);
    }

    /**
     * Geçerli belediye bilgisine göre mahalleleri getirir
     * @return
     */
    @Cacheable(value="mahalleler", key = "#root.methodName.toString()")
    public List<MahalleDTO> getMahalleListByCurrentBelediye() {
        LOG.debug("searching getMahalleListByCurrentBelediye");
        return denetimAddressRepository.findMahalleListByCurrentBelediye();
    }

    /**
     * Geçerli tüm illeri getirir
     * @return
     */
    @Cacheable(value = "iller", key = "#root.methodName.toString()")
    public List<IlDTO> getIlList() {
        return denetimAddressRepository.findIlList();
    }

}
