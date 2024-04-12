package vo;

import java.util.List;

import lombok.Value;

@Value
public class WifiInfoApiResponseVO {
	private TbPublicWifiInfo TbPublicWifiInfo;

	@Value
    public static class TbPublicWifiInfo {
        private int list_total_count;
        private Result RESULT;
        private List<WifiInfoVO> row;

        @Value
        public static class Result {
            private String CODE;
            private String MESSAGE;
        }

    }
}
