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
package org.mevenide.ui.eclipse.sync.model.dependency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.Dependency;

/**  
 * 
 * @author Gilles Dodinet (gdodinet@wanadoo.fr)
 * @version $Id: DependencyWrapper.java 26 ao�t 2003 Exp gdodinet 
 * 
 */
public class DependencyWrapper {
	private static Log log = LogFactory.getLog(DependencyWrapper.class);
	
	private Dependency dependency;
	private boolean isInherited;
	private DependencyGroup dependencyGroup;
	
	private boolean isReadOnly;
	private boolean isInPom;
	
	public DependencyWrapper(Dependency dependency, boolean isInherited, DependencyGroup group) {
		if ( dependency == null ) {
			throw new RuntimeException("Trying to initialize a DependencyWrapper with a null Exception");
		}
		this.dependency = dependency;
		this.isInherited = isInherited;
		this.dependencyGroup = group;
	}
	
    public Dependency getDependency() {
        return dependency;
    }

        public boolean isInherited() {
        return isInherited;
    }

    public void setInherited(boolean isInherited) {
    	log.debug("setting isInherited to " + (isInherited));
        this.isInherited = isInherited;
        this.dependencyGroup.setDependencyInheritance(this.dependency, isInherited);
    }
    
    public boolean equals(Object obj) {
    	if ( obj == null || !(obj instanceof DependencyWrapper) ) {
    		return false;
    	}
    	DependencyWrapper wrapper = (DependencyWrapper) obj;
    	return this.dependency.equals(wrapper.getDependency())
    			&& this.isInherited == wrapper.isInherited
    			&& this.dependencyGroup.equals(wrapper.dependencyGroup);
    }

    public DependencyGroup getDependencyGroup() {
        return dependencyGroup;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public void setDependencyGroup(DependencyGroup dependencyGroup) {
        this.dependencyGroup = dependencyGroup;
    }

    public boolean isInPom() {
        return isInPom;
    }

    public void setInPom(boolean isInPom) {
        this.isInPom = isInPom;
    }

}
