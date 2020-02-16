/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.cec.ccv2hacks.barcodetest.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.ServicelayerBaseTest;
import de.hybris.platform.servicelayer.media.MediaService;

import javax.annotation.Resource;

import org.junit.Test;

import com.sap.cec.ccv2hacks.barcodetest.service.BarcodetestService;


/**
 * This is an example of how the integration test should look like. {@link ServicelayerBaseTest} bootstraps platform so
 * you have an access to all Spring beans as well as database connection. It also ensures proper cleaning out of items
 * created during the test after it finishes. You can inject any Spring service using {@link Resource} annotation. Keep
 * in mind that by default it assumes that annotated field name matches the Spring Bean ID.
 */
@IntegrationTest
public class DefaultBarcodetestServiceIntegrationTest extends ServicelayerBaseTest
{
	@Resource
	private BarcodetestService barcodetestService;
	@Resource
	private MediaService mediaService;

	@Test
	public void shouldGenerateSampleMedia() throws Exception
	{
		// given
		final String message = "Foo Bar";

		// when
		final MediaModel media = barcodetestService.generateSampleBarcodeMedia(message, "info 1", "info 2", "info 3");

		// then
		assertThat(media).isNotNull();
		assertThat(media.getMime()).isEqualTo("image/png");
		assertThat(mediaService.hasData(media)).isTrue();
	}

}
