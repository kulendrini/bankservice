package com.app.bankservice.model;

import java.util.Date;

public class HealthCheckResponseDTO {

    private String status;
    private Date timestamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
