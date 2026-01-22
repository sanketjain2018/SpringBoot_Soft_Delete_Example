package in.sj.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.sj.entity.Employee;
import in.sj.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	private final EmployeeRepository repo;
	
	public EmployeeService(EmployeeRepository repo) {
		this.repo = repo;
	}
	
	// Active employees (active = 0)
    public List<Employee> getAllActiveEmployees() {
        return repo.findAll(); 
        // @SQLRestriction("active = 0") applied automatically
    }
    
 // Soft-deleted employees (active = 1)
    public List<Employee> getDeletedEmployees() {
        return repo.findDeletedEmployees();
    }

    // Save or update
    public void save(Employee employee) {
        repo.save(employee);
    }

    // SOFT DELETE (triggers @SQLDelete)
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Restore employee
    public void restore(Long id) {
        repo.restoreEmployee(id);
    }
}
