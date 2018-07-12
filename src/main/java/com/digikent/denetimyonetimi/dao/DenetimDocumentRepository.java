package com.digikent.denetimyonetimi.dao;

import com.digikent.denetimyonetimi.service.DenetimDocumentService;
import com.digikent.denetimyonetimi.service.DenetimTarafService;
import com.vadi.digikent.service.icerikyonetimi.documentum.DocumentumConnector;
import com.vadi.digikent.service.icerikyonetimi.documentum.DocumentumItemAction;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by Kadir on 7.03.2018.
 */
@Repository
public class DenetimDocumentRepository {
    private final Logger LOG = LoggerFactory.getLogger(DenetimDocumentRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    DenetimDocumentService denetimDocumentService;


    public void saveToDocumentum(String tableName,Long id, byte[] content) throws Exception {

        try {
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
                        System.out.println(ht + "/" + data[folderPath]);
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
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new Exception();
        }

    }
}
