/* 
 * Copyright (C) 2003  Gilles Dodinet (gdodinet@wanadoo.fr)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 */

package org.mevenide.ui.eclipse.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.mevenide.ui.eclipse.launch.dialog.RunMavenWizard;

/**
 * 
 * @author Gilles Dodinet (gdodinet@wanadoo.fr)
 * @version $Id$
 * 
 */
public class RunMavenAction extends AbstractMavenAction {
	
    public void run(IAction action) {
		
		Wizard wizard = new RunMavenWizard();
        WizardDialog dialog 
        	= new WizardDialog(
				PlatformUI.getWorkbench()
                		  .getActiveWorkbenchWindow()
                          .getShell(), wizard);
		dialog.create();
        dialog.open();
	}
    
    
}