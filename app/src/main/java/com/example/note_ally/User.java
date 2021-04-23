package com.example.note_ally;

public class User {

    private String id;
    private String name;
    private String email;
    private String description;
    private String dateofbirth;
    private String phone;

    public User(){
    }

    public User(String id, String name,String email,String description,String dateofbirth,String phone){
        this.id = id;
        this.name=name;
        this.email=email;
        this.description=description;
        this.dateofbirth=dateofbirth;
        this.phone=phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
