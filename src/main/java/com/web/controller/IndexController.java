import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.business.services.*;
import com.business.entities.*;
@Controller
public class IndexController {

	@Autowired
	private CartService cartService;
	
	@ModelAttribute("allRooms")
	public List<Room> populateRooms() {
	    return Arrays.asList(Room.ALL);
	}
	    
	
	@RequestMapping(value="/index.jsp", params={"checkout"})
	public String checkOut(
	        final Cart cart, final BindingResult bindingResult, final ModelMap model) {
	    if (bindingResult.hasErrors()) {
	        return "index.jsp";
	    }
	    this.cartService.add(cart);
	    model.clear();
	    return "redirect:/confirmation.jsp";
	}
	
    @RequestMapping(value="/seedstartermng", params={"addRow"})
    public String addRow(final SeedStarter seedStarter, final BindingResult bindingResult) {
        seedStarter.getRows().add(new Row());
        return "seedstartermng";
    }
    
    
    @RequestMapping(value="/seedstartermng", params={"removeRow"})
    public String removeRow(final SeedStarter seedStarter, final BindingResult bindingResult, final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        seedStarter.getRows().remove(rowId.intValue());
        return "seedstartermng";
    }
}
