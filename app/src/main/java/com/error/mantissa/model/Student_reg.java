package com.error.mantissa.model;

public class Student_reg
{
   private String name,id, dep, email, phone, password,unique_key;


    public Student_reg()
    {

    }

    public Student_reg(String name, String id, String dep, String email, String phone, String password, String unique_key) {
        this.name = name;
        this.id = id;
        this.dep = dep;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.unique_key = unique_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }
}
