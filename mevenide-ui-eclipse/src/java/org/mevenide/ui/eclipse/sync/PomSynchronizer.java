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
package org.mevenide.ui.eclipse.sync;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.Dependency;
import org.apache.maven.project.Project;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.PreferenceStore;
import org.mevenide.ProjectConstants;
import org.mevenide.project.dependency.DependencyUtil;
import org.mevenide.project.io.ProjectReader;
import org.mevenide.project.io.ProjectWriter;
import org.mevenide.sync.AbstractPomSynchronizer;
import org.mevenide.sync.ISynchronizer;
import org.mevenide.ui.eclipse.DefaultPathResolver;
import org.mevenide.ui.eclipse.IPathResolver;
import org.mevenide.ui.eclipse.Mevenide;
import org.mevenide.ui.eclipse.sync.model.dependency.DependencyGroup;
import org.mevenide.ui.eclipse.sync.model.dependency.DependencyGroupMarshaller;
import org.mevenide.ui.eclipse.sync.model.dependency.DependencyWrapper;
import org.mevenide.ui.eclipse.sync.model.source.SourceDirectory;
import org.mevenide.ui.eclipse.sync.model.source.SourceDirectoryGroup;
import org.mevenide.ui.eclipse.sync.model.source.SourceDirectoryGroupMarshaller;
import org.mevenide.ui.eclipse.util.FileUtil;
import org.mevenide.util.MevenideUtil;

/**
 * 
 * @author Gilles Dodinet (gdodinet@wanadoo.fr)
 * @version $Id$
 *
 */
public class PomSynchronizer extends AbstractPomSynchronizer implements ISynchronizer {
	private static Log log = LogFactory.getLog(PomSynchronizer.class);
	
	/** the project under control */
	private IProject project;
    
    /** the POM under control */
	private IFile pom;

    /** helper instance */
    private IPathResolver pathResolver;
    

    
    public void initialize() {
		this.project = Mevenide.getPlugin().getProject();
		this.pom = project.getFile("project.xml");
		pathResolver = new DefaultPathResolver(); 
	}

    /**
     * @see org.mevenide.core.sync.AbstractPomSynchronizer
     */
	protected void mavenize() {
		try {
			
			if ( SynchronizerUtil.shouldSynchronizePom(project) ) {
				log.debug("About to update pom");
				DependencyGroup dependencyGroup = DependencyGroupMarshaller.getDependencyGroup(project, Mevenide.getPlugin().getFile("statedDependencies.xml"));
				SourceDirectoryGroup sourceGroup = SourceDirectoryGroupMarshaller.getSourceDirectoryGroup(project, Mevenide.getPlugin().getFile("sourceTypes.xml"), 0);
				updatePom(sourceGroup, dependencyGroup, Mevenide.getPlugin().getPom());
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("Unable to synchronize project '" + project.getName() + "' due to : " + e);
		}
	}

	/**
     * @see org.mevenide.core.sync.AbstractPomSynchronizer#preSynchronization()
     */
	public void preSynchronization() {
		try {
			Mevenide.getPlugin().createPom();
			FileUtil.assertPomNotEmpty(pom);
		}
		catch (Exception e) {
			log.debug("Unable to create POM due to : " + e);
		}
	}
	
	/**
     * @see org.mevenide.core.sync.AbstractPomSynchronizer#postSynchronization()
     */
	public void postSynchronization() {
		try {
			FileUtil.refresh(project);
		} 
		catch (Exception e) {
			log.debug("Unable to create POM due to : " + e);
		}
	}



	public void updatePom(SourceDirectoryGroup sourceGroup, DependencyGroup dependencyGoup, File pomFile) throws Exception {
		Mevenide.getPlugin().createProjectProperties();
		
		ProjectWriter pomWriter = ProjectWriter.getWriter();
		
		pomWriter.resetSourceDirectories(pomFile);
		
		//WICKED if/else -- to be removed when SourceDirectoryBatchUpdate is done - tho this will only move the problem backward
		log.debug("Writing back " + sourceGroup.getNonInheritedSourceDirectories().size());
		
		for (int i = 0; i < sourceGroup.getNonInheritedSourceDirectories().size(); i++) {
			SourceDirectory directory = (SourceDirectory) sourceGroup.getNonInheritedSourceDirectories().get(i);
			if ( directory.isSource() ) {
				pomWriter.addSource(directory.getDirectoryPath(), pomFile, directory.getDirectoryType());
			}
			if ( directory.getDirectoryType().equals(ProjectConstants.MAVEN_RESOURCE ) ) {
				pomWriter.addResource(directory.getDirectoryPath(), pomFile);
			}
			if ( directory.getDirectoryType().equals(ProjectConstants.MAVEN_TEST_RESOURCE ) ) {
				pomWriter.addUnitTestResource(directory.getDirectoryPath(), pomFile);
			}
					
		}
		
		//List dependencies = dependencyGoup.getDependencies();
		List dependencies = dependencyGoup.getNonInheritedDependencyWrappers();
		log.debug("Writing back " + dependencies.size() + " dependencies to file '" + pomFile.getName() +"'");
		//dependencies.addAll(ProjectUtil.getCrossProjectDependencies());
		
		List nonInheritedDependencies = new ArrayList();
		for (int i = 0; i < dependencies.size(); i++) {
			nonInheritedDependencies.add(((DependencyWrapper) dependencies.get(i)).getDependency());
		}
		pomWriter.setDependencies(nonInheritedDependencies, pomFile);
		
		PreferenceStore store = new PreferenceStore(Mevenide.getPlugin().getPreferencesFilename());
		store.load();
		
		log.debug("isInherited = " + (dependencyGoup.isInherited()) + " ; parentPom = " + store.getString("pom." + sourceGroup.getProjectName() + ".parent"));
		pomWriter.updateExtend(pomFile, dependencyGoup.isInherited(), store.getString("pom." + sourceGroup.getProjectName() + ".parent"));
		
		Mevenide.getPlugin().setBuildPath();
		
		//update parent pom if necessary ?
		String resolvedExtend = MevenideUtil.resolve(ProjectReader.getReader().read(pomFile), store.getString("pom." + sourceGroup.getProjectName() + ".parent"));
		if ( !new File(resolvedExtend).exists() ) {
			resolvedExtend = new File(new File(project.getFile("project.xml").getLocation().toOSString()).getParentFile(), resolvedExtend).getAbsolutePath();
		}			

		if ( new File(resolvedExtend).exists() ) {
			List inheritedDependencies = new ArrayList();
			List inheritedDependencyWrappers = dependencyGoup.getInheritedDependencyWrappers();
			for (int i = 0; i < inheritedDependencyWrappers.size(); i++) {
				DependencyWrapper wrapper = (DependencyWrapper) inheritedDependencyWrappers.get(i); 
            	inheritedDependencies.add(wrapper.getDependency());   
            }
			if ( inheritedDependencies.size() > 0 ) {
				Project parentProject = ProjectReader.getReader().read(new File(resolvedExtend));
				List parentDependencies = parentProject.getDependencies();
				for (int i = 0; i < parentDependencies.size(); i++) {
                    for (int j = 0; j < inheritedDependencies.size(); j++) {
						if ( !DependencyUtil.areEquals((Dependency) inheritedDependencies.get(j), (Dependency) parentDependencies.get(i)) ) {
	                        inheritedDependencies.add(parentDependencies.get(i));
						}
                    }
                }
				ProjectWriter.getWriter().setDependencies(inheritedDependencies, new File(resolvedExtend));
			}
	
			List inheritedSourceDirectories = sourceGroup.getInheritedSourceDirectories();
			for (int i = 0; i < inheritedSourceDirectories.size(); i++) {
		        SourceDirectory directory = (SourceDirectory) inheritedSourceDirectories.get(i);
				if ( directory.isSource() ) {
					ProjectWriter.getWriter().addSource(directory.getDirectoryPath(), new File(resolvedExtend), directory.getDirectoryType());
				}
				if ( directory.getDirectoryType().equals(ProjectConstants.MAVEN_RESOURCE ) ) {
					ProjectWriter.getWriter().addResource(directory.getDirectoryPath(), new File(resolvedExtend));
				}
				if ( directory.getDirectoryType().equals(ProjectConstants.MAVEN_TEST_RESOURCE ) ) {
					ProjectWriter.getWriter().addUnitTestResource(directory.getDirectoryPath(), new File(resolvedExtend));
				}   
            }
			

			//donot refresh parent pom since we're not sure if it is present in workspace
			
		}
	}
	
}