package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.AttendDTO;
import hm.project.hrsupport.entity.Attendance;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.AttendRepository;
import hm.project.hrsupport.repository.EmpRepository;

@Service
public class AttendService {
    
    @Autowired
    private AttendRepository attendRepository;
    private ModelMapper modelMapper;
    private EmpRepository empRepository;

    public AttendService(AttendRepository attendRepository, ModelMapper modelMapper, EmpRepository empRepository) {
        this.attendRepository = attendRepository;
        this.modelMapper = modelMapper;
        this.empRepository = empRepository;
    }


    public List<AttendDTO> getAllAttendance() {
        List<Attendance> attendance = attendRepository.findAll();
        return attendance.stream()
                .map(attend -> modelMapper.map(attend, AttendDTO.class))
                .collect(Collectors.toList());
    }


    public AttendDTO addAttendance(AttendDTO attendDTO) {
        Attendance attend = modelMapper.map(attendDTO, Attendance.class);

        Employee emp = empRepository.findById(attendDTO.getEmployeeId())
                .orElseThrow(()-> new ApiRequestException("Employee ID not found"));
        attend.setEmployee(emp);
        Attendance saveAttendance = attendRepository.save(attend);
        return modelMapper.map(saveAttendance, AttendDTO.class);
    }


    public AttendDTO getAttendanceById(Long id) {
        Attendance attend = attendRepository.findById(id)
                    .orElseThrow(()-> new ApiRequestException("attendance not found with id "+id));
        return modelMapper.map(attend, AttendDTO.class);
    }


    public void deleteAttendance(Long id) {
        attendRepository.deleteById(id);
    }


    public AttendDTO editAttendance(Long id, AttendDTO attendDTO) {
        Attendance attendance = attendRepository.findById(id)
                .orElseThrow(()-> new ApiRequestException("Attendance not found"));
        attendDTO.setId(attendance.getId());
        modelMapper.map(attendDTO, attendance);
        Employee empAttend = empRepository.findById(attendDTO.getEmployeeId())
                .orElseThrow(()-> new ApiRequestException("Attendance not found"));
        attendance.setEmployee(empAttend);
        Attendance savedAttendance = attendRepository.save(attendance);
        return modelMapper.map(savedAttendance, AttendDTO.class);
    }

}
// {
//   "date": "2025-08-27",
//   "checkInTime": "09:15",
//   "checkOutTime": "17:30",
//   "status": "PRESENT",
//   "employeeId": 1
// }
// {
//     "date": "2025-08-19",
//     "checkInTime": "08:30",
//     "checkOutTime": "17:00",
//     "status": "ABSENT",
//     "employeeId": 1
// }