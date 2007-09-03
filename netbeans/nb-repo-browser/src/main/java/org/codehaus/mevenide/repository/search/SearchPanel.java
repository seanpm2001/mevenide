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

package org.codehaus.mevenide.repository.search;

import java.util.StringTokenizer;
import javax.swing.JTextField;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.maven.archiva.indexer.lucene.LuceneQuery;
import org.apache.maven.archiva.indexer.record.StandardIndexRecordFields;
import org.codehaus.mevenide.indexer.LocalRepositoryIndexer;

/**
 *
 * @author  mkleint
 */
public class SearchPanel extends javax.swing.JPanel {
    
    /** Creates new form SearchPanel */
    public SearchPanel() {
        initComponents();
    }
    
    LuceneQuery createLuceneQuery() throws ParseException {
        StringBuffer buff = new StringBuffer();
        appendField(buff, StandardIndexRecordFields.GROUPID_EXACT, txtGroupId, false);
        appendField(buff, StandardIndexRecordFields.ARTIFACTID_EXACT, txtArtifactId, false);
        appendField(buff, StandardIndexRecordFields.PROJECT_NAME, txtName, false);
        appendField(buff, StandardIndexRecordFields.PROJECT_DESCRIPTION, txtDescription, false);
        appendField(buff, StandardIndexRecordFields.PACKAGING, txtPackaging, false);
        appendField(buff, StandardIndexRecordFields.CLASSES, txtClasses, false);
        
        if (txtAny.getText().trim().length() > 0) {
            Query multi = LocalRepositoryIndexer.parseMultiFieldQuery(txtAny.getText());
            buff.append(" ").append(multi.toString());
        }
        System.out.println("search query=" + buff.toString());
        
        return new LuceneQuery(LocalRepositoryIndexer.parseQuery(buff.toString()));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblAny = new javax.swing.JLabel();
        txtAny = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblGroupId = new javax.swing.JLabel();
        txtGroupId = new javax.swing.JTextField();
        lblArtifactId = new javax.swing.JLabel();
        txtArtifactId = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        lblPackaging = new javax.swing.JLabel();
        txtPackaging = new javax.swing.JTextField();
        lblClasses = new javax.swing.JLabel();
        txtClasses = new javax.swing.JTextField();

        lblAny.setLabelFor(txtAny);
        lblAny.setText("Any Field:");

        lblGroupId.setLabelFor(txtGroupId);
        lblGroupId.setText("GroupId:");

        lblArtifactId.setLabelFor(txtArtifactId);
        lblArtifactId.setText("ArtifactId:");

        lblName.setLabelFor(txtName);
        lblName.setText("Name:");

        lblDescription.setLabelFor(txtDescription);
        lblDescription.setText("Description:");

        lblPackaging.setLabelFor(txtPackaging);
        lblPackaging.setText("Packaging:");

        lblClasses.setLabelFor(txtClasses);
        lblClasses.setText("ClassName:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblAny)
                    .add(lblGroupId)
                    .add(lblArtifactId)
                    .add(lblName)
                    .add(lblDescription)
                    .add(lblPackaging)
                    .add(lblClasses))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtAny, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .add(txtGroupId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .add(txtArtifactId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .add(txtName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .add(txtDescription, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .add(txtPackaging, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .add(txtClasses, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(12, 12, 12)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .add(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblAny)
                    .add(txtAny, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblGroupId)
                    .add(txtGroupId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblArtifactId)
                    .add(txtArtifactId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblName)
                    .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDescription)
                    .add(txtDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblPackaging)
                    .add(txtPackaging, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblClasses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtClasses, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void appendField(StringBuffer buff, String field, JTextField txtField, boolean escape) {
        StringTokenizer tokenizer = new StringTokenizer(txtField.getText().trim());
        while (tokenizer.hasMoreTokens()) {
            buff.append(" +").append(field).append(":\"").append(escape ? QueryParser.escape(tokenizer.nextToken()) : tokenizer.nextToken()).append("\"");
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAny;
    private javax.swing.JLabel lblArtifactId;
    private javax.swing.JLabel lblClasses;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblGroupId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPackaging;
    private javax.swing.JTextField txtAny;
    private javax.swing.JTextField txtArtifactId;
    private javax.swing.JTextField txtClasses;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtGroupId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPackaging;
    // End of variables declaration//GEN-END:variables
    
}
