package uas_pkkp;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormPeserta extends javax.swing.JFrame {

    /**
     * @return the idPeserta
     */
    public String getIdPeserta() {
        return txtIdPeserta.getText();
    }

    /**
     * @param idPeserta the idPeserta to set
     */
    public void setIdPeserta(String idPeserta) {
        this.txtIdPeserta.setText(idPeserta);
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return txtNm_Peserta.getText();
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.txtNm_Peserta.setText(nama);
    }

    /**
     * @return the kelamin
     */
    public String getKelamin() {
        return cmbJns_Kel.getSelectedItem().toString();
    }

    /**
     * @param kelamin the kelamin to set
     */
    public void setKelamin(String kelamin) {
        this.cmbJns_Kel.setSelectedItem(kelamin);
    }

    /**
     * @return the usia
     */
    public String getUsia() {
        return txtUsia.getText();
    }

    /**
     * @param usia the usia to set
     */
    public void setUsia(String usia) {
        this.txtUsia.setText(usia);
    }

    /**
     * @return the alamat
     */
    public String getAlamat() {
        return txtAlamat.getText();
    }

    /**
     * @param alamat the alamat to set
     */
    public void setAlamat(String alamat) {
        this.txtAlamat.setText(alamat);
    }

    /**
     * @return the provinsi
     */
    public String getProvinsi() {
        return jComboBoxProvinsi.getSelectedItem().toString();
    }

    /**
     * @param provinsi the provinsi to set
     */
    public void setProvinsi(String provinsi) {
        this.jComboBoxProvinsi.setSelectedItem(provinsi);
    }

    /**
     * @return the kabkota
     */
    public String getKabkota() {
        return jComboBoxKabKota.getSelectedItem().toString();
    }

    /**
     * @param kabkota the kabkota to set
     */
    public void setKabkota(String kabkota) {
        this.jComboBoxKabKota.setSelectedItem(kabkota);
    }

    /**
     * @return the kecamatan
     */
    public String getKecamatan() {
        return jComboBoxKec.getSelectedItem().toString();
    }

    /**
     * @param kecamatan the kecamatan to set
     */
    public void setKecamatan(String kecamatan) {
        this.jComboBoxKec.setSelectedItem(kecamatan);
    }
    
    /**
     * @return the kelurahan
     */
    public String getKelurahan() {
        return jComboBoxKel.getSelectedItem().toString();
    }

    /**
     * @param kelurahan the kelurahan to set
     */
    public void setKelurahan(String kelurahan) {
        this.jComboBoxKel.setSelectedItem(kelurahan);
    }

    /**
     * @return the suratDokter
     */
    public String getSuratDokter() {
        return cmbSrt_Dok.getSelectedItem().toString();
    }

    /**
     * @param suratDokter the suratDokter to set
     */
    public void setSuratDokter(String suratDokter) {
        this.cmbSrt_Dok.setSelectedItem(suratDokter);
    }

    /**
     * @return the skck
     */
    public String getSkck() {
        return cmbSkck.getSelectedItem().toString();
    }

    /**
     * @param skck the skck to set
     */
    public void setSkck(String skck) {
        this.cmbSkck.setSelectedItem(skck);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return cmbStatus.getSelectedItem().toString();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.cmbStatus.setSelectedItem(status);
    }

    /**
     * @return the ipk
     */
    public String getIpk() {
        return txtIpk.getText();
    }

    /**
     * @param ipk the ipk to set
     */
    public void setIpk(String ipk) {
        this.txtIpk.setText(ipk);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return txtEmail.getText();
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.txtEmail.setText(email);
    }

    /**
     * @return the telp
     */
    public String getTelp() {
        return txtNo_Wa.getText();
    }

    /**
     * @param telp the telp to set
     */
    public void setTelp(String telp) {
        this.txtNo_Wa.setText(telp);
    }

    private Connection con;
    private Statement stat;
    private ResultSet res;
    final String queryInsert = "INSERT INTO data_peserta (peserta_id, peserta_nama, peserta_kelamin, peserta_usia, peserta_alamat, peserta_provinsi, peserta_kabkota, peserta_kecamatan, peserta_kelurahan, peserta_surat_dokter, peserta_skck, peserta_status, peserta_ipk, peserta_email, peserta_telp) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String querySelect = "SELECT * FROM data_peserta";
    final String queryUpdate = "update data_peserta set peserta_nama=?, peserta_kelamin=?, peserta_usia=?, peserta_alamat=?, peserta_provinsi=?, peserta_kabkota=?, peserta_kecamatan=?, peserta_kelurahan=?, peserta_surat_dokter=?, peserta_skck=?, peserta_status=?, peserta_ipk=?, peserta_email=?, peserta_telp=? where peserta_id=?;";
    final String queryDelete = "DELETE FROM data_peserta where peserta_id=?";
    final String queryInsertKab = "INSERT INTO kabupaten (kabupaten_nama) VALUES (?)";
    final String queryUpdateKuota = "UPDATE data_kabkota SET kuota = kuota - 1 WHERE name = ?";
    static boolean isUpdate = false;

    public FormPeserta() {
        initComponents();
        setExtendedState(FormPeserta.MAXIMIZED_BOTH);
        open_db();
        selectDB();
        setButtonEdit(false);
        setTextField(false);
        retrieveProvinsi();
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

    //method select database
    public void selectDB() {
        DefaultTableModel dtb = new DefaultTableModel();
        dtb.addColumn("Kode Peserta");
        dtb.addColumn("Nama Lengkap");
        dtb.addColumn("Jenis Kelamin");
        dtb.addColumn("Usia");
        dtb.addColumn("Alamat");
        dtb.addColumn("Provinsi");
        dtb.addColumn("Kabupaten/Kota");
        dtb.addColumn("Kecamatan");
        dtb.addColumn("Kelurahan");
        dtb.addColumn("Surat Dokter");
        dtb.addColumn("SKCK");
        dtb.addColumn("Status");
        dtb.addColumn("IPK");
        dtb.addColumn("Email");
        dtb.addColumn("No Telepon");
        tblPeserta.setModel(dtb);
        System.out.println("Sebelum coba");
        try {
            stat = con.createStatement();
            res = stat.executeQuery(querySelect);
            while (res.next()) {
                dtb.addRow(new Object[]{
                    res.getString("peserta_id"),
                    res.getString("peserta_nama"),
                    res.getString("peserta_kelamin"),
                    res.getInt("peserta_usia"),
                    res.getString("peserta_alamat"),
                    res.getString("peserta_provinsi"),
                    res.getString("peserta_kabkota"),
                    res.getString("peserta_kecamatan"),
                    res.getString("peserta_kelurahan"),
                    res.getString("peserta_surat_dokter"),
                    res.getString("peserta_skck"),
                    res.getString("peserta_status"),
                    res.getString("peserta_ipk"),
                    res.getString("peserta_email"),
                    res.getString("peserta_telp"),
                    //res.getInt("peserta_telp")
                });
            }
            System.out.println("Sukses Load Table");
        } catch (SQLException e) {
            System.out.println("Gagal Load Table " + e);
        }
    }

    //method insert database
    public void insertDB() {
        PreparedStatement statement;
        PreparedStatement statement2;
        try {
            statement = con.prepareStatement(queryInsert);
            statement.setString(1, getIdPeserta());
            statement.setString(2, getNama());
            statement.setString(3, getKelamin());
            statement.setInt(4, Integer.parseInt(getUsia()));
            statement.setString(5, getAlamat());
            statement.setString(6, getProvinsi());
            statement.setString(7, getKabkota());
            statement.setString(8, getKecamatan());
            statement.setString(9, getKelurahan());
            statement.setString(10, getSuratDokter());
            statement.setString(11, getSkck());
            statement.setString(12, getStatus());
            statement.setString(13, getIpk());
            statement.setString(14, getEmail());
            statement.setString(15, getTelp());
            statement.executeUpdate();
            System.out.println("Berhasil Input DB");
            
            statement2 = con.prepareStatement(queryUpdateKuota);
            statement2.setString(1, getKabkota());
            statement2.executeUpdate();
            System.out.println("Berhasil Update DB");
        } catch (SQLException e) {
            System.out.println("Gagal Insert DB " + e);
        }
        insertDBKab();
    }

    public void insertDBKab() {
        PreparedStatement statement;
        try {
            statement = con.prepareStatement(queryInsertKab);
            statement.setString(1, getKabkota());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal input kab " + e);
        }
    }

    public void updateDB() {
        try {
            PreparedStatement statement;
            statement = con.prepareStatement(queryUpdate);
            statement.setString(1, getNama());
            statement.setString(2, getKelamin());
            statement.setInt(3, Integer.parseInt(getUsia()));
            statement.setString(4, getAlamat());
            statement.setString(5, getProvinsi());
            statement.setString(6, getKabkota());
            statement.setString(7, getKecamatan());
            statement.setString(8, getKelurahan());
            statement.setString(9, getSuratDokter());
            statement.setString(10, getSkck());
            statement.setString(11, getStatus());
            statement.setString(12, getIpk());
            statement.setString(13, getEmail());
            statement.setString(14, getTelp());
            statement.setString(15, getIdPeserta());
            statement.executeUpdate();
            System.out.println("Berhasil update");
        } catch (SQLException e) {
            System.out.println("Gagal update " + e);
        }
    }

    public void deleteDB() {
        PreparedStatement statement = null;
        int pilih = JOptionPane.showConfirmDialog(null, "Confirm Delete?");
        if (pilih == 0) {
            try {
                statement = con.prepareStatement(queryDelete);
                statement.setString(1, getIdDelete());
                statement.executeUpdate();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal hapus");
            }
        }
    }

    public String getIdDelete() {
        int row = tblPeserta.getSelectedRow();
        int column = 0;
        String finalValue = (String) tblPeserta.getValueAt(row, column);
        System.out.println(finalValue);
        return finalValue;
    }

    public void fillForm(int index) {
        txtIdPeserta.setText(tblPeserta.getValueAt(index, 0).toString());
        txtNm_Peserta.setText(tblPeserta.getValueAt(index, 1).toString());
        cmbJns_Kel.setSelectedItem(tblPeserta.getValueAt(index, 2).toString());
        txtUsia.setText(tblPeserta.getValueAt(index, 3).toString());
        txtAlamat.setText(tblPeserta.getValueAt(index, 4).toString());
        jComboBoxProvinsi.setSelectedItem(tblPeserta.getValueAt(index, 5).toString());
        jComboBoxKabKota.setSelectedItem(tblPeserta.getValueAt(index, 6).toString());
        jComboBoxKec.setSelectedItem(tblPeserta.getValueAt(index, 7).toString());
        jComboBoxKel.setSelectedItem(tblPeserta.getValueAt(index, 8).toString());
        cmbSrt_Dok.setSelectedItem(tblPeserta.getValueAt(index, 9).toString());
        cmbSkck.setSelectedItem(tblPeserta.getValueAt(index, 10).toString());
        cmbStatus.setSelectedItem(tblPeserta.getValueAt(index, 11).toString());
        txtIpk.setText(tblPeserta.getValueAt(index, 12).toString());
        txtEmail.setText(tblPeserta.getValueAt(index, 13).toString());
        txtNo_Wa.setText(tblPeserta.getValueAt(index, 14).toString());
    }

    public void clearForm() {
        txtIdPeserta.setText("");
        txtNm_Peserta.setText("");
        cmbJns_Kel.setSelectedItem(cmbJns_Kel.getItemAt(0));
        txtUsia.setText("");
        txtAlamat.setText("");
        jComboBoxProvinsi.setSelectedItem(jComboBoxProvinsi.getItemAt(0));
        jComboBoxKabKota.setSelectedItem(jComboBoxKabKota.getItemAt(0));
        jComboBoxKec.setSelectedItem(jComboBoxKec.getItemAt(0));
        jComboBoxKel.setSelectedItem(jComboBoxKel.getItemAt(0));
        cmbSrt_Dok.setSelectedItem(cmbSrt_Dok.getItemAt(0));
        cmbSkck.setSelectedItem(cmbSkck.getItemAt(0));
        cmbStatus.setSelectedItem(cmbStatus.getItemAt(0));
        txtIpk.setText("");
        txtEmail.setText("");
        txtNo_Wa.setText("");
    }

    public void setButtonEdit(boolean value) {
        btnSimpan.setEnabled(value);
        btnBatal.setEnabled(value);
    }

    public void setButtonTambah(boolean value) {
        btnEdit.setEnabled(value);
        btnHapus.setEnabled(value);
        btnTambah.setEnabled(value);
        setButtonEdit(!value);
    }

    public void setTextField(boolean value) {
        txtIdPeserta.setEnabled(value);
        txtNm_Peserta.setEnabled(value);
        cmbJns_Kel.setEnabled(value);
        txtUsia.setEnabled(value);
        txtAlamat.setEnabled(value);
        jComboBoxProvinsi.setEnabled(value);
        jComboBoxKabKota.setEnabled(value);
        jComboBoxKec.setEnabled(value);
        jComboBoxKel.setEnabled(value);
        cmbSrt_Dok.setEnabled(value);
        cmbSkck.setEnabled(value);
        cmbStatus.setEnabled(value);
        txtIpk.setEnabled(value);
        txtEmail.setEnabled(value);
        txtNo_Wa.setEnabled(value);
    }

    public void retrieveProvinsi() {
        ResultSet res;
        Statement stat;
        try {
            stat = con.createStatement();
            res = stat.executeQuery("SELECT * FROM data_provinsi");
            while (res.next()) {
                jComboBoxProvinsi.addItem(res.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("gagal load provinsi " + e);
        }
    }

    int getIdProvinsi() {
        ResultSet res;
        Statement stat;
        PreparedStatement ps = null;
        String namaProvinsi = (jComboBoxProvinsi.getSelectedItem().toString());
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT * FROM data_provinsi WHERE name LIKE ?");
            ps.setString(1, "%" + namaProvinsi + "%");
            res = ps.executeQuery();

            while (res.next()) {
                id = res.getInt("id");
            }
            System.out.println("berhasil load id provinsi");
        } catch (SQLException e) {
            System.out.println("gagal load id provinsi " + e);
        }
        return id;
    }

    public void retrieveKabKota(int idProvinsi) {
        ResultSet res;
        PreparedStatement ps;
        jComboBoxKabKota.removeAllItems();
        try {
            ps = con.prepareStatement("SELECT name FROM data_kabkota WHERE id_provinsi=?");
            ps.setInt(1, idProvinsi);
            res = ps.executeQuery();
            while (res.next()) {
                jComboBoxKabKota.addItem(res.getString("name"));
            }
        } catch (SQLException e) {
            //System.out.println("gagal load data kab/kota " + e);
        }
    }

    int getIdKabKota() {
        ResultSet res;
        PreparedStatement ps = null;
        int id = 0;
        System.out.println("sampai sini");
        try {
            String namaKabKota = (jComboBoxKabKota.getSelectedItem().toString());
            ps = con.prepareStatement("SELECT * FROM data_kabkota WHERE name LIKE ?");
            ps.setString(1, "%" + namaKabKota + "%");
            res = ps.executeQuery();

            while (res.next()) {
                id = res.getInt("id");
            }
            System.out.println("behasil load id kab/kota");
        } catch (SQLException | NullPointerException e) {
            //System.out.println("gagal load id kab/kota " + e);
        }
        return id;
    }

    public void retrieveKecamatan(int idKabKota) {
        ResultSet res;
        Statement stat;
        PreparedStatement ps;
        jComboBoxKec.removeAllItems();
        try {
            ps = con.prepareStatement("SELECT name FROM data_kecamatan WHERE id_kabkota=?");
            ps.setInt(1, idKabKota);
            res = ps.executeQuery();
            while (res.next()) {
                jComboBoxKec.addItem(res.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("gagal load data kecamatan " + e);
        }
    }

    int getIdKecamatan() {
        ResultSet res;
        PreparedStatement ps = null;
        int id = 0;
        System.out.println("sampai sini");
        try {
            String namaKecamatan = (jComboBoxKec.getSelectedItem().toString());
            ps = con.prepareStatement("SELECT * FROM data_kecamatan WHERE name LIKE ?");
            ps.setString(1, "%" + namaKecamatan + "%");
            res = ps.executeQuery();

            while (res.next()) {
                id = res.getInt("id");
            }
            System.out.println("behasil load id kecamatan");
        } catch (SQLException | NullPointerException e) {
//            System.out.println("gagal load id kecamatan " + e);
        }
        return id;
    }

    public void retrieveKelurahan(int idKecamatan) {
        ResultSet res;
        Statement stat;
        PreparedStatement ps;
        jComboBoxKel.removeAllItems();
        try {
            ps = con.prepareStatement("SELECT name FROM data_kelurahan WHERE id_kecamatan=?");
            ps.setInt(1, idKecamatan);
            res = ps.executeQuery();
            while (res.next()) {
                jComboBoxKel.addItem(res.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("gagal load data kelurahan " + e);
        }
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
        jLabel2 = new javax.swing.JLabel();
        txtIdPeserta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNm_Peserta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbJns_Kel = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtUsia = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbSkck = new javax.swing.JComboBox<>();
        cmbSrt_Dok = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtIpk = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNo_Wa = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeserta = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxProvinsi = new javax.swing.JComboBox<>();
        jComboBoxKabKota = new javax.swing.JComboBox<>();
        jComboBoxKec = new javax.swing.JComboBox<>();
        jComboBoxKel = new javax.swing.JComboBox<>();
        btnKeluar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Peserta");

        jLabel1.setBackground(new java.awt.Color(51, 102, 255));
        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Input Data Peserta");
        jLabel1.setOpaque(true);

        jLabel2.setText("Kode Peserta");

        jLabel3.setText("Nama Lengkap");

        jLabel4.setText("Jenis Kelamin");

        cmbJns_Kel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Laki-Laki", "Perempuan" }));
        cmbJns_Kel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel5.setText("Usia");

        jLabel7.setText("Kelurahan");

        jLabel8.setText("Kecamatan");

        jLabel9.setText("Kabupaten/Kota");

        jLabel11.setText("Surat Dokter");

        jLabel12.setText("SKCK");

        jLabel13.setText("Status");

        cmbSkck.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Terlampir", "Tidak Terlampir" }));
        cmbSkck.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cmbSrt_Dok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Terlampir", "Tidak Terlampir" }));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Menikah", "Belum Menikah" }));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel14.setText("IPK");

        jLabel15.setText("Email");

        jLabel16.setText("No Telepon");

        btnTambah.setText("Tambah");
        btnTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        tblPeserta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblPeserta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Peserta", "Nama Lengkap", "Jenis Kelamin", "Usia", "Alamat", "Provinsi", "Kabupaten/Kota", "Kecamatan", "Kelurahan", "Surat Dokter", "SKCK", "Status", "IPK", "Email", "No Telepon"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPeserta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesertaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeserta);

        jLabel10.setText("Provinsi");

        jComboBoxProvinsi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        jComboBoxProvinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProvinsiActionPerformed(evt);
            }
        });

        jComboBoxKabKota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        jComboBoxKabKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKabKotaActionPerformed(evt);
            }
        });

        jComboBoxKec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        jComboBoxKec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKecActionPerformed(evt);
            }
        });

        jComboBoxKel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        jComboBoxKel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKelActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel6.setText("Alamat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdPeserta, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbJns_Kel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNm_Peserta, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUsia, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(190, 190, 190)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel11))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNo_Wa, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbSkck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbSrt_Dok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxKabKota, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboBoxKel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(185, 185, 185)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                            .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jComboBoxKec, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdPeserta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNm_Peserta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbJns_Kel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSrt_Dok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSkck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtNo_Wa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxKabKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxKec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit)
                    .addComponent(btnSimpan))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxKel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal)
                    .addComponent(btnKeluar))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        setButtonTambah(false);
        setTextField(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if (isUpdate == false) {
            insertDB();
        } else {
            updateDB();
        }
        selectDB();
        clearForm();
        setButtonTambah(true);
        setTextField(false);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = tblPeserta.getSelectedRow();
        fillForm(row);
        setButtonTambah(false);
        setTextField(true);
        txtIdPeserta.setEnabled(false);
        isUpdate = true;
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblPesertaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesertaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPesertaMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        deleteDB();
        selectDB();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        setButtonTambah(true);
        clearForm();
        setTextField(false);
        isUpdate = false;
    }//GEN-LAST:event_btnBatalActionPerformed

    private void jComboBoxProvinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProvinsiActionPerformed
        // TODO add your handling code here:
        retrieveKabKota(getIdProvinsi());
    }//GEN-LAST:event_jComboBoxProvinsiActionPerformed

    private void jComboBoxKabKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKabKotaActionPerformed
        // TODO add your handling code here:
        retrieveKecamatan(getIdKabKota());
    }//GEN-LAST:event_jComboBoxKabKotaActionPerformed

    private void jComboBoxKecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKecActionPerformed
        // TODO add your handling code here:
        retrieveKelurahan(getIdKecamatan());
    }//GEN-LAST:event_jComboBoxKecActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void jComboBoxKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxKelActionPerformed

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
            java.util.logging.Logger.getLogger(FormPeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPeserta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbJns_Kel;
    private javax.swing.JComboBox<String> cmbSkck;
    private javax.swing.JComboBox<String> cmbSrt_Dok;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> jComboBoxKabKota;
    private javax.swing.JComboBox<String> jComboBoxKec;
    private javax.swing.JComboBox<String> jComboBoxKel;
    private javax.swing.JComboBox<String> jComboBoxProvinsi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPeserta;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdPeserta;
    private javax.swing.JTextField txtIpk;
    private javax.swing.JTextField txtNm_Peserta;
    private javax.swing.JTextField txtNo_Wa;
    private javax.swing.JTextField txtUsia;
    // End of variables declaration//GEN-END:variables
}
