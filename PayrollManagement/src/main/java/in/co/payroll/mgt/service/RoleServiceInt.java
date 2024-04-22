package in.co.payroll.mgt.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import in.co.payroll.mgt.dto.RoleDTO;
import in.co.payroll.mgt.exception.DuplicateRecordException;

public interface RoleServiceInt {

	public long add(RoleDTO dto) throws DuplicateRecordException;

	public void delete(RoleDTO dto);

	public void update(RoleDTO dto) throws DuplicateRecordException;

	public RoleDTO findByPK(long pk);

	public RoleDTO findByName(String name);

	public List<RoleDTO> search(RoleDTO dto);

	public List search(RoleDTO dto, int pageNo, int pageSize);

	public Map<Long, RoleDTO> getMapDTO(Set<Long> ids);

}
