package org.admin.mapper;

import org.admin.model.IdsRole;
import org.admin.model.IdsUser;

import java.util.List;

public interface IdsAuthMapper {
    IdsUser loadUserByUsername(String userId);

    List<IdsRole> getRolesByUserId(String userId);

}
