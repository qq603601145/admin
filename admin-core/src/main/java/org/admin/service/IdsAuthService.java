package org.admin.service;

import org.admin.mapper.IdsAuthMapper;
import org.admin.model.IdsUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class IdsAuthService implements UserDetailsService {

    @Resource
    IdsAuthMapper idsAuthMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        IdsUser user = idsAuthMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }
}
