package in.co.payroll.mgt.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import in.co.payroll.mgt.dto.LeaveDTO;
import in.co.payroll.mgt.exception.DuplicateRecordException;

public interface LeaveServiceInt {

	public long add(LeaveDTO dto) throws DuplicateRecordException;

	public void delete(LeaveDTO dto);

	public void update(LeaveDTO dto) throws DuplicateRecordException;

	public LeaveDTO findByPK(long pk);

	public LeaveDTO findByName(String name);

	public List<LeaveDTO> search(LeaveDTO dto);

	public List search(LeaveDTO dto, int pageNo, int pageSize);

	public Map<Long, LeaveDTO> getMapDTO(Set<Long> ids);

}
