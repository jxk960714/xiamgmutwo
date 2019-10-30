package  com.jxk.oto.service;
import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import  com.jxk.oto.dto.ImageHoder;
import  com.jxk.oto.dto.ShopExecution;
import  com.jxk.oto.entity.Shop;

/**
 * @author 17122
 *
 */
public interface ShopService {
	
	/**根据shopCondition分页相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	ShopExecution addShop(Shop shop,ImageHoder image)throws RuntimeException;
	int insertShop(Shop shop);
	Shop getByshopId(Long shopId);
	ShopExecution modifyShop(Shop shop,ImageHoder image)throws RuntimeException;


}
