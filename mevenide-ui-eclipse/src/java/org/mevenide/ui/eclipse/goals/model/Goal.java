/* ==========================================================================
 * Copyright 2003-2004 Apache Software Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * =========================================================================
 */
package org.mevenide.ui.eclipse.goals.model;

/**  
 * 
 * @author Gilles Dodinet (gdodinet@wanadoo.fr)
 * @version $Id: Goal.java,v 1.1 7 sept. 2003 Exp gdodinet 
 * 
 */
public class Goal extends Element {
	public static final String DEFAULT_GOAL = "(default)"; 
	
	private Plugin plugin ;
	
	public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

	public boolean equals(Object obj) {
		return (obj instanceof Goal) 
				&& ((Goal) obj).getFullyQualifiedName().equals(getFullyQualifiedName());
	}
	
	public String getFullyQualifiedName() {
		return (plugin != null ? plugin.getName() + ":" : "") + getName();
	}
}
