package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class DumbModel extends Model {

    @Id
    public Long id;

    public String text;

    @ManyToMany
    public List<DumbRelationModel> relations;

}
