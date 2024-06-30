package com.petwellnes.petwellnes_backend.infra.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException (String s) {
        super(s);
    }
}
