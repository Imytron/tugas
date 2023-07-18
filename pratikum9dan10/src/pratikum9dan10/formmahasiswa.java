/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratikum9dan10;
import java.sql.ResultSet;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ARYA
 */
public class formmahasiswa extends javax.swing.JFrame {

    /**
     * Creates new form formmahasiswa
     */
    public formmahasiswa() {
        initComponents();
        jenis.removeAllItems();
        jenis.addItem("Laki-laki");
        jenis.addItem("Perempuan");
        fakultas.removeAllItems();
        fakultas.addItem("TEKNOLOGI INFORMASI");
        fakultas.addItem("TEKNOLOGI INDUSTRI");
        prodi.removeAllItems();
        prodi.addItem("SISTEM INFORMASI");
        prodi.addItem("TEKNIK KOMPUTER");
        prodi.addItem("MATEMATIKA");
        prodi.addItem("DESAIN KOMUNIKASI VISUAL");
        prodi.addItem("TEKNIK INDUSTRI");
        prodi.addItem("MANAJEMEN REKAYASA");
        prodi.addItem("PERDANGANAN INTERNASIONAL");
        tampil();
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    public Connection conn;
    public Statement cn;
    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
    
    public void koneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pratikum9dan10","root", "");
            cn = conn.createStatement();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"koneksi gagal..");
            System.out.println(e.getMessage());
        }
    }
    
        public void tampil(){
        DefaultTableModel tabelnyo = new DefaultTableModel();
        tabelnyo.addColumn("NIM");
        tabelnyo.addColumn("NAMA");
        tabelnyo.addColumn("TEMPAT LAHIR");
        tabelnyo.addColumn("TANGGAL LAHIR");
        tabelnyo.addColumn("NO HP");
        tabelnyo.addColumn("ALAMAT");
        tabelnyo.addColumn("JENIS KELAMIN");
        tabelnyo.addColumn("NAMA ORTU");
        tabelnyo.addColumn("FAKULTAS");
        tabelnyo.addColumn("PROGRAM STUDI");
        
        try{
            koneksi();
            String sql = "SELECT * FROM tabelmahasiswa";
            ResultSet rs = cn.executeQuery(sql);
            
            while (rs.next()) {  
                
                tabelnyo.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                });
            }
            jTable2.setModel(tabelnyo);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ada Kesalahan" + e.getMessage());
        }
    }
    
        public void bersih(){
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jDateChooser1.setDate(null);
        jTextField5.setText("");
        jTextField6.setText("");
        jenis.setSelectedItem("");
        jTextField7.setText("");
        fakultas.setSelectedItem("");
        prodi.setSelectedItem("");
    }
        
        public void validasi(){
        try{
            koneksi();
            String sql = "SELECT * FROM tabelmahasiswa WHERE nim = '" + jTextField2.getText() + "'";
            ResultSet rs = cn.executeQuery(sql);
            if(rs.next()){
                jTextField2.setText(rs.getString(1));
                jTextField3.setText(rs.getString(2));
                jTextField4.setText(rs.getString(3));
                jDateChooser1.setDateFormatString(rs.getString(4));
                jTextField5.setText(rs.getString(5));
                jTextField6.setText(rs.getString(6));
                jenis.setSelectedItem(rs.getString(7));
                jTextField7.setText(rs.getString(8));
                fakultas.setSelectedItem(rs.getString(9));
                prodi.setSelectedItem(rs.getString(10));
                
                jButton2.setEnabled(true);
                jButton1.setEnabled(false);
                
                try {
                    int kel = JOptionPane.showConfirmDialog(this, "NIM " + jTextField2.getText() + " Sudah ada, mau diedit atau di hapus?", "INFORMASI", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(kel == JOptionPane.NO_OPTION) {
                        bersih();
                        jButton2.setEnabled(true);
                        jButton1.setEnabled(true);
                        jTextField2.requestFocus();
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                }catch(Exception e) {
                    
                }
            }
        }catch(Exception e){
            
        }
    }
        
        public void cari(){
        String a = new String(jTextField1.getText());
        if(a.equals("")){
            JOptionPane.showConfirmDialog(null, "Field masih kosong..", "INFORMASI", JOptionPane.INFORMATION_MESSAGE);
            jTextField1.requestFocus();
        }else{
            DefaultTableModel tabelnyo = new DefaultTableModel();
            tabelnyo.addColumn("NIM");
            tabelnyo.addColumn("NAMA");
            tabelnyo.addColumn("TEMPAT LAHIR");
            tabelnyo.addColumn("TANGGAL LAHIR");
            tabelnyo.addColumn("NO HP");
            tabelnyo.addColumn("ALAMAT");
            tabelnyo.addColumn("JENIS KELAMIN");
            tabelnyo.addColumn("NAMA ORTU");
            tabelnyo.addColumn("FAKULTAS");
            tabelnyo.addColumn("PROGRAM STUDI");
            
            try{
               koneksi();
               String sql = "SELECT * FROM tabelmahasiswa WHERE nim LIKE '%" + jTextField1.getText() + "%' or nama LIKE '%" + jTextField1.getText() + "%'";
               ResultSet rs = cn.executeQuery(sql);
               while (rs.next()) {                      
                   tabelnyo.addRow(new Object[] {
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                   });
               }
               jTable1.setModel(tabelnyo);
           }catch(Exception e) {
               JOptionPane.showMessageDialog(null, "Data tidakditemukan..","INFORMASI",JOptionPane.INFORMATION_MESSAGE);
           }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jenis = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        fakultas = new javax.swing.JComboBox<>();
        prodi = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("IMPUT DATA MAHASISWA");

        jLabel2.setText("CARI BERDASARKAN NIM/NAMA MAHASISWA");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Nim Mahasiswa");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Nama Mahasiswa");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Tempat L.Mahasiswa");

        jLabel6.setText("Tanggal L. Mahasiswa");

        jLabel7.setText("No HP Mahasiswa");

        jLabel8.setText("Alamat Mahasiswa");

        jLabel9.setText("Jenis Kelamin");

        jLabel10.setText("Nama ORTU");

        jLabel11.setText("Falkultas");

        jLabel12.setText("Profram Studi");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        fakultas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        prodi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Keluar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4)
                                    .addComponent(jTextField5)
                                    .addComponent(jTextField6)
                                    .addComponent(jenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField7)
                                    .addComponent(fakultas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(prodi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(fakultas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(prodi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        validasi();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            koneksi();
            String tgllahir = sd.format(jDateChooser1.getDate()).toString();
            String sql = "INSERT INTO tabelmahasiswa VALUES('"+ jTextField2.getText() + "','" + jTextField3.getText() + "','" + jTextField4.getText() + "','" + tgllahir + "','" + jTextField5.getText() + "','" + jTextField6.getText() + "','" + jenis.getSelectedItem() + "','" + jTextField7.getText() + "','" + fakultas.getSelectedItem() + "','" + prodi.getSelectedItem() + "')";
            cn.executeUpdate(sql);
            conn.close();
            tampil();
            bersih();
            JOptionPane.showMessageDialog(null,"Data berhasil di simpan");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Proses penyimpanan gagal" + e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            koneksi();
            String tgllahir = sd.format(jDateChooser1.getDate()).toString();
            String sql =  "update tabelmahasiswa set nama='"+ jTextField2.getText() +"',tempat_lahir='" + jTextField4.getText() + "', tanggal_lahir ='" + tgllahir + "', no_hp ='" + jTextField5.getText() + "', alamat='" + jTextField6.getText() + "', jenis_kelamin ='" + jenis.getSelectedItem() +"', nama_ortu='" + jTextField7.getText() + "', fakultas = '" + fakultas.getSelectedItem() + "', prodi = '" + prodi.getSelectedItem() +"' where nim='" + jTextField2.getText() + "'";
            cn.executeUpdate(sql);
            cn.close();
            tampil();
            bersih();
            jTextField2.setEnabled(true);
            jButton1.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jTextField2.requestFocus();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
         cari();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
                try{
            getToolkit().beep();
            int keluar = JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Meghapus Ini..?","PERINGATAN",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(keluar == JOptionPane.YES_OPTION){
                try{
                    koneksi();
                    String sql = "DELETE FROM tabelmahasiswa WHERE nim = '" + jTextField2.getText() + "'";
                    cn.executeUpdate(sql);
                    cn.close();
                    tampil();
                    bersih();
                    jTextField2.setEnabled(true);
                    jButton1.setEnabled(true);
                    jButton2.setEnabled(false);
                    jButton3.setEnabled(false);
                    jTextField2.requestFocus();
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Deleting failed..");
                }
            }
        }catch(Exception e){
            
            
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        int tabel = jTable1.getSelectedRow();
        String a = jTable1.getValueAt(tabel, 0).toString() ;
        String b = jTable1.getValueAt(tabel, 1).toString() ;
        String c = jTable1.getValueAt(tabel, 2).toString() ;
        String d = jTable1.getValueAt(tabel, 3).toString() ;
        String e = jTable1.getValueAt(tabel, 4).toString() ;
        String f = jTable1.getValueAt(tabel, 5).toString() ;
        String g = jTable1.getValueAt(tabel, 6).toString() ;
        String h = jTable1.getValueAt(tabel, 7).toString() ;
        String i = jTable1.getValueAt(tabel, 8).toString() ;
        String j = jTable1.getValueAt(tabel, 9).toString() ;
        
        jTextField2.setText(a);
        jTextField3.setText(b);
        jTextField4.setText(c);
        try{
            jDateChooser1.setDate(sd.parse(jTable2.getValueAt(tabel,3).toString()));
        }catch(Exception er){
            System.out.println(er.getMessage());
        }
        jTextField5.setText(e);
        jTextField6.setText(f);
        jenis.setSelectedItem(g);
        jTextField7.setText(h);
        fakultas.setSelectedItem(i);
        prodi.setSelectedItem(j);
    }//GEN-LAST:event_jTable2MouseClicked

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
            java.util.logging.Logger.getLogger(formmahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formmahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formmahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formmahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formmahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> fakultas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JComboBox<String> jenis;
    private javax.swing.JComboBox<String> prodi;
    // End of variables declaration//GEN-END:variables

    private void Cari() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
