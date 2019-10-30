package  com.jxk.oto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import  com.jxk.oto.dao.ProductCategoryDao;
import  com.jxk.oto.dto.ProductCategoryExecution;
import  com.jxk.oto.entity.ProductCategory;
import  com.jxk.oto.enums.ProductCategoryStateEnum;
import  com.jxk.oto.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Override
	public List<ProductCategory> queryProductCategory(Long shopId) {
		// TODO Auto-generated method stub
		return productCategoryDao.queryProductCategory(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchInsertProductCategorylist(List<ProductCategory> productCategoryList)
			throws RuntimeException {
		// TODO Auto-generated method stub
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao
						.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new RuntimeException("商品类别创建失败");
				} else {

					return new ProductCategoryExecution(
							ProductCategoryStateEnum.SUCCESS);
				}

			} catch (Exception e) {
				throw new RuntimeException("batchInsertProductCategory error: "
						+ e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(
					ProductCategoryStateEnum.INNER_ERROR);
		}

	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId) throws RuntimeException {
		// TODO Auto-generated method stub
		try {
			int effectNum=productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectNum<=0) {
				throw new RuntimeException("商品类别删除失败");
				
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("deleteProductCategory"+e.getMessage());
		}
	}
  
	

}
