package com.starvincci.JIT.Sqlserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starvincci.JIT.Sqlserver.mapper.MmlReqAMapper;
import com.starvincci.JIT.pojo.MmlReqA;

@Service
public class MpsBookServiceImpl  {

	@Autowired
	private  MmlReqAMapper mmlA;
	

	public List<Integer> selectmpsBookByBtID(Integer id) {
		// TODO Auto-generated method stub
		return mmlA.selectmpsBookByBtID(id);
	}


	public List<MmlReqA> selectBtNO(MmlReqA mmlReqA) {
		// TODO Auto-generated method stub
		return mmlA.selectBtNO(mmlReqA);
	}

}
