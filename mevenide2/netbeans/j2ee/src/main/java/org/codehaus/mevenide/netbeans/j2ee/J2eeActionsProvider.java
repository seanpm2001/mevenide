/* ==========================================================================
 * Copyright 2005-2006 Mevenide Team
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

package org.codehaus.mevenide.netbeans.j2ee;

import java.io.InputStream;
import java.util.ArrayList;
import org.codehaus.mevenide.netbeans.api.NbMavenProject;
import org.codehaus.mevenide.netbeans.spi.actions.AbstractMavenActionsProvider;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.ActionProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;

/**
 * j2ee specific defaults for project running and debugging..
 * @author mkleint
 */
public class J2eeActionsProvider extends AbstractMavenActionsProvider {

    private ArrayList<String> supported;
    /** Creates a new instance of J2eeActionsProvider */
    public J2eeActionsProvider() {
        supported = new ArrayList<String>();
        supported.add(NbMavenProject.TYPE_WAR);
        supported.add(NbMavenProject.TYPE_EAR);
        supported.add(NbMavenProject.TYPE_EJB);
    }
    
    
    public InputStream getActionDefinitionStream() {
        String path = "/org/codehaus/mevenide/netbeans/j2ee/webActionMappings.xml"; //NOI18N
        InputStream in = getClass().getResourceAsStream(path);
        assert in != null : "no instream for " + path;  //NOI18N
        return in;
    }

    @Override
    public boolean isActionEnable(String action, Project project, Lookup lookup) {
        if (ActionProvider.COMMAND_RUN_SINGLE.equals(action) || 
            ActionProvider.COMMAND_DEBUG_SINGLE.equals(action)) {
            //only enable for doc root fileobjects..
            FileObject[] fos = extractFileObjectsfromLookup(lookup);
            if (fos != null) {
                Sources srcs = project.getLookup().lookup(Sources.class);
                SourceGroup[] grp = srcs.getSourceGroups("doc_root"); //NOI18N J2EE
                for (int i = 0; i < grp.length; i++) {
                    String relPath = FileUtil.getRelativePath(grp[i].getRootFolder(), fos[0]);
                    if (relPath != null) {
                        return true;
                    }
                }
            }
            return false;
        } else if (ActionProvider.COMMAND_RUN.equals(action) || 
                   ActionProvider.COMMAND_DEBUG.equals(action)) {
            //performance, don't read the xml file to figure enablement..
            NbMavenProject mp = project.getLookup().lookup(NbMavenProject.class);
            return supported.contains(mp.getPackagingType());
        } else {
            return false;
        }
    }
}
