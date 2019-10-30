package  com.jxk.oto.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import  com.jxk.oto.dto.ImageHoder;
import  com.jxk.oto.dto.ProductExecution;
import  com.jxk.oto.entity.Product;

public interface ProductService {
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/** 
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	

	ProductExecution addProduct(Product product, ImageHoder thumbnail, 
			List<ImageHoder> productImgList)
			throws RuntimeException;

	/**
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws RuntimeException
	 */
	ProductExecution modifyProductProduct( Product product, ImageHoder thumbnail, 
	List<ImageHoder> productImgList) throws RuntimeException;
}
