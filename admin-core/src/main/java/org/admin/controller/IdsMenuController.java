package org.admin.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageRowBounds;
import org.admin.common.IdsUtils;
import org.admin.model.IdsMenu;
import org.admin.model.IdsRole;
import org.admin.model.IdsUser;
import org.admin.model.RespBean;
import org.admin.service.IdsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Ids;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class IdsMenuController {

    @Autowired
    private IdsMenuService idsMenuService;

    @RequestMapping("/init")
    public Map<String, Object> init() {
        Map<String, Object> map = new HashMap<>();
        List<IdsMenu> menus = idsMenuService.getMenusByUserId();
        IdsUser idsUser = IdsUtils.getCurrentUser();
        List<String> list = new ArrayList<>();
        for (IdsRole role : idsUser.getRoles()) {
            list.add(role.getName());
        }
        map.put("menus", menus);
        map.put("roles", list.toArray());
        return map;
    }

    /**
     * 获取菜单树
     * @param rid
     * @return
     */
    @RequestMapping(value = "/tree/{rid}", method = RequestMethod.GET)
    public Map<String, Object> menuTree(@PathVariable Long rid) {
        Map<String, Object> map = new HashMap<>();
        List<IdsMenu> menus = idsMenuService.menuTree();
        map.put("menus", menus);
        List<String> selMids = idsMenuService.getMenusByRoleId(rid);
        map.put("mids", selMids);
        return map;
    }

    @RequestMapping("/roles")
    public List<IdsRole> allRoles() {
        return idsMenuService.roles();
    }

    @RequestMapping(value = "/deleteRole/{roleId}", method = RequestMethod.DELETE)
    public RespBean deleteRole(@PathVariable Long roleId) {
        if (idsMenuService.deleteRoleById(roleId) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public RespBean addNewRole(@RequestParam String role, @RequestParam String roleZh) {
        if (idsMenuService.addNewRole(role, roleZh) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @RequestMapping(value = "/updateMenuRole", method = RequestMethod.POST)
    public RespBean updateMenuRole(Long roleId, @RequestParam("menuIds") String menuIds, HttpServletRequest request) {
        List<Long> menuIdsList = JSON.parseArray(menuIds,Long.class);
        if (idsMenuService.updateMenuRole(roleId, menuIdsList) == menuIdsList.size()) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/updateRoles", method = RequestMethod.POST)
    public RespBean updateRoles(String userId, String roleIds) {
        List<Long> roleIdsList = JSON.parseArray(roleIds,Long.class);
        if (idsMenuService.updateRoles(userId, roleIdsList) > 0) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping("/id/{hrId}")
    public IdsUser getUserById(@PathVariable Long userId) {
        return idsMenuService.getUserById(userId);
    }

    @RequestMapping("/user/{keywords}")
    public List<IdsUser> getUserByKeywords(@PathVariable(required = false) String keywords) {
        List<IdsUser> list = idsMenuService.getUserByKeywords(keywords);
        return list;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "") String name) {

        Map<String, Object> map = new HashMap<>();
        page = (page - 1) * limit;
        PageRowBounds pageRowBounds = new PageRowBounds(page, limit);
        List<IdsMenu> menus = idsMenuService.getMenus(pageRowBounds,name);

        map.put("menus", menus);
        map.put("total", pageRowBounds.getTotal());
        return map;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RespBean create(IdsMenu menu) {
        try {
            if (idsMenuService.addMenu(menu)) {
                return RespBean.ok("添加成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败!");
        }
        return RespBean.error("添加失败!");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RespBean update(IdsMenu menu) {
        try {
            if (idsMenuService.updateMenu(menu)) {
                return RespBean.ok("编辑成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("编辑失败!");
        }
        return RespBean.error("编辑失败!");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RespBean delete(Long id) {
        try {
            if (idsMenuService.deleteMenu(id)) {
                return RespBean.ok("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("删除失败!");
        }
        return RespBean.error("删除失败!");
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public RespBean createUser(IdsUser user) {
        try {
            if (idsMenuService.createUser(user) == 1) {
                return RespBean.ok("添加成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败!");
        }
        return RespBean.error("添加失败!");
    }

    @GetMapping(value = "/deleteUser/{userId}")
    public RespBean deleteUser(@PathVariable String userId) {
        try {
            if (idsMenuService.deleteUser(userId) == 1) {
                return RespBean.ok("添加成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败!");
        }
        return RespBean.error("添加失败!");
    }
}
