package model;

import java.util.ArrayList;

public class File {

    private String name;
    private String owner;
    private ArrayList<String> viewers;

    public File() {
    }

    public File(String name, String owner, ArrayList<String> viewers) {
        this.name = name;
        this.owner = owner;
        this.viewers = viewers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<String> getViewers() {
        return viewers;
    }

    public void setViewers(ArrayList<String> viewers) {
        this.viewers = viewers;
    }
}
