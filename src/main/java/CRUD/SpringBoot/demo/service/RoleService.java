package CRUD.SpringBoot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import CRUD.SpringBoot.demo.dao.RoleDAO;
import CRUD.SpringBoot.demo.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class RoleService {

    final
    RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    public Role getRoleById(long id) {
        return roleDAO.getRoleById(id);
    }
    public List<Role> getRolesList() {
        return roleDAO.getRolesList();
    }
}
