package com.digikent.zabita.entity;

import com.digikent.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kadir on 26.01.2018.
 */
@Entity
@Table(name = "BISLISLETME")
public class BISLIsletme extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "bislisletme_seq", sequenceName = "BISLISLETME_ID", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bislisletme_seq")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long ID;


}
