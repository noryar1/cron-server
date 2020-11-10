package org.noryar.timer.job.crontab.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * crontab任务实体类
 *
 * @author noryar
 */
@Entity
@Table(name = "crontab_job")
public class CrontabJob implements Serializable {

    private static final long serialVersionUID = -6150923421760984291L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "crontab")
    private String crontab;

    @Column(name = "cmd")
    private String cmd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrontab() {
        return crontab;
    }

    public void setCrontab(String crontab) {
        this.crontab = crontab;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeoutJob{");
        sb.append("id=").append(id);
        sb.append(", crontab='").append(crontab).append('\'');
        sb.append(", cmd='").append(cmd).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
