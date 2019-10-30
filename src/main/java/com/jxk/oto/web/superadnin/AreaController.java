package  com.jxk.oto.web.superadnin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import  com.jxk.oto.service.AreaService;
import  com.jxk.oto.entity.*;

@RestController
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger=(Logger) LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private AreaService areaService;
	@RequestMapping(value = "/listArea", method = RequestMethod.GET)
	  public  Map<String, Object> listArea() {
		logger.info("====start====");
		long starttime=System.currentTimeMillis();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			System.out.println(list);
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error");
		long endtime=System.currentTimeMillis();
		
		logger.debug("costtime:[{}]ms",endtime-starttime);
		logger.info("====end===");

		return modelMap;
	}
	@RequestMapping("/login")
	public String jxk() {
		return "success";
	}
}
