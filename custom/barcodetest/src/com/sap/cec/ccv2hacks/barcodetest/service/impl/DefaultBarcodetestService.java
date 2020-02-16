/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.sap.cec.ccv2hacks.barcodetest.service.impl;

import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoderRegistry;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.sap.cec.ccv2hacks.barcodetest.service.BarcodetestService;


public class DefaultBarcodetestService implements BarcodetestService
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultBarcodetestService.class);

	private MediaService mediaService;
	private ModelService modelService;

	@Override
	public byte[] generateSampleBarcode(final String message, final String info1, final String info2, final String info3)
	{
		try (ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			generate(out, message, info1, info2, info3);
			return out.toByteArray();
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public void generateSampleBarcodeForMedia(final MediaModel media, final String message, final String info1, final String info2,
			final String info3)
	{
		try (InputStream in = new ByteArrayInputStream(generateSampleBarcode(message, info1, info2, info3)))
		{
			mediaService.setStreamForMedia(media, in, "barcode.png", "image/png");
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public MediaModel generateSampleBarcodeMedia(final String message, final String info1, final String info2, final String info3)
	{
		final CatalogUnawareMediaModel media = modelService.create(CatalogUnawareMediaModel.class);
		media.setCode("SampleBarcode-" + UUID.randomUUID());
		modelService.save(media);

		generateSampleBarcodeForMedia(media, message, info1, info2, info3);

		modelService.save(media);

		return media;
	}

	private void generate(final OutputStream out, final String msg, final String info1, final String info2, final String info3)
			throws IOException
	{
		final String[] paramArr = new String[]
		{ info1, info2, info3 };

		//Create the barcode bean
		final DataMatrixBean bean = new DataMatrixBean();

		final int dpi = 200;

		//Configure the barcode generator
		bean.setModuleWidth(UnitConv.in2mm(8.0f / dpi)); //makes a dot/module exactly eight pixels
		bean.doQuietZone(false);
		bean.setShape(SymbolShapeHint.FORCE_RECTANGLE);

		final boolean antiAlias = false;
		final int orientation = 0;
		//Set up the canvas provider to create a monochrome bitmap
		final BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, antiAlias, orientation);

		//Generate the barcode
		bean.generateBarcode(canvas, msg);

		//Signal end of generation
		canvas.finish();

		//Get generated bitmap
		final BufferedImage symbol = canvas.getBufferedImage();

		final int fontSize = 32; //pixels
		final int lineHeight = (int) (fontSize * 1.2);
		final Font font = new Font("Arial", Font.PLAIN, fontSize);
		int width = symbol.getWidth();
		int height = symbol.getHeight();
		final FontRenderContext frc = new FontRenderContext(new AffineTransform(), antiAlias, true);
		for (int i = 0; i < paramArr.length; i++)
		{
			final String line = paramArr[i];
			final Rectangle2D bounds = font.getStringBounds(line, frc);
			width = (int) Math.ceil(Math.max(width, bounds.getWidth()));
			height += lineHeight;
		}

		//Add padding
		final int padding = 2;
		width += 2 * padding;
		height += 3 * padding;

		final BufferedImage bitmap = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		final Graphics2D g2d = (Graphics2D) bitmap.getGraphics();
		g2d.setBackground(Color.white);
		g2d.setColor(Color.black);
		g2d.clearRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		g2d.setFont(font);

		//Place the barcode symbol
		final AffineTransform symbolPlacement = new AffineTransform();
		symbolPlacement.translate(padding, padding);
		g2d.drawRenderedImage(symbol, symbolPlacement);

		//Add text lines (or anything else you might want to add)
		int y = padding + symbol.getHeight() + padding;
		for (int i = 0; i < paramArr.length; i++)
		{
			final String line = paramArr[i];
			y += lineHeight;
			g2d.drawString(line, padding, y);
		}
		g2d.dispose();

		//Encode bitmap as file
		final String mime = "image/png";
		final BitmapEncoder encoder = BitmapEncoderRegistry.getInstance(mime);
		encoder.encode(bitmap, out, mime, dpi);
	}

	@Required
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}
}
