/* ==========================================================================
 * Copyright 2006 Mevenide Team
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

import java.util.Arrays;
import java.util.List;
import org.codehaus.mevenide.netbeans.execute.PrerequisitesChecker;
import org.codehaus.mevenide.netbeans.execute.RunConfig;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleProvider;
import org.netbeans.spi.project.ActionProvider;

/**
 *
 * @author mkleint
 */
public class J2EEPrerequisitesChecker implements PrerequisitesChecker {

    private List applicableActions = Arrays.asList(new String[] {
        ActionProvider.COMMAND_RUN,
        ActionProvider.COMMAND_RUN_SINGLE,
        ActionProvider.COMMAND_DEBUG,
        ActionProvider.COMMAND_DEBUG_SINGLE
    });
    
    /** Creates a new instance of J2EEPrerequisitesChecker */
    public J2EEPrerequisitesChecker() {
    }

    public boolean checkRunConfig(String actionName, RunConfig config) {
        if (!applicableActions.contains(actionName)) {
            return true;
        }
        J2eeModuleProvider provider = (J2eeModuleProvider)config.getProject().getLookup().lookup(J2eeModuleProvider.class);
        if (provider != null) {
            boolean isReady = provider.getConfigSupport().ensureConfigurationReady();
            //TODO report not-readiness.
        }
        return true;
    }
    
}
