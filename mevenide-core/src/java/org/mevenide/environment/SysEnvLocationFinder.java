/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
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
package org.mevenide.environment;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.taskdefs.Execute;

/**  
 * 
 * @author Gilles Dodinet (gdodinet@wanadoo.fr)
 * @version $Id: SysEnvLocationFinder.java,v 1.1 15 nov. 2003 Exp gdodinet 
 * 
 */
public class SysEnvLocationFinder extends AbstractLocationFinder {
    private static Log log = LogFactory.getLog(SysEnvLocationFinder.class); 
    
    private static SysEnvLocationFinder locationFinder = new SysEnvLocationFinder();
    
	private Properties envProperties;
	
    private SysEnvLocationFinder() {
        loadEnvironment();
    }
    
    
	/**
	 * this is a slighty modified version of org.apache.tools.ant.taskdefs.Property#loadEnvironment()
	 * (c) ASF
	 */
	private void loadEnvironment() {
	   Properties props = new Properties();
	   Vector osEnv = Execute.getProcEnvironment();
	   log.debug("loading environment");
	   for (Enumeration e = osEnv.elements(); e.hasMoreElements();) {
		   String entry = (String) e.nextElement();
		   int pos = entry.indexOf('=');
		   if (pos == -1) {
			log.debug("Ignoring: " + entry);
		   } else {
			   props.put(entry.substring(0, pos),
			   entry.substring(pos + 1));
		   }
	   }
	   envProperties = props;
	   log.debug("environment loaded");
	}
    
    static SysEnvLocationFinder getInstance() {
        return locationFinder; 
    }
    
    public String getJavaHome() {
		return (String) envProperties.get("JAVA_HOME");
    }
    
    public String getMavenHome() {
		return (String) envProperties.get("MAVEN_HOME");
    }
    
    public String getMavenLocalHome() {
		return (String) envProperties.get("MAVEN_HOME_LOCAL");
    }
    
    public String getMavenLocalRepository() {
		return (String) envProperties.get("MAVEN_REPO_LOCAL");
    }
    
    public String getMavenPluginsDir() {
        return null;
    }
}
