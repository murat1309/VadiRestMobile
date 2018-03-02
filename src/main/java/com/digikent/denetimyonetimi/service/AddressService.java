package com.digikent.denetimyonetimi.service;

import com.digikent.denetimyonetimi.dao.DenetimRepository;
import com.digikent.denetimyonetimi.dto.adres.BelediyeDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleDTO;
import com.digikent.denetimyonetimi.dto.adres.MahalleSokakDTO;
import com.digikent.denetimyonetimi.dto.adres.SokakDTO;
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
public class AddressService {

    private final Logger LOG = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    SessionFactory sessionFactory;

    @Inject
    DenetimRepository denetimRepository;

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
        return denetimRepository.findMahalleAndSokakListByBelediyeId(belediyeId);
    }

    /**
     * mevcut belediyenin bulunduğu ildeki belediye listesini getirir
     * @return
     */
    @Cacheable(value="belediyeler", key = "#root.methodName")
    public List<BelediyeDTO> getBelediyeList() {
        LOG.debug("searching getBelediyeList");
        return denetimRepository.findBelediyeList();
    }

    /**
     * Belediye Id bilgisine göre mahalleleri getirir
     * @param belediyeId
     * @return
     */
    @Cacheable(value="mahalleler", key = "#belediyeId")
    public List<MahalleDTO> getMahalleByBelediyeId(Long belediyeId) {
        LOG.debug("searching getMahalleByBelediyeId");
        return denetimRepository.findMahalleListByBelediyeId(belediyeId);
    }

    /**
     * Mahalle Id bilgisine göre sokakları getirir
     * @param mahalleId
     * @return
     */
    @Cacheable(value = "sokaklar", key = "#mahalleId")
    public List<SokakDTO> getSokakByMahalleId(Long mahalleId) {
        LOG.debug("searching getSokakByMahalleId");
        return denetimRepository.findSokakListByMahalleId(mahalleId);
    }

    /**
     * Geçerli belediye bilgisine göre mahalleleri getirir
     * @return
     */
    @Cacheable(value="mahalleler", key = "#root.methodName.toString()")
    public List<MahalleDTO> getMahalleListByCurrentBelediye() {
        LOG.debug("searching getMahalleListByCurrentBelediye");
        return denetimRepository.findMahalleListByCurrentBelediye();
    }

}
