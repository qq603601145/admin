package org.admin.model;

import java.io.Serializable;
import java.util.List;

public class IdsMenu implements Serializable {
    private static final long serialVersionUID = 7895581355792774592L;

    private String id;//id

    private String name;//菜单名

    private String path;//访问地址

    private String component;//组件

    private String icon;

    private String redirect;//面包屑中点击跳转地址

    private String parentId;//父id

    private List<IdsRole> roles;

    private List<IdsMenu> children;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<IdsMenu> getChildren() {
        return children;
    }

    public void setChildren(List<IdsMenu> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<IdsRole> getRoles() {
        return roles;
    }

    public void setRoles(List<IdsRole> roles) {
        this.roles = roles;
    }
}
