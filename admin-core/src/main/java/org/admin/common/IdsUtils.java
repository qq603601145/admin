package org.admin.common;

import org.admin.model.IdsUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class IdsUtils {
    public static IdsUser getCurrentUser() {
        return (IdsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
