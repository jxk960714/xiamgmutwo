package  com.jxk.oto.web.shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  com.jxk.oto.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import  com.jxk.oto.dto.ImageHoder;
import  com.jxk.oto.dto.ShopExecution;
import  com.jxk.oto.entity.*;
import  com.jxk.oto.enums.ShopStateEnum;
import  com.jxk.oto.service.AreaService;
import  com.jxk.oto.service.ShopCategoryService;
import  com.jxk.oto.service.ShopService;
import  com.jxk.oto.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shop")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired 
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	@RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String, Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId<=0) {
			Object currentShopObj=request.getSession().getAttribute("currentShop");
			if(currentShopObj==null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/Oto/shop/shoplist");
		
				
			}else {
			   Shop currentShop=(Shop)currentShopObj;
			   modelMap.put("redirect", false);
			   modelMap.put("shopId",currentShop.getShopId());
			}
			
		}else {
			Shop currentShop=new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
		
	}
	
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object>getShoplist(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user=new PersonInfo();
		user.setUserId(1L);
		user.setName("jxk");
		request.getSession().setAttribute("user", user);
		 user = (PersonInfo) request.getSession()
				.getAttribute("user");
		user.setUserId(1L);
		/*long employeeId = user.getUserId();*/
	/*	if (hasAccountBind(request, employeeId)) {
			modelMap.put("hasAccountBind", true);
		} else {
			modelMap.put("hasAccountBind", false);
		}*/
	/*	List<Shop> list = new ArrayList<Shop>();*/
		try {
			Shop shopCondition=new Shop();
			shopCondition.setOwner(user);
			ShopExecution se=shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList",se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
			/*ShopExecution shopExecution = shopService
					.getByEmployeeId(employeeId);
			list = shopExecution.getShopList();
			modelMap.put("shopList", list);
			modelMap.put("user", user);
			modelMap.put("success", true);
			// 鍒楀嚭搴楅摵鎴愬姛涔嬪悗锛屽皢搴楅摵鏀惧叆session涓綔涓烘潈闄愰獙璇佷緷鎹紝鍗宠甯愬彿鍙兘鎿嶄綔瀹冭嚜宸辩殑搴楅摵
			request.getSession().setAttribute("shopList", list);*/
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId>-1) {
			try {
				Shop shop=shopService.getByshopId(shopId);
				List<Area> areaList=areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList",areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				modelMap.put("success", false);
				modelMap.put("errmsg",e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errmsg","empty shopId");
		}
		return modelMap;
		
	}
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		 if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入错了验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = (Shop)request.getSession().getAttribute("currentShop");
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
			 
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//修改店铺信息
		if (shop != null && shop.getShopId()!=null) {
			try {
				
				ShopExecution se;
				try {
					if(shopImg==null) {
						se=shopService.modifyShop(shop, null);
					}else {ImageHoder image=new ImageHoder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				
					se = shopService.modifyShop(shop,image);
					}
					if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
						// 若shop创建成功，则加入session中，作为权限使用
						@SuppressWarnings("unchecked")
						List<Shop> shopList = (List<Shop>) request.getSession()
								.getAttribute("shopList");
						if (shopList != null && shopList.size() > 0) {
							shopList.add(se.getShop());
							request.getSession().setAttribute("shopList", shopList);
						} else {
							shopList = new ArrayList<Shop>();
							shopList.add(se.getShop());
							request.getSession().setAttribute("shopList", shopList);
						}
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", se.getStateinfo());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息id");
		}
		return modelMap;
	}
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		modelMap.put("areaList", areaList);
		modelMap.put("success", true);
		return modelMap;
	}
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		 if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入错了验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
			
			 
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shop != null && shopImg != null) {
			try {
				
			/*	PersonInfo user = (PersonInfo) request.getSession()
						.getAttribute("user");*/
				PersonInfo user=new PersonInfo();
				user.setUserId(1L);
				System.out.println(user);
				shop.setOwner(user);
				ShopExecution se;
				try {
					ImageHoder image=new ImageHoder(shopImg.getOriginalFilename(),shopImg.getInputStream());
				
					se = shopService.addShop(shop,image);
					System.out.println(se);
				
					if (se.getState() == ShopStateEnum.CHECK.getState()) {
						modelMap.put("success", true);
						// 若shop创建成功，则加入session中，作为权限使用
						@SuppressWarnings("unchecked")
						List<Shop> shopList = (List<Shop>) request.getSession()
								.getAttribute("shopList");
						if (shopList != null && shopList.size() > 0) {
							shopList.add(se.getShop());
							request.getSession().setAttribute("shopList", shopList);
						} else {
							//第一次创建店铺
							shopList = new ArrayList<Shop>();
							shopList.add(se.getShop());
							request.getSession().setAttribute("shopList", shopList);
						}
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", se.getStateinfo());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}


	
	/*private boolean hasAccountBind(HttpServletRequest request, long employeeId) {
		// TODO Auto-generated method stub
		return false;
	}
*/

}
