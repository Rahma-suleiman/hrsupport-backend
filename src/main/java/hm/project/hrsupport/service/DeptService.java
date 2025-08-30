package hm.project.hrsupport.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.DeptDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.DeptRepository;

@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DeptDTO> getAllDepartment() {
        List<Department> depts = deptRepository.findAll();
        return depts.stream()
        .map(dept -> modelMapper.map(dept, DeptDTO.class))
        .collect(Collectors.toList());
    }

    public DeptDTO getDeptById(Long id) {
        Department deptId = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department not found with id" + id));
        return modelMapper.map(deptId, DeptDTO.class);
    }

    public DeptDTO creatDepartment(DeptDTO deptDTO) {
        Department department = modelMapper.map(deptDTO, Department.class);
        // check if department exists by name
        Optional<Department> isDeptExist = deptRepository.findByName(deptDTO.getName());
        if (isDeptExist.isPresent()) {
            // throw new ApiRequestException("department exist");
             throw new ApiRequestException("Department with name " + deptDTO.getName() + " already exists");
        }
        Department saveDept = deptRepository.save(department);
        return modelMapper.map(saveDept, DeptDTO.class);
    }


    public DeptDTO editDepartment(Long id, DeptDTO deptDTO) {
        Department existingDepart = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department ID not found"));
        deptDTO.setId(existingDepart.getId());
        modelMapper.map(deptDTO, existingDepart);
        Department updatedDept = deptRepository.save(existingDepart);
        return modelMapper.map(updatedDept, DeptDTO.class);
    }

    public void deleteDepartment(Long id) {
        deptRepository.deleteById(id);
    }

}

// {
//     "name": "IT"
// }
//   {
//     "name": "HR"
//   },
//   {
//     "name": "Account"
//   },
//   {
//     "name": "Science"
//   },
//   {
//     "name": "Agriculture"
//   },
//   {
//     "name": "Finance"
//   },
//   {
//     "name": "Procurement"
//   },
//   {
//     "name": "Production"
//   },
//   {
//     "name": "Sale and Marketing"
//   }