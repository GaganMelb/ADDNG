package com.project.gagan.addng;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Gagan on 31-Mar-15.
 */
@ParseClassName("Centre")
public class Centre extends ParseObject{

    private String id; // centre id (ObjectID)
    private String name; // name of the centre
    private int numberOfPatients;

    public Centre(){

    }


    Centre(String centreId, String centreName, int numPatients) {
        id = centreId;
        name = centreName;
        numberOfPatients = numPatients;

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
    public int getNumberOfPatients() {
        return numberOfPatients;
    }
    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    @Override
    public String toString() {
        return this.getName()+ " " + "-" + " "+this.numberOfPatients;
    }



}
