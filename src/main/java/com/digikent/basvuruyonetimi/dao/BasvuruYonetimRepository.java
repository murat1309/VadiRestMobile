package com.digikent.basvuruyonetimi.dao;

import com.digikent.basinyayin.dto.AsmaIndirmeIslemiDTO;
import com.digikent.basinyayin.dto.TtnyekipDTO;
import com.digikent.basinyayin.dto.TtnylokasyonDTO;
import com.digikent.basinyayin.dto.VtnytanitimDTO;
import com.digikent.web.rest.errors.CustomParameterizedException;


import com.vadi.digikent.service.icerikyonetimi.documentum.DocumentumConnector;
import com.vadi.digikent.service.icerikyonetimi.documentum.DocumentumItemAction;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisi;
import com.vadi.smartkent.datamodel.domains.icerikyonetimi.dm1.DM1IsAkisiAdim;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Serkan on 4/1/2016.
 */
@Repository
public class BasvuruYonetimRepository {
    private final Logger LOG = LoggerFactory.getLogger(BasvuruYonetimRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }

    public DM1IsAkisi getDdm1isakisi(@Param("id") Long id){
        Session session = getCurrentSession();

        String sqlQuery = "from DM1IsAkisi d where d.ID= :id";
        Query query1 = session.createQuery(sqlQuery);
        query1.setParameter("id", id);
        DM1IsAkisi dm1IsAkisi = (DM1IsAkisi)query1.uniqueResult();

        return dm1IsAkisi;
    }

    public DM1IsAkisiAdim getEdm1isakisiadim(@Param("id") Long id){
        Session session = getCurrentSession();

        String sqlQuery = "from DM1IsAkisiAdim e where e.ID= :id";
        Query query1 = session.createQuery(sqlQuery);
        query1.setParameter("id", id);
        DM1IsAkisiAdim dm1IsAkisiAdim = (DM1IsAkisiAdim)query1.uniqueResult();

        return dm1IsAkisiAdim;
    }

    public void updateEdm1isakisiadim(long personelId, DM1IsAkisiAdim dM1IsAkisiAdim) throws Exception {
        Session session = getCurrentSession();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();

        String sqlQuery = "UPDATE DM1IsAkisiAdim e " +
                "set e.izahat = :izahat , e.sonucDurumu= :sonucdurumu , e.sonuctarihi= :sonuctarihi " +
                "WHERE e.ID= :id";

        try{
            Query query = session.createQuery(sqlQuery);
            int updatedRows = query
                    .setParameter("sonucdurumu", dM1IsAkisiAdim.getSonucDurumu())
                    .setParameter("izahat", dM1IsAkisiAdim.getIzahat())
                    .setParameter("sonuctarihi", currentDate)
                    .setParameter("id", dM1IsAkisiAdim.getID())
                    .executeUpdate();
        }catch (Exception e){
            LOG.warn("DM1IsAkisiAdim update hata {}",e.getMessage(),dM1IsAkisiAdim.getID() );
            throw new Exception(e.getMessage());
        }

        try{
            Query exQuery = session.createSQLQuery("CALL " +
                    "DM1.P_BELGE_ISLEM_KAYDET(:adimId,:kayitTuru,:personelId,:errcode,:errtext)");

            exQuery.setParameter("adimId", dM1IsAkisiAdim.getID());
            exQuery.setParameter("kayitTuru", "U");
            exQuery.setParameter("personelId", personelId);
            exQuery.setParameter("errcode", 0);
            exQuery.setParameter("errtext", "");
            int exRows = exQuery.executeUpdate();
        }catch(Exception e){
            LOG.warn("P_BELGE_ISLEM_KAYDET hata {}",e.getMessage() );
            throw new Exception(e.getMessage());
        }
    }

    /*
        EBelediyeService.applyBasvuruEk documentum kismi
     */
    public void saveToDocumentum(String tableName,Long id, byte[] content){

        String digikentInitialPath = "";
        if(System.getenv("DIGIKENT_PATH") != null) {
            digikentInitialPath = System.getenv("DIGIKENT_PATH");
        } else {
            digikentInitialPath = "C:\\DIGIKENT_PATH";
        }

        Properties belTel = new Properties();
        File belLogo = new File(digikentInitialPath + "\\documentum\\documentum.properties");
        if(belLogo.exists() && (DocumentumConnector.connected == null || !DocumentumConnector.connected.equals("Bagli."))) {
            System.err.println("Bagli degil");

            try {
                FileInputStream ebldSorguLink = new FileInputStream(belLogo);
                belTel.load(ebldSorguLink);
                ebldSorguLink.close();
            } catch (IOException var27) {
                var27.printStackTrace();
            }

            DocumentumConnector.userName = belTel.getProperty("username");
            DocumentumConnector.password = belTel.getProperty("password");
            DocumentumConnector.repository = belTel.getProperty("repository");
        }

        String tableNameWithId = tableName + " - " + id;
        String mailIcerik = "/DYS/" + tableName;
        Hashtable message = new Hashtable();
        message.put("att_tablename", tableName);
        message.put("att_anahtaralan", id);
        (new DocumentumItemAction()).checkPathExists(mailIcerik + "/" + tableNameWithId);
        String itemType = "bld_klasor";
        String folderitemId = "";
        if(!(new DocumentumItemAction()).checkPathExists(mailIcerik + "/" + tableNameWithId)) {
            if(!(new DocumentumItemAction()).checkPathExists(mailIcerik)) {
                String[] data = mailIcerik.split("\\/");
                String ht = "/" + data[1];

                for(int folderPath = 2; folderPath < data.length; ++folderPath) {
                    if(!(new DocumentumItemAction()).checkPathExists(ht + "/" + data[folderPath])) {
                        (new DocumentumItemAction()).createContainerFolder(ht, data[folderPath]);
                    }

                    ht = ht + "/" + data[folderPath];
                }
            }

            message.put("att_tanim", tableNameWithId);
            folderitemId = (new DocumentumItemAction()).createFolder(itemType, message, mailIcerik);
        } else {
            folderitemId = (new DocumentumItemAction()).getFolderItemID(mailIcerik + "/" + tableNameWithId);
        }

        if(folderitemId.length() > 0) {
            LOG.debug("Documentum Folder created with id:{}", folderitemId);
            String itemId;
            Hashtable var34;
            String var36;
            if(content != null && content.length > 0) {
                var34 = new Hashtable();
                var34.put("att_tablename", tableName);
                var34.put("att_anahtaralan", id + "");
                var34.put("object_name", tableName + "-" + tableNameWithId);
                var34.put("att_tanim", "resim");
                var36 = "/DYS/" + tableName;
                itemId = "";
                String pid = (new DocumentumItemAction()).createDocument("bld_dokuman", "jpeg", content, var34, new String[]{var36});
                LOG.debug("Documentum Folder pid:{}", pid);
            }
        } else {
            LOG.warn("Documentum Folder could not be created");
        }
    }
}
