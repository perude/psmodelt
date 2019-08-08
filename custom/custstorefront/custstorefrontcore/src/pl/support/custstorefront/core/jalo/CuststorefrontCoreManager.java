/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package pl.support.custstorefront.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import pl.support.custstorefront.core.constants.CuststorefrontCoreConstants;
import pl.support.custstorefront.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class CuststorefrontCoreManager extends GeneratedCuststorefrontCoreManager
{
	public static final CuststorefrontCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (CuststorefrontCoreManager) em.getExtension(CuststorefrontCoreConstants.EXTENSIONNAME);
	}
}
