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

package org.codehaus.mevenide.repository;

import org.codehaus.mevenide.repository.search.SearchResultChildren;
import org.openide.nodes.AbstractNode;

/**
 *
 * @author mkleint
 */
public class ArtifactIdNode extends AbstractNode {
    
    /** Creates a new instance of ArtifactIdNode */
    public ArtifactIdNode(String id, String art) {
        super(new SearchResultChildren(id, art));
        setName(art);
        setDisplayName(art);
    }
    
    
}