package com.example.library.service;

import com.example.library.model.Staff;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final List<Staff> staffList = new ArrayList<>();
    private long idCounter = 1;

    public Staff createStaff(String name, String role) {
        Staff staff = new Staff();
        staff.setId(idCounter++);
        staff.setName(name);
        staff.setRole(role);
        staffList.add(staff);
        return staff;
    }

    public String getStaffInfo(Long id) {
        Optional<Staff> staff = staffList.stream().filter(s -> s.getId().equals(id)).findFirst();
        if (staff.isEmpty()) return "Staff not found";
        Staff s = staff.get();
        return "ID: " + s.getId() + ", Name: " + s.getName() + ", Role: " + s.getRole();
    }

    public List<Staff> listAll() {
        return staffList;
    }
}
