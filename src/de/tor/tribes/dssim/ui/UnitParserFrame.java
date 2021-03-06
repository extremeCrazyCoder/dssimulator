/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tor.tribes.dssim.ui;

import de.tor.tribes.dssim.types.UnitHolder;
import de.tor.tribes.dssim.util.UnitManager;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.JButton;

/**
 *
 * @author Torridity
 */
public class UnitParserFrame extends javax.swing.JFrame {

    private HashMap<UnitHolder, Integer> lastUnits = new HashMap<>();

    /**
     * Creates new form UnitParserFrame
     */
    public UnitParserFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jAddAsAttackerButton = new javax.swing.JButton();
        jAddAsDefenderButton = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Truppenparser");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Bitte hier die Truppeninformationen einfügen. Zulässige Einträge können aus den Übersichten, dem Versammlungsplatz oder aus Berichte kommen (Format: 0 0 0 100 30 0 usw.) oder aus der Dorfübersicht (Format: 100 Axtkämpfer usw.)");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                fireParseTroopsEvent(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jAddAsAttackerButton.setText("Als Angreifer einfügen");
        jAddAsAttackerButton.setEnabled(false);
        jAddAsAttackerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fireAddEvent(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jAddAsAttackerButton, gridBagConstraints);

        jAddAsDefenderButton.setText("Als Verteidiger einfügen");
        jAddAsDefenderButton.setEnabled(false);
        jAddAsDefenderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fireAddEvent(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jAddAsDefenderButton, gridBagConstraints);

        jButton3.setText("Schließen");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fireDisposeEvent(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fireParseTroopsEvent(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_fireParseTroopsEvent
        parseTroops();
    }//GEN-LAST:event_fireParseTroopsEvent

    private void fireAddEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fireAddEvent
        JButton b = (JButton) evt.getSource();
        if (!b.isEnabled()) {
            return;
        }
        if (b.equals(jAddAsAttackerButton)) {
            DSWorkbenchSimulatorFrame.getSingleton().insertAttackers(lastUnits);
        } else {
            DSWorkbenchSimulatorFrame.getSingleton().insertDefenders(lastUnits);
        }
    }//GEN-LAST:event_fireAddEvent

    private void fireDisposeEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fireDisposeEvent
        dispose();
    }//GEN-LAST:event_fireDisposeEvent

    private void parseTroops() {
        int units = UnitManager.getSingleton().getUnits().length;
        StringTokenizer t = new StringTokenizer(jTextArea1.getText(), " \t");
        lastUnits.clear();
        if (t.countTokens() == units || t.countTokens() == units - 1) {//allow missing militia
            for (UnitHolder unit : UnitManager.getSingleton().getUnits()) {
                try {
                    Integer amount = Integer.parseInt(t.nextToken());
                    lastUnits.put(unit, amount);
                } catch (Exception e) {
                }
            }
            if (lastUnits.size() < units - 1) {//allow missing militia
                //invalid
                lastUnits.clear();
            }
        } else {
            StringTokenizer tok = new StringTokenizer(jTextArea1.getText(), "\n\r");
            try {
                String line = tok.nextToken();
                for (UnitHolder unit : UnitManager.getSingleton().getUnits()) {
                    if (line.contains(unit.getPlainName())) {
                        //found unit
                        line = line.replaceAll(unit.getPlainName(), "");
                        try {
                            Integer amount = Integer.parseInt(line.trim());
                            lastUnits.put(unit, amount);
                            break;
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e) {
                lastUnits.clear();
            }
        }

        jAddAsAttackerButton.setEnabled(!lastUnits.isEmpty());
        jAddAsDefenderButton.setEnabled(!lastUnits.isEmpty());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new UnitParserFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAddAsAttackerButton;
    private javax.swing.JButton jAddAsDefenderButton;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
