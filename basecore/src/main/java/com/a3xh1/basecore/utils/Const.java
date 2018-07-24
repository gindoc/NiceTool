package com.a3xh1.basecore.utils;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.a3xh1.basecore.utils.Const.RONG.*;

/**
 * Author: GIndoc on 2017/4/9 20:42
 * email : 735506583@qq.com
 * FOR   :
 */

public class Const {
    public final static int VERSION = 1;

    public static class SharePreferenceKey {
        public static final String USER = "user";
        public static final String TOKEN = "token";
        public static final String LOGIN_STATE = "is_login";
        public static final String USER_ID = "user_id";
        public static final String USER_PHONE = "user_phone";
        public static final String LAT = "lat";
        public static final String LON = "lon";
        public static final String CURRENCY_UNIT = "currency_unit";
        public static final String IS_FIRST_IN = "IS_FIRST_IN";
    }

    public static class NORMAL {
        public static final int TYPE_BUY = 0x100;
        public static final int TYPE_SHOPPINGCAR = 0x101;
        public static final int TYPE_PAYPWD = 1;
        public static final int TYPE_LOGINPWD = 2;
        public static final int TYPE_IN = 1;
        public static final int TYPE_OUT = 0;
        public static final int ADD = 1;
        public static final int EDIT = 2;
        public static final int DELETE_ORDER = 3;
        public static final int CONFIRM_ORDER = 7;
        public static final int CONFIRM_WITHDRAW = 6;
        public static final int TYPE_COLLECT = 4;
        public static final int TYPE_PROD = 5;
        public static final int DELETE_ADDRESS = 11;
        public static final int DEFAULT_ADDRESS = 12;
    }

    public static class PAY {
        public static final int ALIPAY = 1;
        public static final int WX = 2;
        public static final int REST = 4;
        public static final int CASH = 3;
        public static final int PAY_PAL = 5;

        public static final String WX_APP_ID = "wxbb42a9d4feee0cbb";
        public static final String WX_APP_SECRET = "4b6ea172f52a537fc011670bdb863f28";

        public static final String QQ_APP_ID = "1106307878";
        public static final String QQ_APP_KEY = "llLKBULhbBDiocjM";
    }

    @StringDef({DEBUG_APP_KEY, APP_KEY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RONG {
        String APP_KEY = "p5tvi9dspn4r4";
        String DEBUG_APP_KEY = "";
    }


    public static class REQUEST {
        public static final int REQUEST_FOR_TAKE_FROM_CAMERA = 0x002;
        public static final int REQUEST_FOR_TAKE_FROM_ALBUM = 0x003;
        public static final int REQUEST_FOR_REGIST = 0x004;
        public static final int REQUEST_FOR_LOGIN = 0x005;
        public static final int REQUEST_FOR_RECHARGE=0x011;
        public static final int REQUEST_FOR_ADDOREDIT_ADDRESS = 0x006;
        public static final int REQUEST_FOR_ADDBANKCARD = 0x007;
        public static final int REQUEST_FOR_CHOOSEBANKCARD = 0x008;
        public static final int REQUEST_FOR_ADDRESS = 0x009;
        public static final int REQUEST_FOR_INITUSER = 0x010;

        public static final int REQUEST_FOR_BUSINESS_ENTER = 0x012;

        public static final int REQUEST_FOR_AGENT_APPLY = 0x011;
        public static final int REQUEST_FOR_CLUB_APPLY = 0x012;
        public static final int REQUEST_FOR_BUSINESS_APPLY = 0x013;
        public static final int REQUEST_FOR_CREATOR_APPLY = 0x014;
        public static final int REQUEST_FOR_SCAN_QRCODE = 0X015;
        public static final int REQUEST_FOR_SHOPPINGCAR_ORDER_DETAIL = 0x016;
        public static final int REQUEST_FOR_MODIFY_USER_MSG = 0x017;
        public static final int REQUEST_FOR_ACTIVE_MEMBER = 0x018;
        public static final int REQUEST_FOR_ACTIVE_BUSINESS = 0x019;
        public static final int REQUEST_FOR_ACTIVE_ClUB = 0x020;
        public static final int REQUEST_FOR_CHOOSE_ADDRESS = 0x021;
        public static final int REQUEST_FOR_LOCATION = 0x022;
        public static final int REQUEST_FOR_FORGET_LOGIN_PWD = 0x023;
        public static final String BUSINESS_RECV_FROM_HOME = "isRecvFromHomePage";
        public static final String MINE_QRCODE_FROM_HOME = "mineQrcodeFromHomePage";
    }

    public static class ROLE {
        public static final int MEMBER = 0;
        public static final int BUSINESS = 1;
        public static final int CLUB = 2;
        public static final int AGENT = 3;
        public static final int CREATOR = 4;
    }

    public static class KEY {
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String PRODUCT_CODE = "PRODUCT_CODE";
        public static final String KEYS = "keys";
        public static final String PAY_SUCCESS = "pay_success";
        public static final String ORDER_ID = "order_id";
        public static final String ORDER_CODE = "order_code";
        public static final String ORDER_DETAIL = "order_detail";
        public static final String PROD_CODE = "prod_code";
        public static final String KEYWORDS = "keywords";
        public static final String USER_PHONE = "user_phone";
        public static final String PARTNER_ID = "partner_id";
        public static final String PARTNER_NAME = "partner_name";
        public static final String ADDRESS = "address";
    }
}
