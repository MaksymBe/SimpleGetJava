import java.util.Date;

public class Group {
    private String name;
    private Date periodStart;
    private Date periodFinish;
    private Integer id;

    public Group() {
    }

    public Group(String name, Date periodStart, Date periodFinish, Integer id){
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
}
