package org.admin.mapper;

import com.github.pagehelper.PageRowBounds;
import org.admin.model.IdsMenu;
import org.admin.model.IdsRole;
import org.admin.model.IdsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IdsMenuMapper {

    List<IdsMenu> getAllMenu();

    List<IdsMenu> getMenus(PageRowBounds pageRowBounds,@Param("name") String name);

    List<IdsMenu> getMenusByUserId(String userId);

    List<IdsMenu> menuTree();

    List<String> getMenusByRoleId(Long roleId);

    boolean addMenu(IdsMenu menu);

    int deleteMenuByRoleId(@Param("roleId") Long roleId);

    int addMenuRole(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    boolean updateMenu(IdsMenu menu);

    boolean deleteMenu(Long id);

    List<IdsRole> roles();

    int deleteRoleById(Long roleId);

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleByUserId(String userId);

    int addRolesForUser(@Param("userId") String userId, @Param("roleIds") List<Long> roleIds);

    IdsUser getUserById(Long userId);

    List<IdsUser> getUserByKeywords(@Param("keywords") String keywords);

    int createUser(IdsUser user);

    int deleteUser(String userId);
}
