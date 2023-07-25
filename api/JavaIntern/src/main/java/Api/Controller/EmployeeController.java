package Api.Controller;

import Api.Entity.Employee;
import Api.Exception.ResourceNotFoundException;
import Api.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/home")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    //get all employees
    @GetMapping(value = "/employees")
    public List<Employee> getEmployee(){
        return this.employeeRepository.findAll();
    }

    // get employee by id
    @GetMapping(value = "/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Integer eid)
    {
        return this.employeeRepository.findById(eid).orElseThrow(()-> new ResourceNotFoundException("user not found with id"+eid));
    }

    // create employee

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return this.employeeRepository.save(employee);
    }


    //update Employee
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable("id")Integer eid){
        Employee existing = this.employeeRepository.findById(eid).orElseThrow(()-> new ResourceNotFoundException("user not found with id"+eid));
        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        return this.employeeRepository.save(existing);
    }

    // delete Employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Integer eid){
        Employee exisitngemp = this.employeeRepository.findById(eid).orElseThrow(()->new ResourceNotFoundException("user not found to be deleted"+eid));
        this.employeeRepository.delete(exisitngemp);
        return ResponseEntity.ok().build();
    }

}
