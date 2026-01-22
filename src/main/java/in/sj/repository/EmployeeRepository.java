package in.sj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.sj.entity.Employee;
import jakarta.transaction.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	 /*
     * IMPORTANT:
     * Because you used @SQLRestriction("active = 0"),
     * normal JPA queries automatically return ONLY active records.
     */

    // 🔍 Fetch ONLY soft-deleted employees (ADMIN use)
    @Query(value = "SELECT * FROM tbl_employee WHERE active = 1", nativeQuery = true)
    List<Employee> findDeletedEmployees();

    // ♻ Restore soft-deleted employee
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_employee SET active = 0 WHERE id = :id", nativeQuery = true)
    void restoreEmployee(Long id);
}
