package  com.jxk.oto.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.jxk.oto.dao.ShopDao;
import  com.jxk.oto.dto.ImageHoder;
import  com.jxk.oto.dto.ShopExecution;
import  com.jxk.oto.entity.Shop;
import  com.jxk.oto.enums.ShopStateEnum;
import  com.jxk.oto.service.ShopService;
import  com.jxk.oto.util.FileUtil;
import  com.jxk.oto.util.ImageUtil;
import  com.jxk.oto.util.PageCalculator;
@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
     /**
	 * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */
	public ShopExecution addShop(Shop shop, ImageHoder image)
			throws RuntimeException {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new RuntimeException("店铺创建失败");
			} else {
				if (image.getImage() != null) {
					try {
						addShopImg(shop, image);
						
					}catch(Exception e){
						throw new RuntimeException("addShopImg error: "
								+ e.getMessage());
					}
						effectedNum = shopDao.updateShop(shop);
						if (effectedNum <= 0) {
							throw new RuntimeException("新图片地址失败");
						}
					}
				} 
		}catch (Exception e) {
					throw new RuntimeException("addShopImg error: "
							+ e.getMessage());
				}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
		
	}
	private void addShopImg(Shop shop,ImageHoder image) {
		String dest = FileUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(image, dest);
		System.out.println(shopImgAddr);
		shop.setShopImg(shopImgAddr);
	}
	@Override
	public Shop getByshopId(Long shopId) {
		// TODO Auto-generated method stub
		return shopDao.queryByShopId(shopId);
	}
	@Override
	public ShopExecution modifyShop(Shop shop,ImageHoder image) throws RuntimeException {
		// TODO Auto-generated method stub
		if(shop==null||shop.getShopId()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			//1判断是否需要图片修改
			try {
			if(image.getImage()!=null&&image.getImageName()!=null&&"".equals(image.getImageName())) {
				Shop tempShop=shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					FileUtil.deleteFile(tempShop.getShopImg());
					
				}
				addShopImg(shop, image);
			}
			
			//2更新图片信息
			shop.setLastEditTime(new Date());
			int effectnum=shopDao.updateShop(shop);
			if(effectnum<=0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
				
			}else {
				shop=shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}}catch (Exception e) {
				throw new RuntimeException("modifyShop"+e.getMessage());
			}
			
		}
		
	}
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex,
				pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		} else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;

	}
	@Override
	public int insertShop(Shop shop) {
		// TODO Auto-generated method stub
		return shopDao.insertShop(shop);
	}


}
