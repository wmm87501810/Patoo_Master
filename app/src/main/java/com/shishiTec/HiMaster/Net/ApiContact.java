package com.shishiTec.HiMaster.Net;

/**
 * Created by Pursue on 16/1/25.
 */
public class ApiContact {
//    public static final String BASE_DOMAIN = "http://kaifa.gomaster.cn/";
    //public static final String BASE_URL = "http://kaifa.gomaster.cn/rest/";
    public static final String IMAGE_URL = "http://kaifa.gomaster.cn/attms/";



    //测试环境(郭)
   public static final String BASE_URL ="http://10.0.16.233/patoo-BMS/webapp/rest/";

    public static final String WEB ="http://10.0.16.233/patoo-BMS/webapp/";
    public static final String IMAGE_URL2 ="http://10.0.16.233/patoo-BMS/webapp/attms/";

//    //正式环境
//    public static final String BASE_URL ="http://app1.ipatoo.cn/rest/";

//    用于发发送消息
    public static final String KEY_ZUOHUADELTER = "zuohua_delete";
    public static final String KEY_YOUHUAZAI = "youhua_zai";
    public static final String KEY_DELETEFRIEND = "delete_friend";
    public static final String KEY_DELETEFRIENDMSG = "delete_friendmsg";



    //发送验证码
    public static final String VERIFY_SMS = "index.php?c=iuser&a=verify_sms";
//    //用户注册
//    public static final String REGIST = "index.php?c=iuser&a=regist";
    //用户登录和注册
    public static final String LOGIN = "index.php?c=iuser&a=loginOrRegister";
    //用户登录和注册
    public static final String AUTOLOGIN = "index.php?c=iuser&a=by_security_code_login";
    //设置隐私密码
    public static final String SECURITYCODE = "index.php?c=iuser&a=setSecurityCode";
    //设置性别
    public static final String SETSEX = "index.php?c=iuser&a=check_sex";
    //个人中心
    public static final String GETMYCENTER = "index.php?c=icenter&a=get_personal_detail";
    //发现（用户列表）
    public static final String GETUSERLISETINFO = "index.php?c=ifind&a=get_user_list";
    //发现（用户详情）
    public static final String GETUSERDETAILINFO = "index.php?c=ifind&a=get_character_detail";
    //发现（好友LIST）
    public static final String GETFRIENDS= "index.php?c=idiscover&a=get_friend_list";
    //发现（删除好友消息LIST）
    public static final String DELETEFRIEND= "index.php?c=idiscover&a=delete_friend";
    //发现（删除好友消息LIST）
    public static final String DELETERFMSG= "index.php?c=ifind&a=delete_message";
    //发消息）
    public static final String POSTMSG= "index.php?c=idiscover&a=send_message";
    //发现(聊天记录）
    public static final String GETCHATINFO= "index.php?c=idiscover&a=get_chat";
    //发现（好友消息LIST）
    public static final String GETMSG= "index.php?c=ifind&a=get_latest_news";
    //发现（点赞）
    public static final String POSTZAI= "index.php?c=idiscover&a=update_likes";
    //我的PAWU（）
    public static final String GETPAWUDEVICE = "index.php?c=idevice&a=list_device";
    //我的PAWU（bind）
    public static final String BINDPAWUDEVICE = "index.php?c=idevice&a=add_device";
    //我的PAWU（unbind）
    public static final String UNBINDPAWUDEVICE = "index.php?c=idevice&a=remove_device";
    //我的PAWU（unbind）
    public static final String SHEQUCARD = "index.php?c=iposts&a=lists";

    //退出登录
    public static final String EXIT = "index.php?c=iuser&a=logout";
    //第三方登录
    public static final String THIRDLOGIN = "index.php?c=iuser&a=login_third_party";
    //修改密码
    public static final String RESETPASSWORD = "index.php?c=iuser&a=reset_pwd";
    //找回密码
    public static final String FINDPASSWORD = "index.php?c=iuser&a=find_password";
    //个人主页粉丝
    public static final String MYFANS = "index.php?c=iuser&a=my_fans";
    //个人主页关注
    public static final String MYFOLLOWS = "index.php?c=iuser&a=my_follows";
    //个人主页
    public static final String USERCENTER = "index.php?c=iuser&a=user_center";
    //用户关注
    public static final String ATTENUSER = "index.php?c=iuser&a=atten_user";
    //取消关注
    public static final String CANCLEATTENUSER = "index.php?c=iuser&a=cancel_atten_user";
    //客户端启动配置
    public static final String CLIENTCONFIG = "index.php?c=ipublic&a=init_conf";
    //添加自定义标签
    public static final String CUSTOMTAG = "index.php?c=ishare&a=tag_add";
    //达人分享点赞
    public static final String MASTERLIKE = "index.php?c=ishare&a=master_like";
    //取消达人分享点赞
    public static final String CANCELMASTERLIKE = "index.php?c=ishare&a=cancel_master_like";
    //达人首页数据初始化
    public static final String MASTERSTART = "index.php?c=ishare&a=master_start";
    //达人分享列表
    public static final String MASTERLIST = "index.php?c=ishare&a=master_list";
    //达人标签分享列表
    public static final String MASTERLISTTAG = "index.php?c=ishare&a=master_list_tag";
    //达人分享详情
    public static final String MASTERDETAIL = "index.php?c=ishare&a=master_detail";
    //达人分享发布
    public static final String MASTERADD = "index.php?c=ishare&a=master_add";
    //关键字搜索
    public static final String TAGLISTBYKEYWORDS = "index.php?c=ishare&a=tag_list_by_keywords";
    //达人获取课程列表
    public static final String GETMASTERCOURSES = "index.php?c=iuser&a=get_master_courses";
    //获取系统通知列表
    public static final String GETSYSTEMINFORM = "index.php?c=iuser&a=get_system_inform";
    //获得评论及回复列表
    public static final String GETCOMMENTLIST = "index.php?c=iuser&a=get_comment_list";
    //获得私信列表
    public static final String GETPRIVATENEWSLIST = "index.php?c=iuser&a=get_private_news_list";
    //获得私信内容详情
    public static final String GETPRIVATENEWSDETAIL = "index.php?c=iuser&a=get_private_news_detail";
    //获取达人订单列表
    public static final String GETMASTERORDERS = "index.php?c=iuser&a=get_master_orders";


    public static final String BIGMANLIST = "index.php?c=ishare&a=user_list";
    //牛人分享点赞接口
    public static final String BIGMANZAMBIA = "index.php?c=ishare&a=user_like";
    //牛人分享取消点赞接口
    public static final String BIGMANCANCLEZAMBIA = "index.php?c=ishare&a=cancel_user_like";
    //牛人分享详情接口
    public static final String BIGMANSHAREDETAIL = "index.php?c=ishare&a=user_detail";
    //牛人分享详情关注接口
    public static final String BIGMANDETAILFOLLOW = "index.php?c=iuser&a=atten_user";
    //牛人分享详情取消关注接口
    public static final String BIGMANDETAILCANCLEFOLLOW = "index.php?c=iuser&a=cancel_atten_user";
    //牛人详情评论列表
    public static final String BIGMANDCOMMENT = "index.php?c=ishare&a=user_comment_list";
    //牛人评论回复
    public static final String COMMENTREPLY = "index.php?c=ishare&a=user_comment_reply";
    //M点流水明细
    public static final String SCOREDETAIL = "index.php?c=iuser&a=get_score_detail";
    //代金券
    public static final String COUPON = "index.php?c=iuser&a=get_coupon";
    //酱油卡
    public static final String SOYCARD = "index.php?c=iuser&a=get_card";
    //个人分享
    public static final String MYSHARE = "index.php?c=iuser&a=get_share";
    //购课订单
    public static final String MYORDER = "index.php?c=iuser&a=get_order_list";
    //达人课程列表
    public static final String MASTERCOURSE = "index.php?c=iuser&a=get_collects";
    //个人中心收藏
    public static final String USERCENTERCOLLECT = "index.php?c=iuser&a=get_collects";
    //牛人提交评论
    public static final String BIGMANCOMMITCOMMENT = "index.php?c=ishare&a=user_comment_add";
    //牛人提交评论
    public static final String MASTERCOMMITCOMMENT = "index.php?c=ishare&a=master_comment_add";
    //分享搜索接口
    public static final String SEARCHBYKEYWORD = "index.php?c=ishare&a=search_by_keywords";
    //查询课程列表
    public static final String SEARCHBYKEYWORDCOURSE = "index.php?c=icourse&a=search_by_keywords";

    //意见反馈
    public static final String ADDSUGGEST = "index.php?c=iuser&a=add_suggest";

    //单图上传
    public static final String UPLOADIMAGE = "index.php?c=ipublic&a=upload";
    //修改用户资料
    public static final String MODIFYUSERINFO = "index.php?c=iuser&a=edit_user_info";
    //达人评论回复
    public static final String MASTERCOMMENTREPLY = "index.php?c=ishare&a=master_comment_reply";
    //达人人详情评论列表
    public static final String MASTERCOMMENT = "index.php?c=ishare&a=master_comment_list";
    //M点支付课程列表
    public static final String MPAYCOURSE = "index.php?c=icourse&a=mpoints_list";
    //更改订单状态
    public static final String UPDATEORDERSTATUS = "index.php?c=iuser&a=update_order_status";
    //删除订单
    public static final String DELETEORDER = "index.php?c=iuser&a=delete_order";
    //多图上传
    public static final String MULTIUPLOAD = "index.php?c=ipublic&a=multi_upload";
    //删除订单
    public static final String USERADD = "index.php?c=ishare&a=user_add";
    //删除订单
    public static final String MALLSTART = "index.php?c=icourse&a=course_start";
    //删除订单
    public static final String CATEGORYLIST = "index.php?c=icourse&a=category_list";
    //课程详情
    public static final String COURSEDETAIL = "index.php?c=icourse&a=detail";
    //客户端启动配置接口
    public static final String INITCONF = "index.php?c=ipublic&a=init_conf";
    //根据关键字查询课程列表
    public static final String SEARCHBYKEYWORDS = "index.php?c=icourse&a=search_by_keywords";
    //下单确认
    public static final String PAYSURE = "index.php?c=icourse&a=pay_info";
    //课程收藏
    public static final String COLLECT = "index.php?c=icourse&a=collect";
    //课程取消收藏
    public static final String CANCELCOLLECT = "index.php?c=icourse&a=cancel_collect";
    //日历数据获取
    public static final String CALENDAR = "index.php?c=icourse&a=time_info";
    //日历数据获取
    public static final String BUYSURE = "index.php?c=icourse&a=buy_course";
    //日历数据获取
    public static final String CARDDEATIL = "index.php?c=icard&a=detail";
    //获得用户个人主页
    public static final String USEROTHER = "index.php?c=iuser&a=user_pages";
    //TA们也想学
    public static final String OTHERSTUDY = "index.php?c=icourse&a=student_list";
    //用户点赞
    public static final String USERCANCELZAMBIA = "index.php?c=iuser&a=user_like";
    //用户取消点赞
    public static final String USERZAMBIA = "index.php?c=iuser&a=cancel_user_like";
    //获取个人订单列表
    public static final String ORDERLIST = "index.php?c=iuser&a=get_order_list";
    //获取我的收藏列表
    public static final String COLLECTSLIST = "index.php?c=iuser&a=get_collects";
    //兑换码接口
    public static final String CODEEXCHANGE = "index.php?c=iuser&a=code_exchange";
    //酱油卡下单确认
    public static final String CARDORDER = "index.php?c=icard&a=pay_info";
    //酱油卡下单
    public static final String PAYCARD = "index.php?c=icard&a=buy_card";
    //购买酱油卡列表
    public static final String CARDLISTS = "index.php?c=icard&a=lists";
}
