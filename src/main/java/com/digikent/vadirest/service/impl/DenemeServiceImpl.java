package com.digikent.vadirest.service.impl;

import com.digikent.vadirest.service.DenemeService;

import org.springframework.stereotype.Service;

/**
 * Created by yunusoncel on 18.2.2016.
 */
@Service
public class DenemeServiceImpl implements DenemeService {
	@Override
	public Boolean toplama(String first, String second) {
		return first.equals(second);
	}
}
