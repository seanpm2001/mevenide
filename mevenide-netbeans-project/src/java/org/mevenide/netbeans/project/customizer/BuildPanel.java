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
package org.mevenide.netbeans.project.customizer;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.Build;
import org.apache.maven.project.Project;
import org.mevenide.netbeans.project.MavenProject;
import org.mevenide.project.ProjectConstants;
import org.mevenide.project.source.SourceDirectoryUtil;
import org.openide.util.NbBundle;

/**
 *
 * @author  Milos Kleint (ca206216@tiscali.cz)
 */
public class BuildPanel extends JPanel implements ProjectPanel {
    private static Log logger = LogFactory.getLog(BuildPanel.class);
    
    private boolean propagate;
    private ProjectValidateObserver valObserver;
    private DocumentListener listener;
    private MavenProject project;
    /** Creates new form BuildPanel */
    public BuildPanel( boolean propagateImmediately, boolean enable, MavenProject proj) {
        initComponents();
        propagate = propagateImmediately;
        valObserver = null;
        project = proj;
        //TODO add listeners for immediatePropagation stuff.
        setName(NbBundle.getMessage(BuildPanel.class, "BuildPanel.name"));
        setEnableFields(enable);
    }
    
    public void setEnableFields(boolean enable) {
        txtAspectSrc.setEditable(enable);
        txtIntTestSrc.setEditable(enable);
        txtSrc.setEditable(enable);
        txtTestSrc.setEditable(enable);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        lblSrc = new javax.swing.JLabel();
        txtSrc = new javax.swing.JTextField();
        lblTestSrc = new javax.swing.JLabel();
        txtTestSrc = new javax.swing.JTextField();
        lblAspectSrc = new javax.swing.JLabel();
        txtAspectSrc = new javax.swing.JTextField();
        lblIntTestSrc = new javax.swing.JLabel();
        txtIntTestSrc = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        lblSrc.setLabelFor(txtSrc);
        lblSrc.setText(org.openide.util.NbBundle.getMessage(BuildPanel.class, "BuildPanel.txtSrc.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblSrc, gridBagConstraints);

        txtSrc.setPreferredSize(new java.awt.Dimension(60, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        add(txtSrc, gridBagConstraints);

        lblTestSrc.setLabelFor(txtTestSrc);
        lblTestSrc.setText(org.openide.util.NbBundle.getMessage(BuildPanel.class, "BuildPanel.txtTestSrc.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblTestSrc, gridBagConstraints);

        txtTestSrc.setPreferredSize(new java.awt.Dimension(60, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        add(txtTestSrc, gridBagConstraints);

        lblAspectSrc.setLabelFor(txtAspectSrc);
        lblAspectSrc.setText(org.openide.util.NbBundle.getMessage(BuildPanel.class, "BuildPanel.txtAspectSrc.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblAspectSrc, gridBagConstraints);

        txtAspectSrc.setPreferredSize(new java.awt.Dimension(60, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        add(txtAspectSrc, gridBagConstraints);

        lblIntTestSrc.setLabelFor(txtIntTestSrc);
        lblIntTestSrc.setText(org.openide.util.NbBundle.getMessage(BuildPanel.class, "BuildPanel.txtIntTestSrc.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblIntTestSrc, gridBagConstraints);

        txtIntTestSrc.setPreferredSize(new java.awt.Dimension(60, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(txtIntTestSrc, gridBagConstraints);

    }//GEN-END:initComponents
    
    public Project copyProject(Project project) {
        SourceDirectoryUtil.addSource(project, txtSrc.getText().trim(), ProjectConstants.MAVEN_SRC_DIRECTORY);
        SourceDirectoryUtil.addSource(project, txtTestSrc.getText().trim(), ProjectConstants.MAVEN_TEST_DIRECTORY);
        //        SourceDirectoryUtil.addSource(project, txtIntTestSrc.getText().trim(), ProjectConstants.MAVEN_INTEGRATION_TEST_DIRECTORY);
        SourceDirectoryUtil.addSource(project, txtAspectSrc.getText().trim(), ProjectConstants.MAVEN_ASPECT_DIRECTORY);
        return project;
    }
    
    public void setProject(Project project, boolean resolve) {
//TODO        setEnableFields(!resolve);        
        Build build = project.getBuild();
        if (build == null) {
            txtSrc.setText(resolve ? getValue("${maven.src.dir}/java", true) : ""); //NOI18N
            txtTestSrc.setText(resolve ? getValue("${maven.src.dir}/test/java", true) : ""); //NOI18N
            txtAspectSrc.setText(""); //NOI18N
            txtIntTestSrc.setText(""); //NOI18N
        } else {
            txtSrc.setText(build.getSourceDirectory() == null ? 
                                (resolve ? getValue("${maven.src.dir}/java", true) : "") :
                                getValue(build.getSourceDirectory(), resolve));
            txtTestSrc.setText(build.getUnitTestSourceDirectory() == null ? 
                                (resolve ? getValue("${maven.src.dir}/test/java", true) : "") :
                                getValue(build.getUnitTestSourceDirectory(), resolve));
            txtAspectSrc.setText(build.getAspectSourceDirectory() == null ? ""
                                    : getValue(build.getAspectSourceDirectory(), resolve));
            txtIntTestSrc.setText(build.getIntegrationUnitTestSourceDirectory() == null ? ""
                                    : getValue(build.getIntegrationUnitTestSourceDirectory(), resolve));
        }
        
    }
    
   private String getValue(String value, boolean resolve) {
        if (resolve) {
            return project.getPropertyResolver().resolveString(value);
        }
        return value;
    }    
    
    public void setValidateObserver(ProjectValidateObserver observer) {
        valObserver = observer;
        if (listener == null) {
            listener = new ValidateListener();
            txtSrc.getDocument().addDocumentListener(listener);
        }
    }
    
    private void doValidate() {
        logger.debug("Listener called");
        ProjectValidateObserver obs = valObserver;
        if (obs != null) {
            obs.resetValidState(isInValidState(), getValidityMessage());
        }
    }
    
    private int doValidateCheck() {
        if (txtSrc.getText().trim().length() == 0) {
            return 1;
        }
        return 0;
    }
    
    public boolean isInValidState() {
        return doValidateCheck() == 0;
    }
    
    public String getValidityMessage() {
        int retCode = doValidateCheck();
        String message = "";
        if (retCode == 1) {
            message = NbBundle.getMessage(BuildPanel.class, "BuildPanel.error1.text");
        }
        return message;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAspectSrc;
    private javax.swing.JLabel lblIntTestSrc;
    private javax.swing.JLabel lblSrc;
    private javax.swing.JLabel lblTestSrc;
    private javax.swing.JTextField txtAspectSrc;
    private javax.swing.JTextField txtIntTestSrc;
    private javax.swing.JTextField txtSrc;
    private javax.swing.JTextField txtTestSrc;
    // End of variables declaration//GEN-END:variables
    
    /**
     * attach to the fields that are validated.
     */
    private class ValidateListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            doValidate();
        }
        
        public void insertUpdate(DocumentEvent e) {
            doValidate();
        }
        
        public void removeUpdate(DocumentEvent e) {
            doValidate();
        }
    }
    
}
