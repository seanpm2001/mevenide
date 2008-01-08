/*
 *  Copyright 2008 Anuradha.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.codehaus.mevenide.netbeans.actions.usages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Anuradha
 */
public class UsedGroup {
  private String name;
  private List<UsedArtifact> usedArtifacts=new  ArrayList<UsedArtifact>();
    public UsedGroup(String name) {
        this.name = name;
    }

    public void removeUsedArtifact(Object o) {
         usedArtifacts.remove(o);
    }

    public boolean addAllUsedArtifacts(Collection<? extends UsedArtifact> c) {
        return usedArtifacts.addAll(c);
    }

    public boolean addUsedArtifact(UsedArtifact e) {
        return usedArtifacts.add(e);
    }

    public List<UsedArtifact> getUsedArtifacts() {
        return new  ArrayList<UsedArtifact>(usedArtifacts);
    }

    public String getName() {
        return name;
    }
  
}
