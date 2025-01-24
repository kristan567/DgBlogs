package com.example.Blog_Application2.projection;

public interface UserProjection {
    long getId();

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getEmail();

    String getPhone();

    boolean isActive();
}
