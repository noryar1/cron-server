package org.noryar.timer.register.bean;

import java.io.Serializable;

public class CrontabJobBean implements Serializable {
    private static final long serialVersionUID = 320991027287321995L;
    private Long id;
    private String crontab;
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
}
