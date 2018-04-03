package Core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "groups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="periodStart")
    private Date periodStart;
    @Column(name="periodFinish")
    private Date periodFinish;

    public Group() {
    }

    public Group(String name, Date periodStart, Date periodFinish, Integer id) {
        this.name = name;
        this.periodStart = periodStart;
        this.periodFinish = periodFinish;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodFinish() {
        return periodFinish;
    }

    public void setPeriodFinish(Date periodFinish) {
        this.periodFinish = periodFinish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void patch(Group group) {
        name = (group.getName() != null) ? group.getName() : name;
        periodFinish = (group.getPeriodFinish() != null) ? group.getPeriodFinish() : periodFinish;
        periodStart = (group.getPeriodStart() != null) ? group.getPeriodStart() : periodStart;
    }
}
