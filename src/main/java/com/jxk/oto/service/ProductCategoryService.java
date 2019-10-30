package  com.jxk.oto.service;

import java.util.List;

import  com.jxk.oto.dto.ProductCategoryExecution;
import  com.jxk.oto.entity.ProductCategory;

public interface ProductCategoryService {
	List<ProductCategory> queryProductCategory(Long shopId);
	ProductCategoryExecution  batchInsertProductCategorylist(List<ProductCategory>productCategoryList)
	throws RuntimeException;
	ProductCategoryExecution deleteProductCategory(Long productCategoryId,Long shopId)throws RuntimeException;

}
