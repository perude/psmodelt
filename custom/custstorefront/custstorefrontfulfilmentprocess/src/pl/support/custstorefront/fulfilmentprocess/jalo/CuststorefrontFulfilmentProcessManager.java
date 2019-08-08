/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package pl.support.custstorefront.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import pl.support.custstorefront.fulfilmentprocess.constants.CuststorefrontFulfilmentProcessConstants;

public class CuststorefrontFulfilmentProcessManager extends GeneratedCuststorefrontFulfilmentProcessManager
{
	public static final CuststorefrontFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CuststorefrontFulfilmentProcessManager) em.getExtension(CuststorefrontFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
