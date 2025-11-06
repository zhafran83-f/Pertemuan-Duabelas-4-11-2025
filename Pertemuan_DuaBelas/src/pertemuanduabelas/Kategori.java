/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuanduabelas;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
@Entity
@Table(name = "kategori")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategori.findAll", query = "SELECT k FROM Kategori k"),
    @NamedQuery(name = "Kategori.findByIdKategori", query = "SELECT k FROM Kategori k WHERE k.idKategori = :idKategori"),
    @NamedQuery(name = "Kategori.findByNamaKategori", query = "SELECT k FROM Kategori k WHERE k.namaKategori = :namaKategori"),
    @NamedQuery(name = "Kategori.findByDeskripsi", query = "SELECT k FROM Kategori k WHERE k.deskripsi = :deskripsi")})
public class Kategori implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_kategori")
    private String idKategori;
    @Basic(optional = false)
    @Column(name = "nama_kategori")
    private String namaKategori;
    @Column(name = "deskripsi")
    private String deskripsi;
    @OneToMany(mappedBy = "idKategori")
    private Collection<Books> booksCollection;

    public Kategori() {
    }

    public Kategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public Kategori(String idKategori, String namaKategori) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @XmlTransient
    public Collection<Books> getBooksCollection() {
        return booksCollection;
    }

    public void setBooksCollection(Collection<Books> booksCollection) {
        this.booksCollection = booksCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKategori != null ? idKategori.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategori)) {
            return false;
        }
        Kategori other = (Kategori) object;
        if ((this.idKategori == null && other.idKategori != null) || (this.idKategori != null && !this.idKategori.equals(other.idKategori))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pertemuanduabelas.Kategori[ idKategori=" + idKategori + " ]";
    }
    
}
