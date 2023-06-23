package sg.edu.nus.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.caps.exceptions.ResourceNotFoundException;
import sg.edu.nus.iss.caps.model.Admin;
import sg.edu.nus.iss.caps.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminRepository repoAdmin;

    @Override
    public Admin getAdminByUsername(String username){
        return repoAdmin.getAdminByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
    }
}
