package com.project.gagan.addng;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Gagan on 05-Apr-15.
 */
@ParseClassName("Patient")

public class Patient extends ParseObject{

    private String id; // Patient id (ObjectID)
    private String name;// name of the patient
    private Boolean flag;// active_flag
private String centre_code;

    public Patient(){

    }

    Patient(String centreId, String centreName, Boolean active_flag,String centre) {
        id = centreId;
        name = centreName;
        flag=active_flag;
        centre_code=centre;

    }

    Patient(String centreId, String centreName, Boolean active_flag) {
        id = centreId;
        name = centreName;
        flag=active_flag;

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
    public Boolean getFlag() {
        return flag;
    }
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getParseObject(){
        return centre_code;
    }

    public String setParseObject(String centre) {
        this.centre_code = centre;
        return centre_code;
    }


    @Override
    public String toString() {
        return this.getName()+" " + this.getFlag();
    }


}
