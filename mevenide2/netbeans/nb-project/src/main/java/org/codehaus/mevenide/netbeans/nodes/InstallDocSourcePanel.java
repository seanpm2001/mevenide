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
package org.codehaus.mevenide.netbeans.nodes;

import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;


/**
 *
 * @author  mkleint
 */
public class InstallDocSourcePanel extends javax.swing.JPanel {

    private static File lastFolder = new File(System.getProperty("user.home")); //NOI18N

    private boolean docs;
    
    /** Creates new form InstallPanel */
    private InstallDocSourcePanel(boolean javadoc) {
        initComponents();
        docs = javadoc;
        if (javadoc) {
            lblFile.setText(org.openide.util.NbBundle.getMessage(InstallDocSourcePanel.class, "TXT_Javadoc_Loc"));
        } else {
            lblFile.setText(org.openide.util.NbBundle.getMessage(InstallDocSourcePanel.class, "TXT_Sources_Loc"));
        }
    }
    
    boolean isJavadoc() {
        return docs;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFile = new javax.swing.JLabel();
        txtFile = new javax.swing.JTextField();
        btnFile = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(lblFile, "Javadoc Location :");

        org.openide.awt.Mnemonics.setLocalizedText(btnFile, org.openide.util.NbBundle.getMessage(InstallDocSourcePanel.class, "BTN_Browse")); // NOI18N
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(lblFile)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnFile)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblFile)
                    .add(btnFile)
                    .add(txtFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileActionPerformed
        JFileChooser chooser = new JFileChooser(lastFolder);
        chooser.setDialogTitle(isJavadoc() ? NbBundle.getMessage(InstallDocSourcePanel.class, "TIT_Select_javadoc_zip")
                                           : NbBundle.getMessage(InstallDocSourcePanel.class, "TIT_Select_source_zip"));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return (f.isDirectory() || f.getName().toLowerCase().endsWith(".jar") || f.getName().toLowerCase().endsWith(".zip")); //NOI18N
            }
            public String getDescription() {
                
                return isJavadoc() ? NbBundle.getMessage(InstallDocSourcePanel.class, "LBL_Select_javadoc_zip")
                                   : NbBundle.getMessage(InstallDocSourcePanel.class, "LBL_Select_source_zip");
            }
        });
        chooser.setMultiSelectionEnabled(false);
        if (txtFile.getText().trim().length() > 0) {
            File fil = new File(txtFile.getText().trim());
            if (fil.exists()) {
                chooser.setSelectedFile(fil);
            }
        }
        int ret = chooser.showDialog(SwingUtilities.getWindowAncestor(this), org.openide.util.NbBundle.getMessage(InstallDocSourcePanel.class, "BTN_Select"));
        if (ret == JFileChooser.APPROVE_OPTION) {
            txtFile.setText(chooser.getSelectedFile().getAbsolutePath());
            txtFile.requestFocusInWindow();
        }

    }//GEN-LAST:event_btnFileActionPerformed

    File getFile() {
        File fil = txtFile.getText().trim().length() > 0 ? new File(txtFile.getText().trim()) : null;
        return fil != null && fil.exists() ? fil : null;
    }
    
    public static File showInstallDialog(boolean javadoc) {
        final InstallDocSourcePanel panel = new InstallDocSourcePanel(javadoc);
        final JButton btnSelect  = new JButton(org.openide.util.NbBundle.getMessage(InstallDocSourcePanel.class, "BTN_Select"));
        btnSelect.setEnabled(panel.getFile() != null);
        panel.addDocListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                btnSelect.setEnabled(panel.getFile() != null);
            }
            public void insertUpdate(DocumentEvent e) {
                btnSelect.setEnabled(panel.getFile() != null);
            }
            public void removeUpdate(DocumentEvent e) {
                btnSelect.setEnabled(panel.getFile() != null);
            }
        });
        Object[] options =  new Object[] {
            btnSelect,
            NotifyDescriptor.CANCEL_OPTION
        };
        String tit = panel.isJavadoc() ? NbBundle.getMessage(InstallDocSourcePanel.class, "TIT_Use_local_docs")
                                 : NbBundle.getMessage(InstallDocSourcePanel.class, "TIT_Use_local_source");
        DialogDescriptor dd = new DialogDescriptor(panel, tit,
                true,
                options,
                btnSelect, 0, HelpCtx.DEFAULT_HELP, null);
        dd.setClosingOptions(options);
        Object ret = DialogDisplayer.getDefault().notify(dd);
        if (ret == btnSelect) {
            lastFolder = panel.getFile().getParentFile();
            return panel.getFile();
        }
        return null;
    }

    private void addDocListener(DocumentListener documentListener) {
        txtFile.getDocument().addDocumentListener(documentListener);
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFile;
    private javax.swing.JLabel lblFile;
    private javax.swing.JTextField txtFile;
    // End of variables declaration//GEN-END:variables
    
}
