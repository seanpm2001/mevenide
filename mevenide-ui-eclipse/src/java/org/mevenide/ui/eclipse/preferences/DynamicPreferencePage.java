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
package org.mevenide.ui.eclipse.preferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.mevenide.util.StringUtils;


/**  
 * 
 * @author <a href="mailto:rhill2@free.fr">Gilles Dodinet</a>
 * @version $Id$
 * 
 */
public class DynamicPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
    
    private List properties;
    
    private Map editors = new HashMap();
    
    private PreferencesManager preferencesManager;
    
    public DynamicPreferencePage() {
        super();
        preferencesManager = PreferencesManager.getDynamicPreferencesManager();
		preferencesManager.loadPreferences();
    }
    
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        if( properties != null ) {
            GridLayout layout = new GridLayout();
            layout.numColumns = 2;
            for ( Iterator it = properties.iterator(); it.hasNext(); ) {
                PluginProperty pluginProperty = (PluginProperty) it.next();
                StringFieldEditor editor = createPluginPropertyEditor(composite, pluginProperty);
                editors.put(pluginProperty, editor);
            }
        }
        return composite; 
	}
  
	private StringFieldEditor createPluginPropertyEditor(Composite parent, PluginProperty pluginProperty) {
        String propertyName = pluginProperty.getName(); 
        String propertyDefault = pluginProperty.getDefault();
        String propertyLabel = pluginProperty.getLabel();
        String propertyDescription = pluginProperty.getDescription();
        String propertyType = pluginProperty.getType();
        String pageId = pluginProperty.getPageId();
        
        StringFieldEditor editor = new StringFieldEditor(pageId + "." + propertyName, propertyLabel, parent);
        editor.fillIntoGrid(parent, 2);
        
        editor.setPreferenceStore(preferencesManager.getPreferenceStore());
        editor.load();

        editor.setEmptyStringAllowed(!pluginProperty.isRequired());
        
        String toolTip = propertyName + " : " + 
        				(!StringUtils.isNull(propertyDescription) ? propertyDescription : "No available description");
        editor.getLabelControl(parent).setToolTipText(toolTip);
        
        if ( StringUtils.isNull(editor.getStringValue()) && 
                !StringUtils.isNull(propertyDefault) ) {
            editor.setStringValue(propertyDefault);
        }
        
        return editor;
    }

    public void init(IWorkbench workbench) {
    }
	
    public List getProperties() {
        return properties;
    }
    
    public void setProperties(List properties) {
        this.properties = properties;
    }
    
    protected void performApply() {
        performOk();
    }
    
    protected void performDefaults() {
        // TODO Auto-generated method stub
        super.performDefaults();
    }
    
    
    public boolean performOk() {
        for (Iterator it = editors.keySet().iterator(); it.hasNext(); ) {
            PluginProperty pluginProperty = (PluginProperty) it.next();
            StringFieldEditor editor = (StringFieldEditor) editors.get(pluginProperty);
            String pageId = pluginProperty.getPageId();
            String propertyName = pluginProperty.getName();
            preferencesManager.setValue(pageId + "." + propertyName, editor.getStringValue());
        }
        return preferencesManager.store();
    }
}
