package  com.jxk.oto.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import  com.jxk.oto.entity.*;

/**
 * @author 17122
 *
 */
public interface ProductCategoryDao {
	
	/**通过shopid查询对应店铺的商品类型
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategory(Long shopId);
	/**批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory>productCategoryList);
	/**删除商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") Long productCategoryId,@Param("shopId") Long shopId);

}
