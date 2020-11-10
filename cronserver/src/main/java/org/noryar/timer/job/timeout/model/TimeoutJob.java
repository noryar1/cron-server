package org.noryar.timer.job.timeout.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 超时任务实体类
 *
 * @author noryar
 */
@Entity
@Table(name = "timeout_job")
public class TimeoutJob implements Serializable {

    private static final long serialVersionUID = -6150923421760984291L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "timeout")
    private int timeout;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeoutJob{");
        sb.append("id=").append(id);
        sb.append(", timeout=").append(timeout);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }
}
