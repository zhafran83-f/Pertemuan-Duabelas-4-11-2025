/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuanduabelas;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
@Entity
@Table(name = "books")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
    @NamedQuery(name = "Books.findByIdBuku", query = "SELECT b FROM Books b WHERE b.idBuku = :idBuku"),
    @NamedQuery(name = "Books.findByJudul", query = "SELECT b FROM Books b WHERE b.judul = :judul"),
    @NamedQuery(name = "Books.findByPenulis", query = "SELECT b FROM Books b WHERE b.penulis = :penulis"),
    @NamedQuery(name = "Books.findByPenerbit", query = "SELECT b FROM Books b WHERE b.penerbit = :penerbit"),
    @NamedQuery(name = "Books.findByTahunTerbit", query = "SELECT b FROM Books b WHERE b.tahunTerbit = :tahunTerbit")})
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_buku")
    private String idBuku;
    @Basic(optional = false)
    @Column(name = "judul")
    private String judul;
    @Basic(optional = false)
    @Column(name = "penulis")
    private String penulis;
    @Column(name = "penerbit")
    private String penerbit;
    @Column(name = "tahun_terbit")
    private Integer tahunTerbit;
    @JoinColumn(name = "id_kategori", referencedColumnName = "id_kategori")
    @ManyToOne
    private Kategori idKategori;

    public Books() {
    }

    public Books(String idBuku) {
        this.idBuku = idBuku;
    }

    public Books(String idBuku, String judul, String penulis) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public Kategori getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(Kategori idKategori) {
        this.idKategori = idKategori;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBuku != null ? idBuku.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.idBuku == null && other.idBuku != null) || (this.idBuku != null && !this.idBuku.equals(other.idBuku))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pertemuanduabelas.Books[ idBuku=" + idBuku + " ]";
    }
    
}
