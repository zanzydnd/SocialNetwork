package ru.itis.kpfu.kozlov.social_network_api.exception;

public class NotFoundException extends Exception{
    public NotFoundException(){}

    public NotFoundException(String messg){
        super(messg);
    }
}
