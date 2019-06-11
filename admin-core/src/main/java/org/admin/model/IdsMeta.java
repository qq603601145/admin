package org.admin.model;

import java.io.Serializable;
import java.util.List;

public class IdsMeta implements Serializable {
    private static final long serialVersionUID = -7411239528866111547L;

    private String title;//面包屑和菜单显示名称

    private List<IdsRole> roles;//菜单权限

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IdsRole> getRoles() {
        return roles;
    }

    public void setRoles(List<IdsRole> roles) {
        this.roles = roles;
    }
}
