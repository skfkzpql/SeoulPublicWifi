package dao;

import java.util.List;

import dto.WifiInfoDTO;
import dto.WifiInfoInputDTO;

public interface WifiInfoDAO {
    int insertWifiInfo();
    WifiInfoDTO selectWifiInfo(WifiInfoInputDTO wifiInfoInputDTO);
    List<WifiInfoDTO> select20NearestWifiInfo(WifiInfoInputDTO wifiInfoInputDTO);
}
