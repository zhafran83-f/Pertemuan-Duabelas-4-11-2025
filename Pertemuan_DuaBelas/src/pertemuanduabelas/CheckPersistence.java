/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuanduabelas;

import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class CheckPersistence {
        public static void main(String[] args) {
        System.out.println("=== CEK PERSISTENCE.XML ===");
        
        // Cek apakah persistence.xml ada di classpath
        URL persistenceUrl = CheckPersistence.class.getClassLoader().getResource("META-INF/persistence.xml");
        
        if (persistenceUrl != null) {
            System.out.println("✓ persistence.xml DITEMUKAN di: " + persistenceUrl.getPath());
            
            try {
                // Coba baca file
                InputStream is = CheckPersistence.class.getClassLoader().getResourceAsStream("META-INF/persistence.xml");
                if (is != null) {
                    System.out.println("✓ persistence.xml BISA DIBACA");
                    is.close();
                } else {
                    System.out.println("✗ persistence.xml TIDAK BISA DIBACA");
                }
            } catch (Exception e) {
                System.out.println("✗ Error membaca persistence.xml: " + e.getMessage());
            }
        } else {
            System.out.println("✗ persistence.xml TIDAK DITEMUKAN di classpath");
            System.out.println("Pastikan file ada di: src/META-INF/persistence.xml");
        }
        
        // Cek libraries
        System.out.println("\n=== CEK LIBRARIES ===");
        try {
            Class.forName("org.hibernate.jpa.HibernatePersistenceProvider");
            System.out.println("✓ HibernatePersistenceProvider ditemukan");
        } catch (ClassNotFoundException e) {
            System.out.println("✗ HibernatePersistenceProvider TIDAK ditemukan");
        }
        
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✓ PostgreSQL Driver ditemukan");
        } catch (ClassNotFoundException e) {
            System.out.println("✗ PostgreSQL Driver TIDAK ditemukan");
        }
    }
}
