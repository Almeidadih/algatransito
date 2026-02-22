package com.example.algatransito.domain.excepiton;

public class NegocioExcepiton extends RuntimeException {

    public NegocioExcepiton(String message) {
        super(message);
    }
}
