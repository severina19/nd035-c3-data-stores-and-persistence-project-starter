package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User implements Serializable {
    String phoneNumber;

    String notes;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    List<Pet> pets = new ArrayList<Pet>();

    public Customer() {
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void insertPet(Pet pet) {
        pets.add(pet);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
