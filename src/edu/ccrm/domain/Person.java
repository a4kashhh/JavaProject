package edu.ccrm.domain;

import java.time.LocalDateTime;

public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected final LocalDateTime createdAt;

    public Person(String id, String fullName, String email){
        if(id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public String getId(){ return id; }
    public String getFullName(){ return fullName; }
    public String getEmail(){ return email; }
    public LocalDateTime getCreatedAt(){ return createdAt; }

    public abstract String role();
    @Override
    public String toString(){
        return String.format("%s[id=%s,name=%s,email=%s,created=%s]", role(), id, fullName, email, createdAt);
    }
}
