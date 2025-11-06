/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuanduabelas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class TestJPA {
    public static void main(String[] args) {
        System.out.println("=== TEST JPA DENGAN GENERATED ENTITIES ===");
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // 1. Create EntityManagerFactory
            System.out.println("1. Membuat EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("perpustakaan_pu");
            System.out.println("✓ Berhasil membuat EntityManagerFactory");
            
            // 2. Create EntityManager
            System.out.println("2. Membuat EntityManager...");
            em = emf.createEntityManager();
            System.out.println("✓ Berhasil membuat EntityManager");
            
            // 3. Mulai transaction
            System.out.println("3. Memulai transaction...");
            em.getTransaction().begin();
            
            // 4. Test query - ambil semua kategori
            System.out.println("4. Mengambil data kategori...");
            List<Kategori> categories = em.createQuery("SELECT k FROM Kategori k", Kategori.class).getResultList();
            
            System.out.println("✓ Berhasil mengambil " + categories.size() + " kategori");
            System.out.println("Daftar Kategori:");
            for (Kategori cat : categories) {
                System.out.println("  - " + cat.getIdKategori() + ": " + cat.getNamaKategori());
                if (cat.getDeskripsi() != null) {
                    System.out.println("    Deskripsi: " + cat.getDeskripsi());
                }
            }
            
            // 5. Test query - ambil semua buku dengan JOIN FETCH
            System.out.println("\n5. Mengambil data buku dengan JOIN FETCH...");
            List<Books> books = em.createQuery(
                "SELECT b FROM Books b LEFT JOIN FETCH b.idKategori", Books.class) // PERHATIAN: idKategori bukan kategori
                .getResultList();
            System.out.println("✓ Berhasil mengambil " + books.size() + " buku");
            
            System.out.println("Daftar Buku:");
            for (Books book : books) {
                System.out.println("  - " + book.getIdBuku() + ": " + book.getJudul());
                System.out.println("    Penulis: " + book.getPenulis());
                System.out.println("    Penerbit: " + book.getPenerbit());
                System.out.println("    Tahun: " + book.getTahunTerbit());
                
                // PERHATIAN: Gunakan getIdKategori() bukan getKategori()
                if (book.getIdKategori() != null) {
                    System.out.println("    Kategori: " + book.getIdKategori().getNamaKategori());
                } else {
                    System.out.println("    Kategori: Tidak ada kategori");
                }
                System.out.println();
            }
            
            // 6. Commit transaction
            em.getTransaction().commit();
            System.out.println("✓ Transaction committed");
            
            // 7. Test relationship dengan JOIN query
            System.out.println("7. Testing relationship dengan JOIN query...");
            List<Object[]> results = em.createQuery(
                "SELECT b.judul, k.namaKategori, b.penulis FROM Books b JOIN b.idKategori k", Object[].class) // PERHATIAN: idKategori
                .getResultList();
            
            System.out.println("Data Buku dengan Kategori (JOIN):");
            for (Object[] result : results) {
                System.out.println("  - " + result[0] + " | Kategori: " + result[1] + " | Penulis: " + result[2]);
            }
            
            System.out.println("\n=== TEST JPA BERHASIL 100% ===");
            System.out.println("Total Kategori: " + categories.size());
            System.out.println("Total Buku: " + books.size());
            
        } catch (Exception e) {
            // Rollback jika ada error
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("✗ Transaction rolled back");
            }
            
            System.err.println("=== ERROR ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            if (em != null) {
                em.close();
                System.out.println("EntityManager ditutup");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory ditutup");
            }
        }
    }
}
