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
@Table(name = "log_rpc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogRpc.findAll", query = "SELECT l FROM LogRpc l"),
    @NamedQuery(name = "LogRpc.findByLogId", query = "SELECT l FROM LogRpc l WHERE l.logId = :logId"),
    @NamedQuery(name = "LogRpc.findByUser", query = "SELECT l FROM LogRpc l WHERE l.user = :user"),
    @NamedQuery(name = "LogRpc.findByLog", query = "SELECT l FROM LogRpc l WHERE l.log = :log"),
    @NamedQuery(name = "LogRpc.findByInsertTime", query = "SELECT l FROM LogRpc l WHERE l.insertTime = :insertTime")})
public class LogRpc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_id")
    private Integer logId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "log")
    private String log;
    @Column(name = "insert_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTime;

    @PrePersist
    protected void onCreate() {
        insertTime = new Date();
    }

    public LogRpc() {
    }

    public LogRpc(Integer logId) {
        this.logId = logId;
    }

    public LogRpc(Integer logId, String user, String log) {
        this.logId = logId;
        this.user = user;
        this.log = log;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogRpc)) {
            return false;
        }
        LogRpc other = (LogRpc) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.LogRpc[ logId=" + logId + " ]";
    }

}
