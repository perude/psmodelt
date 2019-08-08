/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package pl.sap.customsite.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import pl.sap.customsite.core.constants.CustomsiteCoreConstants;
import pl.sap.customsite.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class CustomsiteCoreManager extends GeneratedCustomsiteCoreManager
{
	public static final CustomsiteCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CustomsiteCoreManager) em.getExtension(CustomsiteCoreConstants.EXTENSIONNAME);
	}
}
