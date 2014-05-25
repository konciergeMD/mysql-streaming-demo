package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DumbRelationModel {

    @Id
    public Long id;

    public String value;

}
