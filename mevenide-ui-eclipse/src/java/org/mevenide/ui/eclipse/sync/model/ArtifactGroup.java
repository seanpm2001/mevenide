/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 Gilles Dodinet (rhill@wanadoo.fr).  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software licensed under 
 *        Apache Software License (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Mevenide" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact mevenide-general-dev@lists.sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Mevenide", nor may "Apache" or "Mevenide" appear in their name, without
 *    prior written permission of the Mevenide Team and the ASF.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.mevenide.ui.eclipse.sync.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * 
 * @author <a href="mailto:gdodinet@wanadoo.fr">Gilles Dodinet</a>
 * @version $Id$
 * 
 */
public abstract class ArtifactGroup {
	private static Log log = LogFactory.getLog(ArtifactGroup.class);
	
	protected IJavaProject javaProject;
	
	private IProject project;
	private String projectName;
	
	protected List artifacts = new ArrayList(); 
	protected List excludedArtifacts = new ArrayList();
	
	protected boolean isInherited;
	
	protected ArtifactGroup parentGroup; 
	
	public ArtifactGroup()  { }
	
	public ArtifactGroup(IProject project)  {
		try {
			if ( project != null && project.hasNature(JavaCore.NATURE_ID) ) {
				this.javaProject = JavaCore.create(project);
				this.projectName = project.getName();
				log.debug("Initializing ArtifactGroup for project " + projectName);
				initialize();
			}
			setProject(project);
		}
		catch ( Exception ex ) {
			log.debug("Error in ArtifactGroup initializer. reason : " + ex);
			ex.printStackTrace();
		}
	}
	
	protected abstract void initialize() throws Exception; 
	
	public IJavaProject getJavaProject() {
		return javaProject;
	}

	public void setJavaProject(IJavaProject project) throws Exception {
		this.javaProject = project;
		initialize();
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProject(IProject project) {
		this.project = project;
		this.projectName = project.getName();
	}

	public void setInherited(boolean isInherited) {
	   this.isInherited = isInherited;
    }

	public boolean isInherited() {
		return isInherited;
	}

	public IProject getProject() {
		return project;
	}
	
	public ArtifactGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(ArtifactGroup parentGroup) {
		this.parentGroup = parentGroup;
	}
	
	public abstract boolean isDuplicated(Object element) ;

}
