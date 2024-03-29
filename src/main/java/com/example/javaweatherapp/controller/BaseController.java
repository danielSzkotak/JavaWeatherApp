package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.view.ViewFactory;

public abstract class BaseController {

    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(ViewFactory viewFactory, String fxmlName) {

        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {

        return fxmlName;
    }
}