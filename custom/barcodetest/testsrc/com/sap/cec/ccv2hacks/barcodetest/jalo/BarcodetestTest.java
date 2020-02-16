/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.cec.ccv2hacks.barcodetest.jalo;

import static org.assertj.core.api.Assertions.assertThat;

import de.hybris.platform.testframework.HybrisJUnit4TransactionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cec.ccv2hacks.barcodetest.service.impl.DefaultBarcodetestService;


/**
 * JUnit Tests for the Barcodetest extension.
 */
public class BarcodetestTest extends HybrisJUnit4TransactionalTest
{
	/**
	 * Edit the local|project.properties to change logging behaviour (properties log4j2.logger.*).
	 */
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(BarcodetestTest.class);

	@Before
	public void setUp()
	{
		// implement here code executed before each test
	}

	@After
	public void tearDown()
	{
		// implement here code executed after each test
	}

	@Test
	public void testBarcodetest()
	{
		final DefaultBarcodetestService service = new DefaultBarcodetestService();

		final byte[] data = service.generateSampleBarcode("foo", "i1", "i2", "i3");
		assertThat(data).isNotNull();
		assertThat(data.length).isGreaterThan(0);
	}
}
