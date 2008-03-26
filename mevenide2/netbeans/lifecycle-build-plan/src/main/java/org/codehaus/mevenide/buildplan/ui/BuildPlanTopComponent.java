/*
 *  Copyright 2008 Mevenide Team.
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
package org.codehaus.mevenide.buildplan.ui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import org.openide.awt.TabbedPaneFactory;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
public final class BuildPlanTopComponent extends TopComponent {

    private static BuildPlanTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "BuildPlanTopComponent";

    private BuildPlanTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(BuildPlanTopComponent.class, "CTL_BuildPlanTopComponent"));

        setIcon(Utilities.loadImage("org/codehaus/mevenide/buildplan/nodes/buildplangoals.png", true));
        tabpane.addPropertyChangeListener(TabbedPaneFactory.PROP_CLOSE, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                synchronized (BuildPlanTopComponent.class) {
                    JTabbedPane pane = (JTabbedPane) evt.getSource();
                    int sel = pane.getSelectedIndex();
                    pane.removeTabAt(sel);
                    if (pane.getTabCount() == 1) {
                        BuildPlanViewUI bpvui = (BuildPlanViewUI) pane.getComponent(0);
                       
                        pane.removeAll();
                        removeAll();
                        addView(bpvui);

                    }
                }
            }
        });
        putClientProperty("SlidingName", NbBundle.getMessage(BuildPlanTopComponent.class, "CTL_BuildPlanTopComponent")); //NOI18N 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabpane = TabbedPaneFactory.createCloseButtonTabbedPane();

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabpane;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized BuildPlanTopComponent getDefault() {
        if (instance == null) {
            instance = new BuildPlanTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the BuildPlanTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized BuildPlanTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(BuildPlanTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof BuildPlanTopComponent) {
            return (BuildPlanTopComponent) win;
        }
        Logger.getLogger(BuildPlanTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    public void addView(BuildPlanViewUI viewUI) {

        synchronized (BuildPlanTopComponent.class) {

            if (getComponents().length == 0) {

                setName(NbBundle.getMessage(BuildPlanTopComponent.class,
                        "CTL_BuildPlanTopComponent2", viewUI.getName()));
                add(viewUI, BorderLayout.CENTER);
            } else {
                if (tabpane.getParent()==null) {
                    BuildPlanViewUI current = (BuildPlanViewUI) getComponents()[0];
                    tabpane.addTab(NbBundle.getMessage(BuildPlanTopComponent.class,
                            "LBL_Buildplan_of") + current.getName() + "    "/*adding space to prevent overlap of X*/,
                            current);
                    removeAll();
                    add(tabpane, BorderLayout.CENTER);

                    
                }
                //______________________________________________________________

                int componentCount = tabpane.getComponentCount();
                tabpane.addTab(NbBundle.getMessage(BuildPlanTopComponent.class,
                        "LBL_Buildplan_of") + viewUI.getName() + "    "/*adding space to prevent overlap of X*/,
                        viewUI);
                setName(NbBundle.getMessage(BuildPlanTopComponent.class,
                        "CTL_BuildPlanTopComponent"));
                tabpane.setSelectedIndex(componentCount);
            }
            repaint();
            updateUI();
        }
    }

    @Override
    protected void componentOpened() {
        super.componentOpened();
    }

    @Override
    protected void componentClosed() {
        tabpane.removeAll();
        removeAll();
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return BuildPlanTopComponent.getDefault();
        }
    }
}
