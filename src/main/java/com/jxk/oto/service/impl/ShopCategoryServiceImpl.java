package  com.jxk.oto.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  com.jxk.oto.dao.ShopCategoryDao;
import  com.jxk.oto.entity.ShopCategory;
import  com.jxk.oto.service.ShopCategoryService;
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
  @Autowired ShopCategoryDao shopCategoryDao;
	@Override
	public List<ShopCategory> getFirstLevelShopCategoryList() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) throws IOException {
		// TODO Auto-generated method stub
		 return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

	@Override
	public List<ShopCategory> getAllSecondLevelShopCategory() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShopCategory getShopCategoryById(Long shopCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
