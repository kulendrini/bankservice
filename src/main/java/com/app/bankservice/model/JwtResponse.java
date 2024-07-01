package com.app.bankservice.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String jwttoken;
    private final long expiresIn;

    public JwtResponse(String jwttoken, long expiresIn) {
        this.jwttoken = jwttoken;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
