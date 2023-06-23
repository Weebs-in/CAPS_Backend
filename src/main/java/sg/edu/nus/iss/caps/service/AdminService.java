package sg.edu.nus.iss.caps.service;

import sg.edu.nus.iss.caps.model.Admin;

public interface AdminService {
    Admin getAdminByUsername(String username);
}
