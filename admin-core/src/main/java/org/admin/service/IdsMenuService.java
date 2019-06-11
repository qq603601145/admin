package org.admin.service;

import com.github.pagehelper.PageRowBounds;
import org.admin.mapper.IdsAuthMapper;
import org.admin.mapper.IdsMenuMapper;
import org.admin.model.IdsMenu;
import org.admin.model.IdsRole;
import org.admin.model.IdsUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "menus_cache")
public class IdsMenuService {
    @Resource
    private IdsMenuMapper idsMenuMapper;

    @Resource
    private IdsAuthMapper idsAuthMapper;

//    @Cacheable(key = "#root.methodName")
    public List<IdsMenu> getAllMenu(){
        return idsMenuMapper.getAllMenu();
    }

    public List<IdsMenu> getMenusByUserId() {
        IdsUser idsUser = (IdsUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idsMenuMapper.getMenusByUserId(idsUser.getUserId());
    }

    public List<IdsMenu> menuTree() {
        return idsMenuMapper.menuTree();
    }

    public List<String> getMenusByRoleId(Long roleId) {
        return idsMenuMapper.getMenusByRoleId(roleId);
    }

    public boolean addMenu(IdsMenu menu){
        return idsMenuMapper.addMenu(menu);
    }

    public boolean updateMenu(IdsMenu menu){
        return idsMenuMapper.updateMenu(menu);
    }

    public boolean deleteMenu(Long id){
        return idsMenuMapper.deleteMenu(id);
    }

    public List<IdsMenu> getMenus(PageRowBounds pageRowBounds,String name) {
        return idsMenuMapper.getMenus(pageRowBounds,name);
    }

    public List<IdsRole> roles() {
        return idsMenuMapper.roles();
    }

    public int deleteRoleById(Long roleId) {
        return idsMenuMapper.deleteRoleById(roleId);
    }

    public int addNewRole(String role, String roleZh) {
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        return idsMenuMapper.addNewRole(role, roleZh);
    }

    public int updateMenuRole(Long roleId, List<Long> menuIds) {
        idsMenuMapper.deleteMenuByRoleId(roleId);
        if (menuIds.size() == 0) {
            return 0;
        }
        return idsMenuMapper.addMenuRole(roleId, menuIds);
    }

    public int updateRoles(String userId, List<Long> roleIds) {
        int i = idsMenuMapper.deleteRoleByUserId(userId);
        if (!roleIds.isEmpty()) {
            i = idsMenuMapper.addRolesForUser(userId, roleIds);
        }
        return i;
    }

    public IdsUser getUserById(Long userId) {
        return idsMenuMapper.getUserById(userId);
    }

    public List<IdsUser> getUserByKeywords(String keywords) {
        return idsMenuMapper.getUserByKeywords(keywords);
    }

    public int createUser(IdsUser user){
        //如果用户名存在，返回错误
        if (idsAuthMapper.loadUserByUsername(user.getUserId()) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPassword());
        user.setPassword(encode);
        return idsMenuMapper.createUser(user);
    }

    public int deleteUser(String userId) {
        return idsMenuMapper.deleteUser(userId);
    }
}
