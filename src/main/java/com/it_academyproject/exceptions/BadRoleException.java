package com.it_academyproject.exceptions;

public final class BadRoleException extends RuntimeException {

    public BadRoleException() {super ("Invalid user role.");}

    public BadRoleException(String message) {super(message);}

}
