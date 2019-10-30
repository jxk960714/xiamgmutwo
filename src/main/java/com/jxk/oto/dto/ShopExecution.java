package  com.jxk.oto.dto;

import java.util.List;

import  com.jxk.oto.entity.Shop;
import  com.jxk.oto.enums.ShopStateEnum;

public class ShopExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateinfo;
	//店铺数量
	private int count;
	//操作 数据库（增闪改查时候用到）
	private Shop shop;
	//shop列表（查询店铺列表时用到）
	private List<Shop> shopList;
	public ShopExecution() {
		
	}
	//店铺操作失败的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateinfo=stateEnum.getStateinfo();
		
	}
	//店铺操作成功的构造器
	public ShopExecution(ShopStateEnum stateEnum ,Shop shop) {
		this.state=stateEnum.getState();
		this.stateinfo=stateEnum.getStateinfo();
		this.shop=shop;
	}
	
	//店铺操作成功的构造器
	public ShopExecution(ShopStateEnum stateEnum ,List<Shop> shopList) {
		this.state=stateEnum.getState();
		this.stateinfo=stateEnum.getStateinfo();
		this.shopList=shopList;
	
	
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateinfo() {
		return stateinfo;
	}
	public void setStateinfo(String stateinfo) {
		this.stateinfo = stateinfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
}
