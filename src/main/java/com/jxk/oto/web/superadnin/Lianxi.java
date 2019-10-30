package  com.jxk.oto.web.superadnin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import  com.jxk.oto.entity.PersonInfo;
import  com.jxk.oto.entity.Shop;
import  com.jxk.oto.service.ShopService;

@RestController
@RequestMapping("/ajax")
public class Lianxi {
	@Autowired
	private ShopService shopService;
	@RequestMapping(value="/test", method = RequestMethod.GET)
	private Map<String,Object> lianxi(HttpServletRequest request) {
		Map<String,Object> list=new HashMap<String,Object>();
		list.put("name","jxk");
		list.put("age",20);
		list.put("success",true);
		return list;
	}
	 @RequestMapping("/fileUpload")
	    public Map<String,Object> handleFormUpload(@RequestParam("file")CommonsMultipartFile file,
	                                   HttpServletRequest request) {
		 String type=request.getParameter("type");
		 System.out.println(type);
		
		 System.out.println("----------Filename----------");
         System.out.println(file.getOriginalFilename());
		
		 
		 Map<String,Object> resultMap = new HashMap<String, Object>();
		 resultMap.put("success", true);
		 return resultMap;
		/* MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;  
		  MultipartFile file2 = mpRequest.getFile("file");
		  String name = file2.getOriginalFilename(); 
          System.out.println("----------articleFile-name----------");
          System.out.println(name);*/
	        //判断上传文件是否存在
	      /*  if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
	            //循环输出上传的文件
	            for (MultipartFile file : uploadfile) {
	                //获取上传文件的原始名称
	                String oringinalFilename = file.getOriginalFilename();
	                System.out.println("名称"+oringinalFilename);
	                //获取源文件名后缀
	                //String prefixName = FilenameUtils.getExtension(oringinalFilename);

	                //设置上传文件的保存地址目录
	                //String dirPath = request.getSession().getServletContext().getRealPath("/fileupload/");
	                Shop shop=new Shop();
	                //设置上传文件的保存地址目录
	                String dirPath = "E:/upload/";
	                File filePath = new File(dirPath);
	                //如果文件地址不存在 则创建目录
	                if (!filePath.exists()) {
	                    filePath.mkdirs();
	                }
	                //使用uuid重新命名上传的文件名称（上传人_uuid_原始文件名称）
	                String newFilename = UUID.randomUUID() + "_" + oringinalFilename;

	                try {
	                    //使用MultipartFilr接口的方法完成文件上传到指定位置
	                    file.transferTo(new File(dirPath + newFilename));
	                    shop.setShopImg(newFilename);
	                    PersonInfo owner=new PersonInfo();
	                    owner.setUserId(1L);
	                    shop.setOwner(owner);
	                    shop.setShopName("测试");
	                    int effectNum=shopService.insertShop(shop);	                    
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    return "error";
	                }
	            }
	            //跳转到成功页面
	            return "success";
	       } else {
	            return "error";
	        }*/
	    }
	

}