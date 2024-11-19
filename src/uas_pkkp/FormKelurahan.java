package uas_pkkp;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormKelurahan extends javax.swing.JFrame {

    // membuat object
    private Connection con;
    private Statement stat;
    private ResultSet res;
    
    private Object[][] datatable = null;
    private String[] header = {
        "ID Kelurahan", "Kelurahan", "ID Kecamatan", "Kecamatan", "ID Kab/Kota", "Kabupaten/Kota"
    };
    Boolean ada = false;
    Boolean edit = false;
    private boolean True;
    
    final String querySelect = "SELECT dkl.id, dkl.name, dkl.id_kecamatan, dkc.name, dkc.id_kabkota, dkk.name FROM data_kelurahan dkl, data_kecamatan dkc, data_kabkota dkk WHERE dkl.id_kecamatan = dkc.id AND dkc.id_kabkota = dkk.id";
    final String queryInsert = "INSERT INTO data_kelurahan(id, name, id_kecamatan) VALUES(?,?,?)";
    final String queryUpdate = "UPDATE data_kelurahan SET id=?, name=?, id_kecamatan=?  WHERE id=?";
    final String queryDelete = "DELETE FROM data_kelurahan WHERE id=?";

    public FormKelurahan() {
        initComponents();
        open_db();;
        aktif(false);
        setTombol(true);
        baca_data();
        tblKelurahan.setDefaultEditor(Object.class, null);
    }

    //method buka database
    private void open_db() {
        try {
            KoneksiMysql kon = new KoneksiMysql("localhost", "root", "", "dbpkkp");
            con = kon.getConnection();
            System.out.println("Koneksi DB : Succesfully");
        } catch (Exception e) {
            System.out.println("Koneksi DB : Failed " + e);
        }
    }

    //method baca database
    private void baca_data() {
        Statement stm;
        try {
            stm = con.createStatement(res.TYPE_SCROLL_INSENSITIVE, res.CONCUR_READ_ONLY);
            res = stm.executeQuery("SELECT dkl.id, dkl.name, dkl.id_kecamatan, dkc.name, dkc.id_kabkota, dkk.name FROM data_kelurahan dkl, data_kecamatan dkc, data_kabkota dkk WHERE dkl.id_kecamatan = dkc.id AND dkc.id_kabkota = dkk.id");

            ResultSetMetaData meta = res.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            while (res.next()) {
                baris = res.getRow();
            }

            datatable = new Object[baris][col];
            int x = 0;
            res.beforeFirst();
            while (res.next()) {
                datatable[x][0] = res.getString("dkl.id");
                datatable[x][1] = res.getString("dkl.name");
                datatable[x][2] = res.getString("dkl.id_kecamatan");
                datatable[x][3] = res.getString("dkc.name");
                datatable[x][4] = res.getString("dkc.id_kabkota");
                datatable[x][5] = res.getString("dkk.name");
                x++;
            }
            tblKelurahan.setModel(new DefaultTableModel(datatable, header));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setField(int index) {
        txtID.setText(tblKelurahan.getValueAt(index, 0).toString());
        txtKelurahan.setText(tblKelurahan.getValueAt(index, 1).toString());
        txtIDKecamatan.setText(tblKelurahan.getValueAt(index, 2).toString());
    }

    private void aktif(boolean x) {
        txtID.setEditable(x);
        txtKelurahan.setEditable(x);
        txtIDKecamatan.setEditable(x);
    }

    private void setTombol(boolean t) {
        cmdTambah.setEnabled(t);
        cmdSimpan.setEnabled(!t);
        cmdHapus.setEnabled(t);
        cmdBatal.setEnabled(!t);
        cmdEdit.setEnabled(t);
        btnKeluar.setEnabled(t);
    }

    private void kosong() {
        txtID.setText("");
        txtKelurahan.setText("");
        txtIDKecamatan.setText("");
    }
    
    public void insert_data() {
        PreparedStatement statement;
        try {
            statement = con.prepareStatement(queryInsert);
            statement.setString(1, txtID.getText());
            statement.setString(2, txtKelurahan.getText());
            statement.setString(3, txtIDKecamatan.getText());
            statement.executeUpdate();
            System.out.println("Berhasil Input DB");
        } catch (SQLException e) {
            System.out.println("Gagal Insert DB " + e);
        }
        baca_data();
    }

    public void update_data() {
        PreparedStatement statement;
        try {
            statement = con.prepareStatement(queryUpdate);
            statement.setString(1, txtID.getText());
            statement.setString(2, txtKelurahan.getText());
            statement.setString(3, txtIDKecamatan.getText());
            statement.executeUpdate();
            System.out.println("Berhasil Update Data");
        } catch (SQLException e) {
            System.out.println("Gagal Update Data " + e);
        }
        baca_data();
    }

    public void delete_data() {
        PreparedStatement statement;
        int confirm = JOptionPane.showConfirmDialog(null, "Delete Data ?");
        if (confirm == 0) {
            try {
                statement=con.prepareStatement(queryDelete);
                statement.setString(1, getIdDelete());
                statement.executeUpdate();
                System.out.println("Berhasil Delete Data");
            } catch (SQLException e) {
                System.out.println("Gagal Delete Data " + e);
            }
        }
        baca_data();
    }

    public String getIdDelete() {
        int row = tblKelurahan.getSelectedRow();
        int column = 0;
        String finalValue = (String) tblKelurahan.getValueAt(row, column);
        System.out.println(finalValue);
        return finalValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKelurahan = new javax.swing.JTable();
        btnKeluar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtKelurahan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIDKecamatan = new javax.swing.JTextField();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Kelurahan");

        jLabel1.setBackground(new java.awt.Color(51, 102, 255));
        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Kelurahan");
        jLabel1.setOpaque(true);

        tblKelurahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Kelurahan", "Kelurahan", "ID Kecamatan", "Kecamatan", "ID Kab/Kota", "Kabupaten/Kota"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblKelurahan);

        btnKeluar.setText("Keluar");
        btnKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel4.setText("ID Kelurahan");

        jLabel2.setText("Kelurahan");

        jLabel5.setText("ID Kecamatan");

        cmdTambah.setText("Tambah");
        cmdTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdSimpan.setText("Simpan");
        cmdSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdSimpanMouseClicked(evt);
            }
        });
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdHapus.setText("Hapus");
        cmdHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdEdit.setText("Edit");
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIDKecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtIDKecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtKelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKeluar)
                    .addComponent(cmdTambah)
                    .addComponent(cmdBatal)
                    .addComponent(cmdHapus)
                    .addComponent(cmdSimpan)
                    .addComponent(cmdEdit))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
        aktif(true);
        setTombol(false);
        kosong();
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdSimpanMouseClicked
        // TODO add your handling code here:
        ResultSet RsUser;
        Statement stm;
        String tId = txtID.getText();
        String tKelurahan = txtKelurahan.getText();
        String tIDKecamatan = txtIDKecamatan.getText();
        try {
            stm = con.createStatement();
            if (edit == true) {
                stm.executeUpdate("UPDATE data_kelurahan set id='" + tId + "', name='" + tKelurahan + "',id_kecamatan='" + tIDKecamatan + "'");
            } else {
                stm.executeUpdate("INSERT into data_kabkota VALUES('" + tId + "','" + tKelurahan + "','" + tIDKecamatan + "')");
            }
            tblKelurahan.setModel(new DefaultTableModel(datatable, header));
            baca_data();
            aktif(false);
            setTombol(true);
            System.out.println("Berhasil Add Kab/Kota");
        } catch (SQLException e) {
            // System.out.println("Error : "+e);
            // JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdSimpanMouseClicked

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:
        if (edit == true) {
            update_data();
        } else {
            insert_data();
        }
        aktif(false);
        setTombol(true);
        kosong();
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusActionPerformed
        // TODO add your handling code here:
        delete_data();
    }//GEN-LAST:event_cmdHapusActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        // TODO add your handling code here:
        int row = tblKelurahan.getSelectedRow();        
        setField(row);
        edit = true;
        aktif(true);
        setTombol(false);
        txtID.setEditable(false);
    }//GEN-LAST:event_cmdEditActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormKelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKelurahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKelurahan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdHapus;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKelurahan;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDKecamatan;
    private javax.swing.JTextField txtKelurahan;
    // End of variables declaration//GEN-END:variables
}
