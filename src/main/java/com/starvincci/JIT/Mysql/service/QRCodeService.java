package com.starvincci.JIT.Mysql.service;

import com.starvincci.JIT.Mysql.mapper.QRCodeMapper;
import com.starvincci.JIT.pojo.WipDetails;
import com.starvincci.JIT.pojo.qrentity.Qrinfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QRCodeService {

    @Resource
    private QRCodeMapper qrCodeMapper;

    public Qrinfo checkqrOnly(Qrinfo qrinfo){
        return qrCodeMapper.checkqrOnly(qrinfo);
    }

    public int addqr(Qrinfo qrinfo){
        return qrCodeMapper.addqr(qrinfo);
    }

    public WipDetails checkwipdetails(String prdno,String facno){
        return qrCodeMapper.checkwipdetails(prdno,facno);
    }

    public int updateendtime1 (WipDetails wipDetails){
        return qrCodeMapper.updateendtime1(wipDetails);
    }
    public int updateendtime2 (WipDetails wipDetails){
        return qrCodeMapper.updateendtime2(wipDetails);
    }
    public int updateendtime3 (WipDetails wipDetails){
        return qrCodeMapper.updateendtime3(wipDetails);
    }
    public int updateendtime4 (WipDetails wipDetails){
        return qrCodeMapper.updateendtime4(wipDetails);
    }
}
