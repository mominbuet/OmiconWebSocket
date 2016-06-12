/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByUser", query = "SELECT u FROM Users u WHERE u.user = :user"),
    @NamedQuery(name = "Users.findBySession", query = "SELECT u FROM Users u WHERE u.session = :session"),
    @NamedQuery(name = "Users.findByIsAlive", query = "SELECT u FROM Users u WHERE u.isAlive = :isAlive"),
    @NamedQuery(name = "Users.findByCommand", query = "SELECT u FROM Users u WHERE u.command = :command"),
    @NamedQuery(name = "Users.findByExecuted", query = "SELECT u FROM Users u WHERE u.executed = :executed"),
    @NamedQuery(name = "Users.findByUpdatedTime", query = "SELECT u FROM Users u WHERE u.updatedTime = :updatedTime")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "session")
    private String session;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_alive")
    private int isAlive;
    @Size(max = 600)
    @Column(name = "command")
    private String command;
    @Column(name = "executed")
    private Integer executed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @PrePersist
    public void onCreate() {
        updatedTime = new Date();
    }

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String user, String session, int isAlive, Date updatedTime) {
        this.id = id;
        this.user = user;
        this.session = session;
        this.isAlive = isAlive;
        this.updatedTime = updatedTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(int isAlive) {
        this.isAlive = isAlive;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getExecuted() {
        return executed;
    }

    public void setExecuted(Integer executed) {
        this.executed = executed;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Users[ id=" + id + " ]";
    }

}
