/* ==========================================================================
 * Copyright 2004 Apache Software Foundation
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

package org.mevenide.properties;

/**
 * Instances of this interface are able to find out where the given property is defined.
 *
 *
 * @author  <a href="mailto:ca206216@tiscali.cz">Milos Kleint</a>
 */
public interface IPropertyLocator {
    /**
     * Location constant, the given key was not found.
     */
    public static final int LOCATION_NOT_DEFINED = -1;
    /**
     * Location constant, the given key is default. Either maven itself or maven plugin default.
     */
    public static final int LOCATION_DEFAULTS = 0;
    /**
     * Location constant, the given key is in project.properties file.
     */
    public static final int LOCATION_PROJECT = 10;
    /**
     * Location constant, the given key is in ${basedir}/build.properties file.
     */
    public static final int LOCATION_PROJECT_BUILD = 11;
    /**
     * Location constant, the given key is in ${user.home}/build.properties file.
     */
    public static final int LOCATION_USER_BUILD = 12;
    
    /**
     * Returns where the key is located.
     */
    int getPropertyLocation(String key);
    
}
