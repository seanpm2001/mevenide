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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.project.MailingList;
import org.apache.maven.project.Project;
import org.mevenide.netbeans.project.MavenProject;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser;
import org.openide.util.NbBundle;

/**
 *
 * @author  Milos Kleint (ca206216@tiscali.cz)
 */
public class ListsPanel extends JPanel implements ProjectPanel {
    private static Log logger = LogFactory.getLog(ListsPanel.class);
    
    private boolean propagate;
    private ProjectValidateObserver valObserver;
    private MailingList currentList;
    private Listener listener;
    private MavenProject project;
    boolean doResolve = false;
    /** Creates new form BasicsPanel */
    public ListsPanel(boolean propagateImmediately, boolean enable, MavenProject proj) {
        initComponents();
        propagate = propagateImmediately;
        project = proj;
        valObserver = null;
        //TODO add listeners for immediatePropagation stuff.
        setName(NbBundle.getMessage(ListsPanel.class, "ListsPanel.name"));
        setEnableFields(enable);
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String url = txtArchive.getText().trim();
                url = project.getPropertyResolver().resolveString(url);
                if (url.startsWith("http://")) {
                    try {
                        URL link = new URL(url);
                        HtmlBrowser.URLDisplayer.getDefault().showURL(link);
                    } catch (MalformedURLException exc) {
                        NotifyDescriptor error = new NotifyDescriptor.Message("Is not a valid URL.", NotifyDescriptor.WARNING_MESSAGE);
                        DialogDisplayer.getDefault().notify(error);
                    }
                    
                }
            }
        });
        lstLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void setEnableFields(boolean enable) {
        txtArchive.setEditable(enable);
        txtName.setEditable(enable);
        txtSubscribe.setEditable(enable);
        txtUnsubscribe.setEditable(enable);
        btnAdd.setEnabled(enable);
        btnEdit.setEnabled(enable);
        btnRemove.setEnabled(enable);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        lblLists = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblArchive = new javax.swing.JLabel();
        txtArchive = new javax.swing.JTextField();
        lblSubscribe = new javax.swing.JLabel();
        txtSubscribe = new javax.swing.JTextField();
        lblUnsubscribe = new javax.swing.JLabel();
        txtUnsubscribe = new javax.swing.JTextField();
        spLists = new javax.swing.JScrollPane();
        lstLists = new javax.swing.JList();
        btnView = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        lblLists.setLabelFor(lstLists);
        lblLists.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.lblLists.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(lblLists, gridBagConstraints);

        btnAdd.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.btnAdd.text"));
        btnAdd.setActionCommand("btnAdd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(btnAdd, gridBagConstraints);

        btnEdit.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.btnEdit.text"));
        btnEdit.setActionCommand("btnEdit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(btnEdit, gridBagConstraints);

        btnRemove.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.btnRemove.text"));
        btnRemove.setActionCommand("btnRemove");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(btnRemove, gridBagConstraints);

        lblName.setLabelFor(txtName);
        lblName.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.lblName.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(lblName, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 0, 0);
        add(txtName, gridBagConstraints);

        lblArchive.setLabelFor(txtArchive);
        lblArchive.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.lblArchive.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(lblArchive, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(txtArchive, gridBagConstraints);

        lblSubscribe.setLabelFor(txtSubscribe);
        lblSubscribe.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.lblLSubscribe.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(lblSubscribe, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 0, 0);
        add(txtSubscribe, gridBagConstraints);

        lblUnsubscribe.setLabelFor(txtUnsubscribe);
        lblUnsubscribe.setText(org.openide.util.NbBundle.getMessage(ListsPanel.class, "ListsPanel.lblUnsubscribe.text"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(lblUnsubscribe, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 0, 0);
        add(txtUnsubscribe, gridBagConstraints);

        spLists.setPreferredSize(new java.awt.Dimension(300, 131));
        spLists.setViewportView(lstLists);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(spLists, gridBagConstraints);

        btnView.setText("View...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(btnView, gridBagConstraints);

    }//GEN-END:initComponents
    
    public void addNotify() {
        super.addNotify();
        listener = new Listener();
        btnAdd.addActionListener(listener);
        btnEdit.addActionListener(listener);
        btnRemove.addActionListener(listener);
        lstLists.addListSelectionListener(listener);
    }
    
    public void removeNotify() {
        super.removeNotify();
        btnAdd.removeActionListener(listener);
        btnEdit.removeActionListener(listener);
        btnRemove.removeActionListener(listener);
        lstLists.removeListSelectionListener(listener);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel lblArchive;
    private javax.swing.JLabel lblLists;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSubscribe;
    private javax.swing.JLabel lblUnsubscribe;
    private javax.swing.JList lstLists;
    private javax.swing.JScrollPane spLists;
    private javax.swing.JTextField txtArchive;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSubscribe;
    private javax.swing.JTextField txtUnsubscribe;
    // End of variables declaration//GEN-END:variables
    
    public void setProject(Project project, boolean resolve) {
//TODO        setEnableFields(!resolve);                
        doResolve = resolve;
        List list = project.getMailingLists();
        DefaultListModel model = new DefaultListModel();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MailingList mail = (MailingList)it.next();
                // need to copy and use fresh instances, for easier rollback
                MailingList modelMail = new MailingList();
                modelMail.setName(mail.getName());
                modelMail.setArchive(mail.getArchive());
                modelMail.setSubscribe(mail.getSubscribe());
                modelMail.setUnsubscribe(mail.getUnsubscribe());
                model.addElement(modelMail);
            }
            lstLists.setModel(model);
        }
        fillValues(null);
        // nothing selected -> disable
        btnRemove.setEnabled(false);
        btnEdit.setEnabled(false);
    }
   
    private String getValue(String value, boolean resolve) {
        if (resolve) {
            return project.getPropertyResolver().resolveString(value);
        }
        return value;
    }
  
    private void fillValues(MailingList list) {
        if (list == null) {
            txtName.setText("");
            txtArchive.setText("");
            txtSubscribe.setText("");
            txtUnsubscribe.setText("");
        } else {
            txtName.setText(list.getName() == null ? "" : getValue(list.getName(), doResolve));
            txtArchive.setText(list.getArchive() == null ? "" : getValue(list.getArchive(), doResolve));
            txtSubscribe.setText(list.getSubscribe() == null ? "" : getValue(list.getSubscribe(), doResolve));
            txtUnsubscribe.setText(list.getUnsubscribe() == null ? "" : getValue(list.getUnsubscribe(), doResolve));
        }
        
    }
    
    public Project copyProject(Project project) {
        // when copying over, we will discard the current instances in the project with our local fresh ones.
        // I hope that is ok, and the mailing lists don't have custom properties.
        DefaultListModel model = (DefaultListModel)lstLists.getModel();
        ArrayList list = new ArrayList(model.size() + 5);
        Enumeration en = model.elements();
        while (en.hasMoreElements()) {
            Object obj = en.nextElement();
            list.add(obj);
        }
        project.setMailingLists(list);
        return project;
    }
    
    public void setValidateObserver(ProjectValidateObserver observer) {
        valObserver = observer;
    }
    
    /**
     * returns 0 for ok, otherwise a integer code.
     */
    private int doValidateCheck() {
        if (txtName.getText().trim().length() == 0) {
            return 1;
        }
        return  0;
    }
    
    public boolean isInValidState() {
        // is always valid, since we can continue, error messages only happen when the
        // attemp to add to list is done.. if it fails, it's not commited, thus the state is always valid.
        return true;
    }
    
    public String getValidityMessage() {
        int retCode = doValidateCheck();
        String message = "";
        // initially and when nothing is selected don't show message.
        // when adding the currentList should be non-null
        if (retCode == 1 && currentList != null) {
            message = NbBundle.getMessage(ListsPanel.class, "ListsPanel.error1.text");
        }
        return message;
    }
    
    private MailingList assignList(MailingList list) {
        logger.debug("Listener called");
        ProjectValidateObserver obs = valObserver;
        if (obs != null) {
            obs.resetValidState(isInValidState(), getValidityMessage());
        }
        if (doValidateCheck() == 0) {
            list.setName(txtName.getText());
            list.setArchive(txtArchive.getText());
            list.setSubscribe(txtSubscribe.getText());
            list.setUnsubscribe(txtUnsubscribe.getText());
            return list;
        }
        return null;
    }
    
    /**
     * action listener for buttons and list selection..
     */
    private class Listener implements ActionListener, ListSelectionListener {
        
        public void actionPerformed(ActionEvent e) {
            if ("btnRemove".equals(e.getActionCommand())) //NOI18N
            {
                DefaultListModel model = (DefaultListModel)lstLists.getModel();
                int index = lstLists.getSelectedIndex();
                model.removeElementAt(index);
                while (index >= model.size()) {
                    index = index - 1;
                }
                if (index > -1) {
                    lstLists.setSelectedIndex(index);
                }
            }
            if ("btnEdit".equals(e.getActionCommand())) //NOI18N
            {
                if (currentList != null) {
                    assignList(currentList);
                } else {
                    logger.debug("Something wrong, no currentList selected when editing"); //NOI18N
                }
            }
            if ("btnAdd".equals(e.getActionCommand())) //NOI18N
            {
                currentList = new MailingList();
                currentList = assignList(currentList);
                if (currentList != null) {
                    DefaultListModel model = (DefaultListModel)lstLists.getModel();
                    model.addElement(currentList);
                    lstLists.setSelectedValue(currentList, true);
                }
            }
        }
        
        public void valueChanged(ListSelectionEvent e) {
            if (lstLists.getSelectedIndex() == -1) {
                currentList = null;
                btnRemove.setEnabled(false);
                btnEdit.setEnabled(false);
            } else {
                currentList = (MailingList)lstLists.getSelectedValue();
                //TEMP                btnRemove.setEnabled(true);
                //TEMP                btnEdit.setEnabled(true);
            }
            fillValues(currentList);
        }
    }
    
}
