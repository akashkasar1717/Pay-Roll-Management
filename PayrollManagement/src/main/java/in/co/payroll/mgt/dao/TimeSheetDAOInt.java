package in.co.payroll.mgt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import in.co.payroll.mgt.dto.TimeSheetDTO;

public interface TimeSheetDAOInt {

	public long add(TimeSheetDTO dto);

	public void update(TimeSheetDTO dto);

	public void delete(TimeSheetDTO dto);

	public TimeSheetDTO findByName(String name);

	public TimeSheetDTO findByPk(long id);

	public List<TimeSheetDTO> search(TimeSheetDTO dto, long pageNo, int pageSize);

	public List<TimeSheetDTO> search(TimeSheetDTO dto);

	public Map<Long, TimeSheetDTO> getMapDTO(Set<Long> ids);

}
