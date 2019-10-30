package  com.jxk.oto.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import  com.jxk.oto.service.ProductCategoryService;
import  com.jxk.oto.util.HttpServletRequestUtil;
import  com.jxk.oto.dto.ProductCategoryExecution;
import  com.jxk.oto.dto.Result;
import  com.jxk.oto.entity.*;
import  com.jxk.oto.enums.*;

@RestController
@RequestMapping("/shop")
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;
	 @RequestMapping(value="removeproductcategory" ,method = RequestMethod.POST)
	 private Map<String, Object> removeProductCategorys(
				Long productCategoryId,
				HttpServletRequest request) {
			Map<String, Object> modelMap = new HashMap<String, Object>();
			
			if(productCategoryId!=null&&productCategoryId>0) {
				try {
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				ProductCategoryExecution pe=productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
					
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg",pe.getStateInfo());
			}
				}catch (RuntimeException e) {
					modelMap.put("success", false);
					modelMap.put("errmsg", e.toString());
					return modelMap;
				}
			}else {
				modelMap.put("seccess", false);
				modelMap.put("errMsg", "至少选择一个商品");
			}
			
			return modelMap;
		}
		
 

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	private Result<List<ProductCategory>> listProductCategorys(HttpServletRequest request) {
		/*Shop shop = new Shop();
		shop.setShopId(1L);
		request.getSession().setAttribute("currentShop", shop);*/
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.queryProductCategory(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true, list);// WEB-INF/html/"shoplist".html
		} else {

			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;

			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}

	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	private Map<String, Object> addProductCategorys(
			@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		String name=HttpServletRequestUtil.getString(request, "name");
		System.out.println("名字"+name);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.
						batchInsertProductCategorylist(productCategoryList);
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS
						.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "至少输入一个商品");
		}
		return modelMap;
	}
	

}
