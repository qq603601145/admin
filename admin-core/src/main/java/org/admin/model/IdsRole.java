package org.admin.model;

import java.io.Serializable;

public class IdsRole implements Serializable {
    private static final long serialVersionUID = -5713305415072395449L;

    private String id;

    private String name;

    private String nameZh;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
