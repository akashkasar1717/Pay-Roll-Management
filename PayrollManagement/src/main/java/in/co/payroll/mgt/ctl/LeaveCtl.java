package in.co.payroll.mgt.ctl;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.co.payroll.mgt.dto.LeaveDTO;
import in.co.payroll.mgt.dto.UserDTO;
import in.co.payroll.mgt.exception.DuplicateRecordException;
import in.co.payroll.mgt.form.LeaveForm;
import in.co.payroll.mgt.service.LeaveServiceInt;

@Controller
@RequestMapping(value = "/ctl/Leave")
public class LeaveCtl extends BaseCtl {

	@Autowired
	LeaveServiceInt leaveService;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String display(@RequestParam(required = false) Long id, @ModelAttribute("form") LeaveForm form,
			Model model) {

		if (form.getId() > 0) {
			form.populate(leaveService.findByPK(id));
		}

		return "Leave";

	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(Locale locale, @RequestParam String operation, @ModelAttribute("form") @Valid LeaveForm form,
			BindingResult bindingResult, Model model) {

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:Leave";
		}

		if (OP_CANCEL.equalsIgnoreCase(operation)) {
			return "redirect:Leave/Search";
		}

		if (bindingResult.hasErrors()) {
			System.err.println("input error");
			return "Leave";
		}

		if (OP_SAVE.equalsIgnoreCase(operation)) {

			LeaveDTO dto = (LeaveDTO) form.getDto();

			try {
				String msg = null;

				if (form.getId() > 0) {
					leaveService.update(dto);
					msg = messageSource.getMessage("message.success.update", null, locale);
				}
				if (form.getId() == 0) {
					leaveService.add(dto);
					msg = messageSource.getMessage("message.success.add", null, locale);
				}
				model.addAttribute("success", msg);
			} catch (DuplicateRecordException e) {
				String msg = messageSource.getMessage("message.leaveIsAlreadyExist", null, locale);
				model.addAttribute("error", msg);
			}
			return "Leave";
		}
		return null;

	}

	@RequestMapping(value = "/Search", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchList(Locale locale, @ModelAttribute("form") LeaveForm form,
			@RequestParam(required = false) String operation, HttpSession session,
			RedirectAttributes redirectAttributes, Model model) {

		if (OP_RESET.equalsIgnoreCase(operation)) {
			return "redirect:/ctl/Leave/Search";
		}
		int pageNo = form.getPageNo();
		int pageSize = form.getPageSize();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		}

		pageNo = (pageNo < 1) ? 1 : pageNo;
		pageSize = (pageSize < 1) ? 10 : pageSize;

		if (OP_DELETE.equals(operation)) {

			pageNo = 1;

			if (form.getIds() != null) {

				for (long id : form.getIds()) {
					LeaveDTO dto = new LeaveDTO();
					dto.setId(id);
					leaveService.delete(dto);
				}
				String msg = messageSource.getMessage("message.success.delete", null, locale);
				model.addAttribute("success", msg);

				// redirectAttributes.addFlashAttribute("success", msg);
				// return "RoleList";

			} else {

				System.out.println("select at least one records");
				String msg = messageSource.getMessage("message.unsuccess.selectatleastonerecords", null, locale);
				model.addAttribute("error", msg);
			}

		}

		LeaveDTO dto = (LeaveDTO) form.getDto();

		UserDTO uDto = (UserDTO) session.getAttribute("userLogin");
		if (uDto.getRoleId() == 2) {
			dto.setEmailId(uDto.getEmailId());
		}

		List<LeaveDTO> list = leaveService.search(dto, pageNo, pageSize);

		List<LeaveDTO> totallist = leaveService.search(null);

		model.addAttribute("list", list);

		/*
		 * Set<Long> roleIds = new HashSet<Long>(); for (UserDTO userDTO : list) {
		 * roleIds.add(userDTO.getRoleId()); }
		 * 
		 * model.addAttribute("roleMap", roleservice.getMapDTO(roleIds));
		 * 
		 */ if (list.size() == 0 && !OP_DELETE.equalsIgnoreCase(operation)) {
			String msg = messageSource.getMessage("message.unsuccess.recoudNotFound", null, locale);
			model.addAttribute("error", msg);
		}

		int listsize = list.size();
		int total = totallist.size();
		int pageNoPageSize = pageNo * pageSize;

		form.setPageNo(pageNo);
		form.setPageSize(pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("listsize", listsize);
		model.addAttribute("total", total);
		model.addAttribute("pagenosize", pageNoPageSize);

		return "LeaveList";
	}

}
