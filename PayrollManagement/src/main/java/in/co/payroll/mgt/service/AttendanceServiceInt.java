package in.co.payroll.mgt.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import in.co.payroll.mgt.dto.AttendanceDTO;
import in.co.payroll.mgt.exception.DuplicateRecordException;

public interface AttendanceServiceInt {
	
	public long add(AttendanceDTO dto) throws DuplicateRecordException;
	public void delete(AttendanceDTO dto) ;
	public void update(AttendanceDTO dto) throws DuplicateRecordException;
	public AttendanceDTO findByPK(long pk) ;
	public AttendanceDTO findByName(String name);
	public List<AttendanceDTO> search(AttendanceDTO dto);
	public List search(AttendanceDTO dto, int pageNo, int pageSize) ;
	public Map<Long, AttendanceDTO> getMapDTO(Set<Long> ids);

}
