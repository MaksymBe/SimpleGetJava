
public class Group {
    private String name;
    private String periodStart;
    private String periodFinish;
    private Integer id;

    public Group() {
    }

    public Group(String name, String periodStart, String periodFinish, Integer id){
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

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodFinish() {
        return periodFinish;
    }

    public void setPeriodFinish(String periodFinish) {
        this.periodFinish = periodFinish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
