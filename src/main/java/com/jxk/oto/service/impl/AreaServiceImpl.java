package  com.jxk.oto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  com.jxk.oto.dao.AreaDao;
import  com.jxk.oto.entity.Area;
import  com.jxk.oto.service.AreaService;
@Service
public class AreaServiceImpl implements AreaService{
	@Autowired
	private AreaDao areadao;
	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		System.out.println(areadao.queryArea());
		return areadao.queryArea();
	}

}
