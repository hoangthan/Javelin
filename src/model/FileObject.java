package model;

import java.io.Serializable;

public class FileObject implements Serializable {
    private static final long serialVersionUID = 1682768651715909261L;

    String name;
    long fileID;
    long size;
    String URI;
    long ownerID;
    String parent = "root";
    boolean isFile;

    public FileObject(long ownerID, String parent) {
        this.ownerID = ownerID;
        this.parent = parent;
    }

    public FileObject(long fileID, String name, long size, String URI, String parent, boolean isFile) {
        this.fileID = fileID;
        this.name = name;
        this.size = size;
        this.URI = URI;
        this.parent = parent;
        this.isFile = isFile;
    }

    public FileObject(String name, long size, String URI, String parent, boolean isFile) {
        this.name = name;
        this.size = size;
        this.URI = URI;
        this.parent = parent;
        this.isFile = isFile;
    }

    public FileObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFileID() {
        return fileID;
    }

    public void setFileID(long fileID) {
        this.fileID = fileID;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }
}
