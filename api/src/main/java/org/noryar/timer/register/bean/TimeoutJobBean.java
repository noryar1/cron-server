package org.noryar.timer.register.bean;

import java.io.Serializable;

public class TimeoutJobBean implements Serializable {
    private static final long serialVersionUID = 585678172270636933L;
    private Long id;
    private int timeout;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeoutJobBean{");
        sb.append("id=").append(id);
        sb.append(", timeout=").append(timeout);
        sb.append('}');
        return sb.toString();
    }
}
