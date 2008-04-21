/* ==========================================================================
 * Copyright 2008 Mevenide Team
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

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import org.apache.maven.artifact.Artifact;
import org.codehaus.mevenide.indexer.api.NBVersionInfo;
import org.codehaus.mevenide.indexer.api.QueryField;
import org.codehaus.mevenide.indexer.api.RepositoryInfo;
import org.codehaus.mevenide.indexer.api.RepositoryPreferences;
import org.codehaus.mevenide.indexer.api.RepositoryQueries;
import org.codehaus.mevenide.indexer.api.RepositoryUtil;
import org.codehaus.mevenide.netbeans.api.CommonArtifactActions;
import org.codehaus.mevenide.repository.dependency.AddAsDependencyAction;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;

/**
 *
 * @author  mkleint
 */
public class FindResultsPanel extends javax.swing.JPanel implements ExplorerManager.Provider {

    private BeanTreeView btv;
    private ExplorerManager manager;
    private ActionListener close;
    private DialogDescriptor dd;

    /** Creates new form FindResultsPanel */
    private FindResultsPanel() {
        initComponents();
        btv = new BeanTreeView();
        btv.setRootVisible(false);
        manager = new ExplorerManager();
        manager.setRootContext(new AbstractNode(Children.LEAF));
        add(btv, BorderLayout.CENTER);
    }

    FindResultsPanel(ActionListener actionListener, DialogDescriptor d) {
        this();
        close = actionListener;
        dd = d;
    }

    void find(final List<QueryField> fields) {
        Node loadingNode = GroupListChildren.createLoadingNode();
        Children.Array array = new Children.Array();
        array.add(new Node[]{loadingNode});
        manager.setRootContext(new AbstractNode(array));
        RequestProcessor.getDefault().post(new Runnable() {

            public void run() {
                final List<NBVersionInfo> infos = RepositoryQueries.find(fields);
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        manager.setRootContext(createRootNode(infos));
                    }
                });
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnModify = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(btnModify, org.openide.util.NbBundle.getMessage(FindResultsPanel.class, "FindResultsPanel.btnModify.text")); // NOI18N
        btnModify.setFocusable(false);
        btnModify.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModify.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnModify);

        org.openide.awt.Mnemonics.setLocalizedText(btnClose, org.openide.util.NbBundle.getMessage(FindResultsPanel.class, "FindResultsPanel.btnClose.text")); // NOI18N
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClose);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
    if (close != null) {
        close.actionPerformed(evt);
    }
}//GEN-LAST:event_btnCloseActionPerformed

private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
    Object ret = DialogDisplayer.getDefault().notify(dd);
    if (ret == DialogDescriptor.OK_OPTION) {
        find(((FindInRepoPanel) dd.getMessage()).getQuery());
    }
}//GEN-LAST:event_btnModifyActionPerformed

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    private Node createRootNode(List<NBVersionInfo> infos) {
        Node node = null;
        Map<String, List<NBVersionInfo>> map = new HashMap<String, List<NBVersionInfo>>();

        for (NBVersionInfo nbvi : infos) {
            String key = nbvi.getGroupId() + " : " + nbvi.getArtifactId();
            List<NBVersionInfo> get = map.get(key);
            if (get == null) {
                get = new ArrayList<NBVersionInfo>();
                map.put(key, get);
            }
            get.add(nbvi);
        }
        Set<String> keySet = map.keySet();
        if (keySet.size() > 0) {
            Children.Array array = new Children.Array();

            List<String> keyList = new ArrayList<String>(keySet);
            Collections.sort(keyList);
            node = new AbstractNode(array);
            for (String key : keyList) {
                array.add(new Node[]{new ArtifactNode(key, map.get(key))});
            }
        }
        return node;

    }

    private static class ArtifactNode extends AbstractNode {

        private List<NBVersionInfo> versionInfos;

        public ArtifactNode(String name, final List<NBVersionInfo> list) {
            super(new Children.Keys<NBVersionInfo>() {

                @Override
                protected Node[] createNodes(NBVersionInfo arg0) {


                    return new Node[]{new VersionNode(arg0, arg0.isJavadocExists(),
                                arg0.isSourcesExists())
                            };
                }

                @Override
                protected void addNotify() {

                    setKeys(list);
                }
            });
            this.versionInfos = list;
            setName(name);
            setDisplayName(name);
        }

        @Override
        public Image getIcon(int arg0) {
            Image badge = Utilities.loadImage("org/codehaus/mevenide/repository/ArtifactBadge.png", true); //NOI18N

            return badge;
        }

        @Override
        public Image getOpenedIcon(int arg0) {
            return getIcon(arg0);
        }

        public List<NBVersionInfo> getVersionInfos() {
            return new ArrayList<NBVersionInfo>(versionInfos);
        }
    }

    private static class VersionNode extends AbstractNode {

        private NBVersionInfo nbvi;
        private boolean hasJavadoc;
        private boolean hasSources;

        /** Creates a new instance of VersionNode */
        public VersionNode(NBVersionInfo versionInfo, boolean javadoc, boolean source) {
            super(Children.LEAF);

            hasJavadoc = javadoc;
            hasSources = source;
            this.nbvi = versionInfo;

            setName(versionInfo.getVersion());
            setDisplayName(versionInfo.getVersion() + " [ " + versionInfo.getType() + (versionInfo.getClassifier() != null ? ("," + versionInfo.getClassifier()) : "") + " ] " + " - " + versionInfo.getRepoId());

            setIconBaseWithExtension("org/codehaus/mevenide/repository/DependencyJar.gif"); //NOI18N

        }

        @Override
        public Action[] getActions(boolean context) {
            Artifact artifact = RepositoryUtil.createArtifact(nbvi);
            Action[] retValue;
            RepositoryInfo info = RepositoryPreferences.getInstance().getRepositoryInfoById(nbvi.getRepoId());
            if (info != null && info.isRemoteDownloadable()) {
                retValue = new Action[]{
                            new AddAsDependencyAction(artifact),
                            CommonArtifactActions.createFindUsages(artifact),
                            null,
                            CommonArtifactActions.createViewProjectHomeAction(artifact),
                            CommonArtifactActions.createViewBugTrackerAction(artifact),
                            CommonArtifactActions.createSCMActions(artifact)
                        };

            } else {


                retValue = new Action[]{
                            new AddAsDependencyAction(artifact),
                            null,
                            CommonArtifactActions.createFindUsages(artifact),
                            null,
                            CommonArtifactActions.createViewJavadocAction(artifact),
                            CommonArtifactActions.createViewProjectHomeAction(artifact),
                            CommonArtifactActions.createViewBugTrackerAction(artifact),
                            CommonArtifactActions.createSCMActions(artifact)
                        };
            }
            return retValue;
        }

        @Override
        public java.awt.Image getIcon(int param) {
            java.awt.Image retValue = super.getIcon(param);
            if (hasJavadoc) {
                retValue = Utilities.mergeImages(retValue,
                        Utilities.loadImage("org/codehaus/mevenide/repository/DependencyJavadocIncluded.png"),//NOI18N
                        12, 12);
            }
            if (hasSources) {
                retValue = Utilities.mergeImages(retValue,
                        Utilities.loadImage("org/codehaus/mevenide/repository/DependencySrcIncluded.png"),//NOI18N
                        12, 8);
            }
            return retValue;

        }

        public NBVersionInfo getNBVersionInfo() {
            return nbvi;
        }

        @Override
        public String getShortDescription() {

            return nbvi.toString();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnModify;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
