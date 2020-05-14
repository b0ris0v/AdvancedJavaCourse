package web.dataSets;

public class UserDataset {
    private long id;
    private String name;

    public UserDataset(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
