/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.cec.ccv2hacks.barcodetest.service;

import de.hybris.platform.core.model.media.MediaModel;


public interface BarcodetestService
{
	byte[] generateSampleBarcode(String message, String info1, String info2, String info3);

	void generateSampleBarcodeForMedia(MediaModel media, String message, String info1, String info2, String info3);

	MediaModel generateSampleBarcodeMedia(String message, String info1, String info2, String info3);
}
