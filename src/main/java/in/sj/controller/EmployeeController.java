package in.sj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.sj.entity.Employee;
import in.sj.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	// Show active employees
	@GetMapping
	public String list(Model model) {
		model.addAttribute("employees", service.getAllActiveEmployees());
		model.addAttribute("employee", new Employee());
		
		return "employee-list";
	}

	// Save employee (PRG pattern)
	@PostMapping("/save")
	public String save(@ModelAttribute Employee employee) {
		service.save(employee);
		return "redirect:/employees";
	}

	// Soft delete
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "redirect:/employees";
	}

	// View deleted employees
	@GetMapping("/deleted")
	public String deleted(Model model) {
		model.addAttribute("employees", service.getDeletedEmployees());
		return "deleted-list";
	}

	// Restore employee
	@GetMapping("/restore/{id}")
	public String restore(@PathVariable Long id) {
		service.restore(id);
		return "redirect:/employees/deleted";
	}
}
