package  com.jxk.oto.web.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shop", method = { RequestMethod.GET,
		RequestMethod.POST })
public class ShopAdminController {
	@RequestMapping(value = "shopedit", method = RequestMethod.GET)
	private String shopregister() {
		return "shop/shopedit";
	}
	@RequestMapping("/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	@RequestMapping("/shopmanage")
	public String shopManagement() {
		return "shop/shopmanage";
	}
	@RequestMapping("/productcategorymanage")
	public String productcategory() {
		return "shop/productcategorymanage";
	}
	@RequestMapping("/test")
	public String test(){
		return "shop/test";
	}
	@RequestMapping("/productmanage")
	public String productmanage(){
		return "shop/productmanage";
	}
	@RequestMapping("/productedit")
	public String productedit(){
		return "shop/productedit";
	}


}
