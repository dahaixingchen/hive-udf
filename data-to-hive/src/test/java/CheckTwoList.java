import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import sun.security.util.Length;

/**
 * @ClassName: CheckTwoList
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/3/2 15:50
 * @Version 1.0
 **/
public class CheckTwoList {
    public static void main(String[] args) {
        String [] s = {"ORG","ORDER_ID","CHANNEL_ID","LOG_KV","ACCT_NBR","ACCT_TYPE","COMMAND_TYPE","ORDER_BRIEF","ORDER_STATUS","ORDER_TIME","ORBER_FAIL_TIME","MER_ID","MER_NAME","PRODUCT_TYPE","TXN_TYPE","TXN_AMT","CURRENCY","OPEN_BANK_ID","OPEN_BANK","CARD_TYPE","CARD_NO","USR_NAME","CERT_TYPE","CERT_ID","PURPOSE","PRIV1","STATE","CITY","SUB_BANK","FLAG","GUARANTY_ID","STATUS","CODE","MESSAGE","DUE_BILL_NO","BUSINESS_DATE","SEND_TIME","ORI_ORDER_ID","SETUP_DATE","OPT_DATETIME","LOAN_USAGE","PAY_CHANNEL_ID","PAY_BIZ_CODE","SUCCESS_AMT","FAILURE_AMT","ONLINE_FLAG","REQUEST_TIME","SERVICESN","AUTH_TXN_TERMINAL","ACQ_ID","MATCH_IND","COMPARED_IND","ERR_IND","MEMO","CREATE_TIME","CREATE_USER","LST_UPD_TIME","LST_UPD_USER","SUB_TERMINAL_TYPE","CONTR_NBR","MOBILE_NUMBER","REF_NBR","CONTACT_CHNL","SERVICE_ID","MERCHANDISE_ORDER","RESPONSE_CODE","RESPONSE_MESSAGE","TERMINAL_DEVICE","BRANCH_NO","OLD_CARD_NO","COINST_CODE","COINST_CHANNEL","BANK_SETTLE_ACCT_NO","ORIGINAL_TXN_AMT","ONLINE_ALLOW","MERCH_PAY_INTEREST","PAY_MODE","ACQ_ACCEPTOR_ID","CAPITAL_SIDE_ID","COOPERATOR_ID","JPA_VERSION","RESERVE_FIELD1","RESERVE_FIELD2","RESERVE_FIELD3","RESERVE_FIELD4","PAYMENT_IDS","PAY_SEQ_NO"};
        String [] s1 = {"org",
                "order_id",
                "channel_id",
                "log_kv",
                "acct_nbr",
                "acct_type",
                "command_type",
                "order_brief",
                "order_status",
                "order_time",
                "orber_fail_time",
                "mer_id",
                "mer_name",
                "product_type",
                "txn_type",
                "txn_amt",
                "currency",
                "open_bank_id",
                "open_bank",
                "card_type",
                "card_no",
                "usr_name",
                "cert_type",
                "cert_id",
                "purpose",
                "priv1",
                "state",
                "city",
                "sub_bank",
                "flag",
                "guaranty_id",
                "status",
                "code",
                "message",
                "due_bill_no",
                "business_date",
                "send_time",
                "ori_order_id",
                "setup_date",
                "opt_datetime",
                "loan_usage",
                "pay_channel_id",
                "pay_biz_code",
                "success_amt",
                "failure_amt",
                "online_flag",
                "request_time",
                "servicesn",
                "auth_txn_terminal",
                "acq_id",
                "match_ind",
                "compared_ind",
                "err_ind",
                "memo",
                "create_time",
                "create_user",
                "lst_upd_time",
                "lst_upd_user",
                "sub_terminal_type",
                "contr_nbr",
                "mobile_number",
                "ref_nbr",
                "contact_chnl",
                "service_id",
                "merchandise_order",
                "response_code",
                "response_message",
                "terminal_device",
                "branch_no",
                "old_card_no",
                "coinst_code",
                "coinst_channel",
                "bank_settle_acct_no",
                "original_txn_amt",
                "online_allow",
                "merch_pay_interest",
                "pay_mode",
                "acq_acceptor_id",
                "capital_side_id",
                "cooperator_id",
                "jpa_version",
                "reserve_field1",
                "reserve_field2",
                "reserve_field3",
                "reserve_field4",
                "payment_ids",
                "pay_seq_no",
                "dw_created_time",
                "dw_created_by",
        };
        for (int i = 0;i<s.length;i++){
            int j=0;
            for (int k = 0;k<s1.length;k++){
                j++;
                if (s[i].toLowerCase().equals(s1[k].toLowerCase())){
                    break;
                }
                if (j >= s1.length){
                    System.out.println("hive字段");
                    System.out.println(s[i]);
                    System.out.println(s.length+"---"+s1.length);
//                    System.out.println("文件字段");
//                    System.out.println(s1[k]);
                }
            }
        }
    }

    @Test
    public void test(){
        String tmpStr= "9982|+|5580677|+|BANK|+||+|14131|+|E|+|BDB|+||+|E|+|20200301040502|+||+||+||+||+|AgentCredit|+|21291.48|+|156|+|0303|+|0303|+|0|+|6226630402757545|+|尹金国|+|I|+|370602196909043472|+|逾期扣款|+|20200301043557|+||+||+|000000001|+|00|+||+||+|1001|+|厦信扣款处理失败:30|失败! 付款账户单笔交易金额超过行限制。|+|20181207130000047814|+|20200229|+|20200301000000|+||+|20200301|+|20200301000000|+|O|+||+||+|0.00|+|21291.48|+|N|+||+||+||+|50000007|+|Y|+|N|+||+||+|20200301040502|+||+|20200301060223|+||+||+|20181207130000047814|+|18675511008|+|20181207130000047814|+||+||+||+||+||+||+|998200060002|+||+||+||+||+||+|N|+||+||+||+|30010|+|50000007|+|3|+|20200301043558836198|+|20200301043558466063|+|settleFlag|+||+||+|558067720200301043558836198";
//        tmpStr = "9982|+|5577460|+|BANK|+||+|16017|+|E|+|SDB|+||+|S|+|20200229123710|+||+||+||+||+|AgentCredit|+|16944.88|+|156|+|0102|+|0102|+|0|+|6222023602004162004|+|唐巧玲|+|I|+|320919197411053226|+|逾期扣款|+||+||+||+|000000001|+|00|+||+||+|0000|+|交易成功|+|20190111150000050687|+|20200229|+|20200229000000|+||+|20200229|+|20200301000000|+|O|+|1|+|0|+|0.00|+|16944.88|+|Y|+|20200229123356|+|APP20200229123356000152|+||+|50000014|+|Y|+|N|+||+||+|20200229123710|+||+|20200301094441|+||+|Android|+|20190111150000050687|+|13711162900|+|20190111150000050687|+||+|TFCRepay|+||+||+||+||+|998200060001|+||+||+||+||+||+||+||+||+||+|30009|+|50000014|+|4|+|20200229123710082090|+|20200229123710942370|+||+||+||+|557746020200229123710082090";
        tmpStr = "9982|+|5551173|+|BANK|+||+|22729|+|E|+|SDB|+||+|E|+|20200226133955|+||+||+||+||+|AgentCredit|+|26391.76|+|156|+|0102|+|0102|+|0|+|6212264402016169108|+|李天斌|+|I|+|510822197303243514|+|逾期扣款|+||+||+||+|000000001|+|00|+||+||+|1001|+|厦信扣款处理失败:40|失败! 卡上的余额不足【1000051】|+|20190809150000063944|+|20200226|+|20200226|+||+|20200226|+|20200226000000|+|O|+|0|+|1|+|0.00|+|26391.76|+|Y|+|20200226133954|+|APP20200257130223565213|+|60000000|+||+|Y|+|N|+||+||+|20200301033003|+||+|20200301033003|+||+|Android|+|20190809150000063944|+|13880798762|+|20190809150000063944|+||+|TFCRepay|+||+||+||+||+|998200040003|+||+||+||+||+||+||+|30010|+|3|+|20200226133955767148|+|20200226133955291773|+||+||+|555117320200226133955767148";
        tmpStr = "9982|+|24255439|+||+|b58f1f5c-1b82-474e-b505-bfe61b57cb37|+|156|+|14379|+|G|+|3|+||+|M|+|G513|+|两呆credit grade 6+还款|+|9130|+|20181211190000095004|+|20190526204921038113|+|20200229|+|144.54|+|Interest|+|N|+||+|000000001|+||+|20200301034442|+||+|20200301034442|+||+|S|+|N|+|0|+|10001";
        tmpStr = StringUtils.replace(tmpStr,"|+|","\001 ");
        String[] split1 = tmpStr.split("\001");
        System.out.println(split1.length);
    }
}
