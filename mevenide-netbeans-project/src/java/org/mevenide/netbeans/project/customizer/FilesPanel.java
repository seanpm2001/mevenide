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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.Project;
import org.mevenide.netbeans.project.MavenProject;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;


/**
 *
 * @author  Milos Kleint (ca206216@tiscali.cz)
 */
public class FilesPanel extends JPanel implements ProjectPanel {
    private static Log logger = LogFactory.getLog(FilesPanel.class);
    
    private boolean propagate;
    private ProjectValidateObserver valObserver;
    private DocumentListener listener;
    private MavenProject project;
    
    /** Creates new form BasicsPanel */
    public FilesPanel(boolean propagateImmediately, boolean enable, MavenProject proj) {
        initComponents();
	project = proj;
        propagate = propagateImmediately;
        valObserver = null;
        setEnableFields(enable);
        lblProject.setIcon(new ImageIcon(Utilities.loadImage("org/mevenide/netbeans/project/resources/LocProject.png")));
        lblBuild.setIcon(new ImageIcon(Utilities.loadImage("org/mevenide/netbeans/project/resources/LocBuild.png")));
        lblUser.setIcon(new ImageIcon(Utilities.loadImage("org/mevenide/netbeans/project/resources/LocUser.png")));
    }
    
    public void setEnableFields(boolean enable) {
        txtBuild.setEditable(enable);
        txtPOMFile.setEditable(enable);
        txtProject.setEditable(enable);
        txtProjectDir.setEditable(enable);
        txtUser.setEditable(enable);
        txtUserDirectory.setEditable(enable);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        lblProjectDir = new javax.swing.JLabel();
        txtProjectDir = new javax.swing.JTextField();
        lblPOMFile = new javax.swing.JLabel();
        txtPOMFile = new javax.swing.JTextField();
        lblPOMFileState = new javax.swing.JLabel();
        lblProject = new javax.swing.JLabel();
        txtProject = new javax.swing.JTextField();
        lblProjectState = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblUserDirectory = new javax.swing.JLabel();
        txtUserDirectory = new javax.swing.JTextField();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblUserState = new javax.swing.JLabel();
        lblBuild = new javax.swing.JLabel();
        txtBuild = new javax.swing.JTextField();
        lblBuildState = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lblProjectDir.setText("Project Directory :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblProjectDir, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(txtProjectDir, gridBagConstraints);

        lblPOMFile.setText("POM File :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblPOMFile, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(txtPOMFile, gridBagConstraints);

        lblPOMFileState.setText("jLabel3");
        lblPOMFileState.setMaximumSize(new java.awt.Dimension(80, 24));
        lblPOMFileState.setMinimumSize(new java.awt.Dimension(80, 24));
        lblPOMFileState.setPreferredSize(new java.awt.Dimension(80, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblPOMFileState, gridBagConstraints);

        lblProject.setText("Project Properties :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblProject, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(txtProject, gridBagConstraints);

        lblProjectState.setText("jLabel5");
        lblProjectState.setMaximumSize(new java.awt.Dimension(80, 24));
        lblProjectState.setMinimumSize(new java.awt.Dimension(80, 24));
        lblProjectState.setPreferredSize(new java.awt.Dimension(80, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblProjectState, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jSeparator1, gridBagConstraints);

        lblUserDirectory.setText("User Directory :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblUserDirectory, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(txtUserDirectory, gridBagConstraints);

        lblUser.setText("User Properies :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblUser, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(txtUser, gridBagConstraints);

        lblUserState.setText("jLabel8");
        lblUserState.setMaximumSize(new java.awt.Dimension(80, 24));
        lblUserState.setMinimumSize(new java.awt.Dimension(80, 24));
        lblUserState.setPreferredSize(new java.awt.Dimension(80, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.1;
        add(lblUserState, gridBagConstraints);

        lblBuild.setText("Build Properties :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(lblBuild, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(txtBuild, gridBagConstraints);

        lblBuildState.setText("jLabel9");
        lblBuildState.setMaximumSize(new java.awt.Dimension(80, 24));
        lblBuildState.setMinimumSize(new java.awt.Dimension(80, 24));
        lblBuildState.setPreferredSize(new java.awt.Dimension(80, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblBuildState, gridBagConstraints);

    }//GEN-END:initComponents
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBuild;
    private javax.swing.JLabel lblBuildState;
    private javax.swing.JLabel lblPOMFile;
    private javax.swing.JLabel lblPOMFileState;
    private javax.swing.JLabel lblProject;
    private javax.swing.JLabel lblProjectDir;
    private javax.swing.JLabel lblProjectState;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserDirectory;
    private javax.swing.JLabel lblUserState;
    private javax.swing.JTextField txtBuild;
    private javax.swing.JTextField txtPOMFile;
    private javax.swing.JTextField txtProject;
    private javax.swing.JTextField txtProjectDir;
    private javax.swing.JTextField txtUser;
    private javax.swing.JTextField txtUserDirectory;
    // End of variables declaration//GEN-END:variables
    
    public void setProject(Project proj, boolean resolve) {
        txtBuild.setText("build.properties");
        txtProject.setText("project.properties");
        txtUser.setText("build.properties");
        txtPOMFile.setText("project.xml");
        txtProjectDir.setText(project.getContext().getProjectDirectory().toString());
        txtUserDirectory.setText(project.getContext().getUserDirectory().toString());
        File userFile = new File(project.getContext().getUserDirectory(), "build.properties");
        File projectFile = new File(project.getContext().getProjectDirectory(), "project.properties");
        File pomFile = new File(project.getContext().getProjectDirectory(), "project.xml");
        File buildFile = new File(project.getContext().getProjectDirectory(), "build.properties");
        if (userFile.exists()) {
            lblUserState.setText("");
        } else {
            lblUserState.setText("Doesn't exist");
        }
        if (buildFile.exists()) {
            lblBuildState.setText("");
        } else {
            lblBuildState.setText("Doesn't exist");
        }
        if (projectFile.exists()) {
            lblProjectState.setText("");
        } else {
            lblProjectState.setText("Doesn't exist");
        }
        if (pomFile.exists()) {
            lblPOMFileState.setText("");
        } else {
            lblPOMFileState.setText("Doesn't exist");
        }
    }
    
    public Project copyProject(Project project) {
        return project;
    }
    
    public void setValidateObserver(ProjectValidateObserver observer) {
        valObserver = observer;
    }
    
    public boolean isInValidState() {
        return true;
    }
    
    public String getValidityMessage() {
        return "";
    }
    
}
