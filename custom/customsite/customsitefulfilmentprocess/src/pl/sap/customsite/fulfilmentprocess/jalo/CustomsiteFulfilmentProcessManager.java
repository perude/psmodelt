/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package pl.sap.customsite.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import pl.sap.customsite.fulfilmentprocess.constants.CustomsiteFulfilmentProcessConstants;

public class CustomsiteFulfilmentProcessManager extends GeneratedCustomsiteFulfilmentProcessManager
{
	public static final CustomsiteFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CustomsiteFulfilmentProcessManager) em.getExtension(CustomsiteFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
