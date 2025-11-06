/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuanduabelas;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class Utama extends JFrame {

    private EntityManagerFactory emf;
    private EntityManager em;

    // TabbedPane
    private JTabbedPane tabbedPane;

    // Komponen untuk Tab Kategori (View Only)
    private JPanel panelKategori;
    private JTable tableKategori;
    private JComboBox<String> comboIDKategori;
    private JTextField tfNamaKategori, tfDeskripsi;

    // Komponen untuk Tab Buku (CRUD)
    private JPanel panelBuku;
    private JTable tableBuku;
    private JTextField tfIDBuku, tfJudul, tfPenulis, tfPenerbit, tfTahun;
    private JComboBox<String> comboKategoriBuku;
    private JButton btnInsertBuku, btnUpdateBuku, btnDeleteBuku, btnUploadBuku, btnCetakBuku;

    public Utama() {
        initializeJPA();
        initComponents();
        loadDataKategori();
        loadDataBuku();
    }

    private void initializeJPA() {
        try {
            emf = Persistence.createEntityManagerFactory("perpustakaan_pu");
            em = emf.createEntityManager();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error koneksi database: " + e.getMessage());
        }
    }

    private void initComponents() {
        setTitle("Sistem Manajemen Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Setup Tab Kategori
        setupTabKategori();

        // Setup Tab Buku
        setupTabBuku();

        add(tabbedPane);
    }

    private void setupTabKategori() {
        panelKategori = new JPanel();
        panelKategori.setLayout(null);

        // Table Kategori
        JScrollPane scrollKategori = new JScrollPane();
        scrollKategori.setBounds(20, 20, 600, 200);
        panelKategori.add(scrollKategori);

        tableKategori = new JTable();
        scrollKategori.setViewportView(tableKategori);

        // ComboBox ID Kategori
        JLabel lblIDKategori = new JLabel("ID Kategori:");
        lblIDKategori.setBounds(20, 240, 100, 25);
        panelKategori.add(lblIDKategori);

        comboIDKategori = new JComboBox<>();
        comboIDKategori.setBounds(120, 240, 100, 25);
        comboIDKategori.addActionListener(e -> loadKategoriDetail());
        panelKategori.add(comboIDKategori);

        // Nama Kategori
        JLabel lblNamaKategori = new JLabel("Nama Kategori:");
        lblNamaKategori.setBounds(20, 280, 100, 25);
        panelKategori.add(lblNamaKategori);

        tfNamaKategori = new JTextField();
        tfNamaKategori.setBounds(120, 280, 200, 25);
        tfNamaKategori.setEditable(false);
        panelKategori.add(tfNamaKategori);

        // Deskripsi
        JLabel lblDeskripsi = new JLabel("Deskripsi:");
        lblDeskripsi.setBounds(20, 320, 100, 25);
        panelKategori.add(lblDeskripsi);

        tfDeskripsi = new JTextField();
        tfDeskripsi.setBounds(120, 320, 300, 25);
        tfDeskripsi.setEditable(false);
        panelKategori.add(tfDeskripsi);

        tabbedPane.addTab("Kategori", panelKategori);
    }

    private void setupTabBuku() {
        panelBuku = new JPanel();
        panelBuku.setLayout(null);

        // Table Buku
        JScrollPane scrollBuku = new JScrollPane();
        scrollBuku.setBounds(20, 20, 600, 200);
        panelBuku.add(scrollBuku);

        tableBuku = new JTable();
        tableBuku.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedBookToForm();
                }
            }
        });
        scrollBuku.setViewportView(tableBuku);

        // ID Buku
        JLabel lblIDBuku = new JLabel("ID Buku:");
        lblIDBuku.setBounds(20, 240, 100, 25);
        panelBuku.add(lblIDBuku);

        tfIDBuku = new JTextField();
        tfIDBuku.setBounds(120, 240, 100, 25);
        panelBuku.add(tfIDBuku);

        // Judul
        JLabel lblJudul = new JLabel("Judul:");
        lblJudul.setBounds(20, 280, 100, 25);
        panelBuku.add(lblJudul);

        tfJudul = new JTextField();
        tfJudul.setBounds(120, 280, 200, 25);
        panelBuku.add(tfJudul);

        // Penulis
        JLabel lblPenulis = new JLabel("Penulis:");
        lblPenulis.setBounds(20, 320, 100, 25);
        panelBuku.add(lblPenulis);

        tfPenulis = new JTextField();
        tfPenulis.setBounds(120, 320, 200, 25);
        panelBuku.add(tfPenulis);

        // Penerbit
        JLabel lblPenerbit = new JLabel("Penerbit:");
        lblPenerbit.setBounds(20, 360, 100, 25);
        panelBuku.add(lblPenerbit);

        tfPenerbit = new JTextField();
        tfPenerbit.setBounds(120, 360, 200, 25);
        panelBuku.add(tfPenerbit);

        // Kategori (ComboBox)
        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setBounds(20, 400, 100, 25);
        panelBuku.add(lblKategori);

        comboKategoriBuku = new JComboBox<>();
        comboKategoriBuku.setBounds(120, 400, 150, 25);
        panelBuku.add(comboKategoriBuku);

        // Tahun
        JLabel lblTahun = new JLabel("Tahun:");
        lblTahun.setBounds(20, 440, 100, 25);
        panelBuku.add(lblTahun);

        tfTahun = new JTextField();
        tfTahun.setBounds(120, 440, 100, 25);
        panelBuku.add(tfTahun);

        // Buttons - Insert, Update, Delete, Upload, Cetak
        btnInsertBuku = new JButton("Insert");
        btnInsertBuku.setBounds(350, 240, 100, 30);
        btnInsertBuku.addActionListener(e -> insertBuku());
        panelBuku.add(btnInsertBuku);

        btnUpdateBuku = new JButton("Update");
        btnUpdateBuku.setBounds(350, 280, 100, 30);
        btnUpdateBuku.addActionListener(e -> updateBuku());
        panelBuku.add(btnUpdateBuku);

        btnDeleteBuku = new JButton("Delete");
        btnDeleteBuku.setBounds(350, 320, 100, 30);
        btnDeleteBuku.addActionListener(e -> deleteBuku());
        panelBuku.add(btnDeleteBuku);

        btnUploadBuku = new JButton("UPLOAD");
        btnUploadBuku.setBounds(470, 240, 100, 30);
        btnUploadBuku.addActionListener(e -> uploadCSV());
        panelBuku.add(btnUploadBuku);

        btnCetakBuku = new JButton("CETAK");
        btnCetakBuku.setBounds(470, 280, 100, 30);
        btnCetakBuku.addActionListener(e -> cetakLaporan());
        panelBuku.add(btnCetakBuku);

        tabbedPane.addTab("Buku", panelBuku);
    }

    private void loadDataKategori() {
        try {
            // Load data untuk ComboBox
            List<Kategori> categories = em.createQuery("SELECT k FROM Kategori k ORDER BY k.idKategori", Kategori.class).getResultList();
            comboIDKategori.removeAllItems();
            comboKategoriBuku.removeAllItems();

            for (Kategori kategori : categories) {
                comboIDKategori.addItem(kategori.getIdKategori());
                comboKategoriBuku.addItem(kategori.getIdKategori() + " - " + kategori.getNamaKategori());
            }

            // Load data untuk Table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Kategori");
            model.addColumn("Nama Kategori");
            model.addColumn("Deskripsi");

            for (Kategori kategori : categories) {
                model.addRow(new Object[]{
                    kategori.getIdKategori(),
                    kategori.getNamaKategori(),
                    kategori.getDeskripsi()
                });
            }

            tableKategori.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading kategori: " + e.getMessage());
        }
    }

    private void loadKategoriDetail() {
        String selectedID = (String) comboIDKategori.getSelectedItem();
        if (selectedID != null) {
            try {
                Kategori kategori = em.find(Kategori.class, selectedID);
                if (kategori != null) {
                    tfNamaKategori.setText(kategori.getNamaKategori());
                    tfDeskripsi.setText(kategori.getDeskripsi());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading kategori detail: " + e.getMessage());
            }
        }
    }

    private void loadDataBuku() {
        try {
            List<Books> books = em.createQuery(
                    "SELECT b FROM Books b LEFT JOIN FETCH b.idKategori", Books.class)
                    .getResultList();

            // Custom sorting untuk ID Buku (B1, B2, B3, ..., B9, B10, B11)
            books.sort((b1, b2) -> {
                String id1 = b1.getIdBuku();
                String id2 = b2.getIdBuku();

                // Extract angka dari ID (B1 -> 1, B10 -> 10)
                int num1 = Integer.parseInt(id1.substring(1));
                int num2 = Integer.parseInt(id2.substring(1));

                return Integer.compare(num1, num2);
            });

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Buku");
            model.addColumn("Judul");
            model.addColumn("Penulis");
            model.addColumn("Penerbit");
            model.addColumn("Kategori");
            model.addColumn("Tahun");

            for (Books book : books) {
                String kategori = (book.getIdKategori() != null) ? book.getIdKategori().getNamaKategori() : "No Category";
                model.addRow(new Object[]{
                    book.getIdBuku(),
                    book.getJudul(),
                    book.getPenulis(),
                    book.getPenerbit(),
                    kategori,
                    book.getTahunTerbit()
                });
            }

            tableBuku.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading buku: " + e.getMessage());
        }
    }

    private void loadSelectedBookToForm() {
        int selectedRow = tableBuku.getSelectedRow();
        if (selectedRow >= 0) {
            String idBuku = (String) tableBuku.getValueAt(selectedRow, 0);
            try {
                Books book = em.find(Books.class, idBuku);
                if (book != null) {
                    tfIDBuku.setText(book.getIdBuku());
                    tfJudul.setText(book.getJudul());
                    tfPenulis.setText(book.getPenulis());
                    tfPenerbit.setText(book.getPenerbit());
                    tfTahun.setText(book.getTahunTerbit() != null ? book.getTahunTerbit().toString() : "");

                    // Set ComboBox kategori
                    if (book.getIdKategori() != null) {
                        String kategoriDisplay = book.getIdKategori().getIdKategori() + " - " + book.getIdKategori().getNamaKategori();
                        comboKategoriBuku.setSelectedItem(kategoriDisplay);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading book details: " + e.getMessage());
            }
        }
    }

    private void insertBuku() {
        try {
            String idBuku = tfIDBuku.getText().trim();
            String judul = tfJudul.getText().trim();
            String penulis = tfPenulis.getText().trim();
            String penerbit = tfPenerbit.getText().trim();
            String tahunText = tfTahun.getText().trim();

            if (idBuku.isEmpty() || judul.isEmpty() || penulis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Buku, Judul, dan Penulis harus diisi!");
                return;
            }

            // Parse kategori dari ComboBox
            String selectedKategori = (String) comboKategoriBuku.getSelectedItem();
            String idKategori = selectedKategori != null ? selectedKategori.split(" - ")[0] : null;
            Kategori kategori = idKategori != null ? em.find(Kategori.class, idKategori) : null;

            // Parse tahun
            Integer tahun = null;
            if (!tahunText.isEmpty()) {
                try {
                    tahun = Integer.parseInt(tahunText);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Tahun harus angka!");
                    return;
                }
            }

            em.getTransaction().begin();

            Books newBook = new Books();
            newBook.setIdBuku(idBuku);
            newBook.setJudul(judul);
            newBook.setPenulis(penulis);
            newBook.setPenerbit(penerbit);
            newBook.setTahunTerbit(tahun);
            newBook.setIdKategori(kategori);

            em.persist(newBook);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!");
            clearBukuForm();
            loadDataBuku();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Error inserting buku: " + e.getMessage());
        }
    }

    private void updateBuku() {
        int selectedRow = tableBuku.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan diupdate dari tabel!");
            return;
        }

        try {
            String idBuku = tfIDBuku.getText().trim();
            String judul = tfJudul.getText().trim();
            String penulis = tfPenulis.getText().trim();
            String penerbit = tfPenerbit.getText().trim();
            String tahunText = tfTahun.getText().trim();

            if (idBuku.isEmpty() || judul.isEmpty() || penulis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Buku, Judul, dan Penulis harus diisi!");
                return;
            }

            // Parse kategori dari ComboBox
            String selectedKategori = (String) comboKategoriBuku.getSelectedItem();
            String idKategori = selectedKategori != null ? selectedKategori.split(" - ")[0] : null;
            Kategori kategori = idKategori != null ? em.find(Kategori.class, idKategori) : null;

            // Parse tahun
            Integer tahun = null;
            if (!tahunText.isEmpty()) {
                try {
                    tahun = Integer.parseInt(tahunText);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Tahun harus angka!");
                    return;
                }
            }

            em.getTransaction().begin();

            Books book = em.find(Books.class, idBuku);
            if (book != null) {
                book.setJudul(judul);
                book.setPenulis(penulis);
                book.setPenerbit(penerbit);
                book.setTahunTerbit(tahun);
                book.setIdKategori(kategori);

                em.merge(book);
                em.getTransaction().commit();

                JOptionPane.showMessageDialog(this, "Buku berhasil diupdate!");
                clearBukuForm();
                loadDataBuku();
            } else {
                em.getTransaction().rollback();
                JOptionPane.showMessageDialog(this, "Buku tidak ditemukan!");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Error updating buku: " + e.getMessage());
        }
    }

    private void deleteBuku() {
        int selectedRow = tableBuku.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dihapus dari tabel!");
            return;
        }

        String idBuku = (String) tableBuku.getValueAt(selectedRow, 0);
        String judul = (String) tableBuku.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus buku:\n" + judul + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                em.getTransaction().begin();

                Books book = em.find(Books.class, idBuku);
                if (book != null) {
                    em.remove(book);
                    em.getTransaction().commit();

                    JOptionPane.showMessageDialog(this, "Buku berhasil dihapus!");
                    clearBukuForm();
                    loadDataBuku();
                } else {
                    em.getTransaction().rollback();
                    JOptionPane.showMessageDialog(this, "Buku tidak ditemukan!");
                }

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                JOptionPane.showMessageDialog(this, "Error deleting buku: " + e.getMessage());
            }
        }
    }

    private void uploadCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                int successCount = 0;
                int errorCount = 0;
                int lineNumber = 0;

                // Skip header
                String header = reader.readLine();
                System.out.println("Header: " + header);

                em.getTransaction().begin();

                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    try {
                        System.out.println("\n=== Processing Line " + lineNumber + " ===");
                        System.out.println("Raw line: " + line);

                        // Flexible separator
                        String[] data;
                        if (line.contains(";")) {
                            data = line.split(";");
                        } else {
                            data = line.split(",");
                        }

                        System.out.println("Data length: " + data.length);
                        for (int i = 0; i < data.length; i++) {
                            System.out.println("  Data[" + i + "]: '" + data[i] + "'");
                        }

                        if (data.length >= 5) {
                            String idBuku = data[0].trim();
                            String judul = data[1].trim();
                            String penulis = data[2].trim();
                            String penerbit = data[3].trim();
                            String idKategori = data[4].trim();
                            String tahunText = data.length > 5 ? data[5].trim() : "";

                            System.out.println("Parsed - ID: " + idBuku + ", Judul: " + judul + ", Kategori: " + idKategori);

                            // Cek apakah buku sudah ada
                            Books existingBook = em.find(Books.class, idBuku);
                            if (existingBook != null) {
                                System.out.println("❌ SKIP - ID sudah ada: " + idBuku);
                                errorCount++;
                                continue;
                            }

                            // Parse kategori
                            Kategori kategori = em.find(Kategori.class, idKategori);
                            if (kategori == null) {
                                System.out.println("❌ SKIP - Kategori tidak ditemukan: " + idKategori);
                                errorCount++;
                                continue;
                            }

                            // Parse tahun
                            Integer tahun = null;
                            if (!tahunText.isEmpty()) {
                                try {
                                    tahun = Integer.parseInt(tahunText);
                                    System.out.println("Tahun: " + tahun);
                                } catch (NumberFormatException e) {
                                    System.out.println("⚠️ Tahun invalid: " + tahunText + " - menggunakan null");
                                }
                            }

                            Books newBook = new Books();
                            newBook.setIdBuku(idBuku);
                            newBook.setJudul(judul);
                            newBook.setPenulis(penulis);
                            newBook.setPenerbit(penerbit);
                            newBook.setTahunTerbit(tahun);
                            newBook.setIdKategori(kategori);

                            em.persist(newBook);
                            successCount++;
                            System.out.println("✅ SUCCESS: " + judul);

                        } else {
                            System.out.println("❌ SKIP - Data tidak lengkap, hanya " + data.length + " kolom");
                            errorCount++;
                        }
                    } catch (Exception e) {
                        errorCount++;
                        System.out.println("❌ ERROR parsing line " + lineNumber + ": " + line);
                        System.out.println("   Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                em.getTransaction().commit();
                reader.close();

                System.out.println("\n=== FINAL RESULT ===");
                System.out.println("Berhasil: " + successCount + ", Gagal: " + errorCount);

                JOptionPane.showMessageDialog(this,
                        "Upload CSV selesai!\nBerhasil: " + successCount + "\nGagal: " + errorCount);
                loadDataBuku();

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                JOptionPane.showMessageDialog(this, "Error uploading CSV: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void cetakLaporan() {
        try {
            // Simple report fallback
            StringBuilder report = new StringBuilder();
            report.append("=== LAPORAN BUKU PERPUSTAKAAN ===\n");
            report.append("Tanggal: ").append(new java.util.Date()).append("\n");
            report.append("Jumlah Buku: ").append(tableBuku.getRowCount()).append("\n\n");

            for (int i = 0; i < tableBuku.getRowCount(); i++) {
                report.append(String.format("%2d. %s - %s (%s)\n",
                        i + 1,
                        tableBuku.getValueAt(i, 0), // ID Buku
                        tableBuku.getValueAt(i, 1), // Judul
                        tableBuku.getValueAt(i, 2) // Penulis
                ));
            }

            // Tampilkan di text area
            JTextArea textArea = new JTextArea(report.toString(), 20, 50);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(this, scrollPane,
                    "Laporan Buku - Preview", JOptionPane.INFORMATION_MESSAGE);

            // Optional: Save to file
            int save = JOptionPane.showConfirmDialog(this,
                    "Simpan laporan ke file?", "Simpan Laporan", JOptionPane.YES_NO_OPTION);

            if (save == JOptionPane.YES_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("laporan_buku.txt"));
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile());
                    writer.write(report.toString());
                    writer.close();
                    JOptionPane.showMessageDialog(this, "Laporan disimpan!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private java.sql.Connection getJdbcConnection() {
        try {
            // Coba ambil connection dari EntityManager (Hibernate)
            return em.unwrap(java.sql.Connection.class);

        } catch (Exception e) {
            try {
                // Fallback: buat koneksi manual
                String url = "jdbc:postgresql://localhost:5432/PBO_Pertemuan_Dua_Belas";
                String user = "postgres";
                String password = "ZayZiya03";

                Class.forName("org.postgresql.Driver");
                return java.sql.DriverManager.getConnection(url, user, password);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error koneksi database: " + ex.getMessage());
                return null;
            }
        }
    }

    private void clearBukuForm() {
        tfIDBuku.setText("");
        tfJudul.setText("");
        tfPenulis.setText("");
        tfPenerbit.setText("");
        tfTahun.setText("");
        if (comboKategoriBuku.getItemCount() > 0) {
            comboKategoriBuku.setSelectedIndex(0);
        }
        tableBuku.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Utama().setVisible(true);
        });
    }
}
