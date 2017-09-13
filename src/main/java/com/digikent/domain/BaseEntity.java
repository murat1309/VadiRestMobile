package com.digikent.domain;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDDATE")
    protected Date updDate;

    @Column(name = "UPDSEQ")
    protected Long updSeq;

    @Column(name = "UPDUSER")
    protected Long updUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRDATE")
    protected Date crDate = new Date();

    @Column(name = "CRUSER")
    protected Long crUser;

    @Column(name = "DELETEFLAG", length = 1)
    protected String deleteFlag;

    @Type(type = "com.vadi.smartkent.datamodel.domains.base.EvetHayirType")
    @Column(name = "ISACTIVE", length = 1)
    protected Boolean isActive;

    @Transient
    public static String sortingColumn;

    @Transient
    public static boolean sortingState;

    public void deepCopy(BaseEntity target) throws IllegalAccessException,
            InvocationTargetException {
        try {
            BeanInfo sourceInfo = Introspector.getBeanInfo(this.getClass());
            BeanInfo targetInfo = Introspector.getBeanInfo(target.getClass());

            for (PropertyDescriptor sourceDescriptor : sourceInfo
                    .getPropertyDescriptors()) {
                Method readMethod = sourceDescriptor.getReadMethod();

                for (PropertyDescriptor targetDescriptor : targetInfo
                        .getPropertyDescriptors()) {
                    if (sourceDescriptor.getName().equals(
                            targetDescriptor.getName())) {
                        Method writeMethod = targetDescriptor.getWriteMethod();
                        Object obj = null;
                        obj = readMethod.invoke(this, null);
                        if (writeMethod != null
                                && !"setId".equalsIgnoreCase(writeMethod
                                .getName())) {
                            writeMethod.invoke(target, obj);
                        }
                        break;
                    }
                }
            }
        } catch (IntrospectionException e) {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Long getUpdSeq() {
        return updSeq;
    }

    public void setUpdSeq(Long updSeq) {
        this.updSeq = updSeq;
    }

    public Long getUpdUser() {
        return updUser;
    }

    public void setUpdUser(Long updUser) {
        this.updUser = updUser;
    }

    public Date getCrDate() {
        return crDate;
    }

    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }

    public Long getCrUser() {
        return crUser;
    }

    public void setCrUser(Long crUser) {
        this.crUser = crUser;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public static String getSortingColumn() {
        return sortingColumn;
    }

    public static void setSortingColumn(String sortingColumn) {
        BaseEntity.sortingColumn = sortingColumn;
    }

    public static boolean isSortingState() {
        return sortingState;
    }

    public static void setSortingState(boolean sortingState) {
        BaseEntity.sortingState = sortingState;
    }

}
