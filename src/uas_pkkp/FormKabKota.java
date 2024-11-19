package uas_pkkp;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormKabKota extends javax.swing.JFrame {

    //membuat object
    private Connection con;
    private Statement stat;
    private ResultSet res;
    
    private Object[][] datatable = null;
    private String[] header = {
        "ID Kab/Kota", "Kabupaten/Kota", "Kuota Awal", "Kuota Sisa"
    };
    Boolean ada = false;
    Boolean edit = false;
    private boolean True;
   
    final String querySelect = "SELECT * FROM data_kabkota";
    final String queryInsert = "INSERT INTO data_kabkota(id, name, kuota_awal, kuota, id_provinsi) VALUES(?,?,?,?,'001')";
    final String queryUpdate = "UPDATE data_kabkota SET id=?, name=?, kuota_awal=?, kuota=?, id_provinsi='001'  WHERE id=?";
    final String queryDelete = "DELETE FROM data_kabkota WHERE id=?";

    public FormKabKota() {
        initComponents();
        open_db();
        aktif(false);
        setTombol(true);
        baca_data();
        tblKabKota.setDefaultEditor(Object.class, null);
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
            res = stm.executeQuery("select * from data_kabkota");

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
                datatable[x][0] = res.getString("id");
                datatable[x][1] = res.getString("name");
                datatable[x][2] = res.getString("kuota_awal");
                datatable[x][3] = res.getString("kuota");
                x++;
            }
            tblKabKota.setModel(new DefaultTableModel(datatable, header));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setField(int index) {
        txtID.setText(tblKabKota.getValueAt(index, 0).toString());
        txtKabKota.setText(tblKabKota.getValueAt(index, 1).toString());
        txtKuotaAwal.setText(tblKabKota.getValueAt(index, 2).toString());
        txtKuotaSisa.setText(tblKabKota.getValueAt(index, 3).toString());
    }

    private void aktif(boolean x) {
        txtID.setEditable(x);
        txtKabKota.setEditable(x);
        txtKuotaAwal.setEditable(x);
        txtKuotaSisa.setEditable(x);
    }

    private void setTombol(boolean t) {
        cmdTambah.setEnabled(t);
        cmdSimpan.setEnabled(!t);
        cmdHapus.setEnabled(t);
        cmdBatal.setEnabled(!t);
        btnKeluar.setEnabled(t);
    }

    private void kosong() {
        txtID.setText("");
        txtKabKota.setText("");
        txtKuotaAwal.setText("");
        txtKuotaSisa.setText("");
    }

    public void insert_data() {
        PreparedStatement statement;
        try {
            statement = con.prepareStatement(queryInsert);
            statement.setString(1, txtID.getText());
            statement.setString(2, txtKabKota.getText());
            statement.setString(3, txtKuotaAwal.getText());
            statement.setString(4, txtKuotaSisa.getText());
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
            statement.setString(2, txtKabKota.getText());
            statement.setString(3, txtKuotaAwal.getText());
            statement.setString(4, txtKuotaSisa.getText());
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
        int row = tblKabKota.getSelectedRow();
        int column = 0;
        String finalValue = (String) tblKabKota.getValueAt(row, column);
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
        tblKabKota = new javax.swing.JTable();
        btnKeluar = new javax.swing.JButton();
        txtKabKota = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKuotaAwal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtKuotaSisa = new javax.swing.JTextField();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdHapus = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Kabupaten/Kota");

        jLabel1.setBackground(new java.awt.Color(51, 102, 255));
        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Kabupaten/Kota");
        jLabel1.setOpaque(true);

        tblKabKota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Kab/Kota", "Kabupaten/Kota", "Kuota Awal", "Kuota Sisa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblKabKota);

        btnKeluar.setText("Keluar");
        btnKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel2.setText("Kabupaten/Kota");

        jLabel3.setText("Kuota Sisa");

        jLabel4.setText("ID Kab/Kota");

        jLabel5.setText("Kuota Awal");

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

        cmdBatal.setText("Batal");
        cmdBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdHapus.setText("Hapus");
        cmdHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusActionPerformed(evt);
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
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKuotaSisa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKabKota, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKuotaAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdTambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdTambah)
                    .addComponent(cmdSimpan))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKabKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmdHapus)
                    .addComponent(cmdEdit))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKuotaAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnKeluar)
                    .addComponent(cmdBatal))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKuotaSisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(33, Short.MAX_VALUE))
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
        String tKabKota = txtKabKota.getText();
        String tKuotaAwal = txtKuotaAwal.getText();
        String tKuotaSisa = txtKuotaSisa.getText();
        try {
            stm = con.createStatement();
            if (edit == true) {
                stm.executeUpdate("UPDATE data_kabkota set id='" + tId + "',name='" + tKabKota + "',kuota_awal='" + tKuotaAwal + "',kuota='" + tKuotaSisa + "'");
            } else {
                stm.executeUpdate("INSERT into data_kabkota VALUES('" + tId + "','" + tKabKota + "','" + tKuotaAwal + "','" + tKuotaSisa + "')");
            }
            tblKabKota.setModel(new DefaultTableModel(datatable, header));
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

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusActionPerformed
        // TODO add your handling code here:
        delete_data();
    }//GEN-LAST:event_cmdHapusActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        // TODO add your handling code here:
        int row = tblKabKota.getSelectedRow();
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
            java.util.logging.Logger.getLogger(FormKabKota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKabKota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKabKota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKabKota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKabKota().setVisible(true);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKabKota;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtKabKota;
    private javax.swing.JTextField txtKuotaAwal;
    private javax.swing.JTextField txtKuotaSisa;
    // End of variables declaration//GEN-END:variables
}
