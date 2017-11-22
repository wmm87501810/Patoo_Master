package com.shishiTec.HiMaster.Net;

import com.shishiTec.HiMaster.Model.bean.ClientStartBean;
import com.shishiTec.HiMaster.Model.bean.FansAndFollowBean;
import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.Model.bean.RegisterAndLoginBean;
import com.shishiTec.HiMaster.Model.bean.UserCenterBean;
import com.shishiTec.HiMaster.Model.bean.VerifyBean;
import com.shishiTec.HiMaster.Model.bean.*;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.realbean.AutoLoginBean;
import com.shishiTec.HiMaster.Model.realbean.ChatInfoBean;
import com.shishiTec.HiMaster.Model.realbean.MyCenterBean;
import com.shishiTec.HiMaster.Model.realbean.MyFriendBean;
import com.shishiTec.HiMaster.Model.realbean.MyFriendMsgBean;
import com.shishiTec.HiMaster.Model.realbean.MyShequBean;
import com.shishiTec.HiMaster.Model.realbean.PawuDeviceBean;
import com.shishiTec.HiMaster.Model.realbean.SwipeBean;
import com.shishiTec.HiMaster.Model.realbean.UserdetailsInfo;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Pursue on 16/1/5.
 */
public interface ApiService {

    //发送验证码
    @POST(ApiContact.VERIFY_SMS)
    Observable<BaseModel<VerifyBean>> getVerifyCode(@Body BaseParams verifyParams);

    //注册
//    @POST(ApiContact.REGIST)
//    Observable<BaseModel<RegisterAndLoginBean>> Regist(@Body BaseParams registerParams);

    //-------------------------登录------------------------------------------
    @POST(ApiContact.LOGIN)
    Observable<BaseModel<RegisterAndLoginBean>> Login(@Body BaseParams loginParams);
    //自动登录
    @POST(ApiContact.AUTOLOGIN)
    Observable<BaseModel<AutoLoginBean>> autoLogin(@Body BaseParams autologinParams);
    // 设置安全密码
    @POST(ApiContact.SECURITYCODE)
    Observable<BaseModel<VerifyBean>> getSecurityCode(@Body BaseParams verifyParams);
    // 设置性别
    @POST(ApiContact.SETSEX)
    Observable<BaseModel<VerifyBean>> getSex(@Body BaseParams verifyParams);
    //-------------------------个人中心------------------------------------------
    @POST(ApiContact.GETMYCENTER)
    Observable<BaseModel<MyCenterBean>> getMyCenter(@Body BaseParams MyCenterBean);
    //------------------------发现-----------------------------------------------
    @POST(ApiContact.GETUSERLISETINFO)//用户列表
    Observable<BaseModel<List<SwipeBean>>> getUserlistinfo(@Body BaseParams useListParms);
    @POST(ApiContact.GETUSERDETAILINFO)//用户列表
    Observable<BaseModel<UserdetailsInfo>> getUserDetailinfo(@Body BaseParams userDetailParms);
    @POST(ApiContact.GETFRIENDS)//好友列表
    Observable<BaseModel<List<MyFriendBean>>> getFriends(@Body BaseParams friendsParms);
    @POST(ApiContact.GETMSG)//好友笑消息列表
    Observable<BaseModel<List<MyFriendMsgBean>>> getFriendsMsg(@Body BaseParams friendmsgParms);
    @POST(ApiContact.DELETEFRIEND)//好友笑消息列表
    Observable<BaseModel<MyFriendBean>> delete_friend(@Body BaseParams friendmsgParms);
    @POST(ApiContact.DELETERFMSG)//好友笑消息列表
    Observable<BaseModel<MyFriendMsgBean>> delete_friendmsg(@Body BaseParams friendmsgParms);
    @POST(ApiContact.POSTMSG)//发消息
    Observable<BaseModel<List<MyFriendMsgBean>>> post_msg(@Body BaseParams friendmsgParms);
    @POST(ApiContact.GETCHATINFO)//好友聊天列表
    Observable<BaseModel<List<ChatInfoBean>>> get_chatinfo(@Body BaseParams friendmsgParms);
    @POST(ApiContact.POSTZAI)//好友聊天列表
    Observable<BaseModel<List<ChatInfoBean>>> post_zai(@Body BaseParams friendmsgParms);
    //------------------------------PAPAWU-----------------------------------------------
    @POST(ApiContact.GETPAWUDEVICE)//用户列表
    Observable<BaseModel<List<PawuDeviceBean>>> getPawuDevice(@Body BaseParams pawudeviceBeanListParms);
    @POST(ApiContact.BINDPAWUDEVICE)//用户列表
    Observable<BaseModel<PawuDeviceBean>> bindPawuDevice(@Body BaseParams pawudeviceBeanListParms);
    @POST(ApiContact.UNBINDPAWUDEVICE)//用户列表
    Observable<BaseModel<PawuDeviceBean>> unbindPawuDevice(@Body BaseParams pawudeviceBeanListParms);
    //--------------------------------------shequ-----------------------------------------------------
    //酱油卡
    @POST(ApiContact.SHEQUCARD)
    Observable<BaseModel<List<MyShequBean>>> getShequ(@Body BaseParams cardParams);

    //第三发登录
    @POST(ApiContact.THIRDLOGIN)
    Observable<BaseModel<RegisterAndLoginBean>> thirdLogin(@Body BaseParams thirdLoginParams);

    //修改密码
    @POST(ApiContact.RESETPASSWORD)
    Observable<BaseModel> resetPwd(@Body BaseParams resetPwdPrams);

    //找回密码
    @POST(ApiContact.FINDPASSWORD)
    Observable<BaseModel> findPwd(@Body BaseParams findPwdParams);

    //退出应用
    @POST(ApiContact.EXIT)
    Observable<BaseModel> exit(@Body BaseParams exitParams);

    //个人主页粉丝列表
    @POST(ApiContact.MYFANS)
    Observable<BaseModel<List<FansAndFollowBean>>> myFans(@Body BaseParams fansParams);

    //个人主页关注列表
    @POST(ApiContact.MYFOLLOWS)
    Observable<BaseModel<List<FansAndFollowBean>>> myFollows(@Body BaseParams followParams);

    //个人主页
    @POST(ApiContact.USERCENTER)
    Observable<BaseModel<UserCenterBean>> userCenter(@Body BaseParams userCenterParams);

    //用户关注
    @POST(ApiContact.ATTENUSER)
    Observable<BaseModel> attenUser(@Body BaseParams attenUserParams);

    //用户取消关注
    @POST(ApiContact.CANCLEATTENUSER)
    Observable<BaseModel> cancleAttenUser(@Body BaseParams cancleAttenUserParams);

    //启动配置
    @POST(ApiContact.CLIENTCONFIG)
    Observable<BaseModel<ClientStartBean>> clientConfig(@Body BaseParams clientConParams);

    //达人首页数据初始化
    @POST(ApiContact.MASTERSTART)
    Observable<BaseModel<MasterStartBean>> masterStart(@Body BaseParams masterStartParams);

    //达人分享列表
    @POST(ApiContact.MASTERLIST)
    Observable<BaseModel<MasterListsBean>> masterList(@Body BaseParams masterListParams);

    //获取达人订单列表
    @POST(ApiContact.GETMASTERORDERS)
    Observable<BaseModel<List<MasterOrderBean>>> getMasterOders(@Body BaseParams masterOrdersParams);

    //达人标签筛选分享列表
    @POST(ApiContact.MASTERLISTTAG)
    Observable<BaseModel<List<MasterListTagBean>>> masterListTag(@Body BaseParams BigManParams);

    //达人分享详情
    @POST(ApiContact.MASTERDETAIL)
    Observable<BaseModel<MasterDetailBean>> masterDetail(@Body BaseParams masterDetailParams);

    //达人分享点赞
    @POST(ApiContact.MASTERLIKE)
    Observable<BaseModel> masterLike(@Body BaseParams masterLikeParams);

    //意见反馈
    @POST(ApiContact.ADDSUGGEST)
    Observable<BaseModel> addSuggest(@Body BaseParams addSuggestParams);

    //取消达人分享点赞
    @POST(ApiContact.CANCELMASTERLIKE)
    Observable<BaseModel> cancelMasterLike(@Body BaseParams cancelMasterLike);

    //达人分享发布接口
    @POST(ApiContact.MASTERADD)
    Observable<BaseModel<MasterAddBean>> masterAdd(@Body BaseParams baseParams);

    //关键字搜索标签
    @POST(ApiContact.TAGLISTBYKEYWORDS)
    Observable<BaseModel> tgaList(@Body BaseParams tagListParams);

    //获取达人课程列表
    @POST(ApiContact.GETMASTERCOURSES)
    Observable<BaseModel<List<GetMasterCourseBean>>> getMasterCourses(@Body BaseParams getMasterCoursesParams);

    //获取系统通知列表
    @POST(ApiContact.GETSYSTEMINFORM)
    Observable<BaseModel<List<NotificationBean>>> getSystemInform(@Body BaseParams getSystemInFormParams);

    //获得评论及回复列表
    @POST(ApiContact.GETCOMMENTLIST)
    Observable<BaseModel<List<CommentListBean>>> getCommentList(@Body BaseParams getCommentList);

    //获得私信列表
    @POST(ApiContact.GETPRIVATENEWSLIST)
    Observable<BaseModel<List<PrivateNewsListBean>>> getPrivateNewsList(@Body BaseParams getPrivateNewsListParams);

    //获得私信内容详情
    @POST(ApiContact.GETPRIVATENEWSDETAIL)
    Observable<BaseModel<List<PrivateNewsListDetailBean>>> getPrivateNewsListDetail(@Body BaseParams getPrivateNewsListDetailParams);

    //牛人分享点赞接口
    @POST(ApiContact.BIGMANZAMBIA)
    Observable<BaseModel> BigManZambia(@Body BaseParams ZambiaParams);

    //牛人分享取消点赞接口
    @POST(ApiContact.BIGMANCANCLEZAMBIA)
    Observable<BaseModel> BigManCancle_Zambia(@Body BaseParams Cancle_ZambiaParams);

    //牛人分享列表
    @POST(ApiContact.BIGMANLIST)
    Observable<BaseModel<List<BigManBean>>> BigManContet(@Body BaseParams BigManParams);

    //牛人分享详情列表
    @POST(ApiContact.BIGMANSHAREDETAIL)
    Observable<BaseModel<ShareDetailBean>> BigManShartDetail(@Body BaseParams BigManShartDetailParams);

    //牛人详情关注
    @POST(ApiContact.BIGMANDETAILFOLLOW)
    Observable<BaseModel> bigManShartDetailFollow(@Body BaseParams BigManShartDetailFollowParams);

    //牛人详情取消关注
    @POST(ApiContact.BIGMANDETAILCANCLEFOLLOW)
    Observable<BaseModel> bigManDetailCancleFollow(@Body BaseParams BigManShartDetailCancleFollowParams);

    //牛人详情评论列表
    @POST(ApiContact.BIGMANDCOMMENT)
    Observable<BaseModel<List<bigManCommentBean>>> bigManComment(@Body BaseParams BigManCommentParams);

    //牛人详情提交评论
    @POST(ApiContact.BIGMANCOMMITCOMMENT)
    Observable<BaseModel> bigManCommitComment(@Body BaseParams BigManCommitCommentParams);

    //达人详情提交评论
    @POST(ApiContact.MASTERCOMMITCOMMENT)
    Observable<BaseModel> masterCommitComment(@Body BaseParams masterCommitCommentParams);

    //牛人分享评论回复
    @POST(ApiContact.COMMENTREPLY)
    Observable<BaseModel> commentReply(@Body BaseParams commentReplyParams);

    //达人分享评论回复
    @POST(ApiContact.MASTERCOMMENTREPLY)
    Observable<BaseModel> masterCommentReply(@Body BaseParams mastercommentReplyParams);

    //分享搜索接口
    @POST(ApiContact.SEARCHBYKEYWORD)
    Observable<BaseModel<List<MyShareBean>>> searchShare(@Body BaseParams searchShareParams);

    //课程搜索接口
    @POST(ApiContact.SEARCHBYKEYWORDCOURSE)
    Observable<BaseModel<List<GetMasterCourseBean>>> searchCourse(@Body BaseParams searchCourseParams);


    //获取M点明细
    @POST(ApiContact.SCOREDETAIL)
    Observable<BaseModel<ScoreDetailBean>> ScorceList(@Body BaseParams scoreParams);

    //代金券
    @POST(ApiContact.COUPON)
    Observable<BaseModel<List<CouponBean>>> getCoupon(@Body BaseParams couponParams);

    //酱油卡
    @POST(ApiContact.SOYCARD)
    Observable<BaseModel<List<SoyCardBean>>> getCard(@Body BaseParams cardParams);

    //个人分享列表
    @POST(ApiContact.MYSHARE)
    Observable<BaseModel<List<MyShareBean>>> queryShare(@Body BaseParams shareParams);

    //订单列表
    @POST(ApiContact.MYORDER)
    Observable<BaseModel<List<OrderBean>>> queryOrder(@Body BaseParams orderParams);

    //达人课程列表
    @POST(ApiContact.MASTERCOURSE)
    Observable<BaseModel<List<MasterCourseBean>>> queryMasterCourse(BaseParams masterCourseParams);

    //单图上传
    @POST(ApiContact.UPLOADIMAGE)
    Observable<BaseModel<UpLoadPictureBean>> upLoadPic(@Body BaseParams upLoadPicParams);

    //编辑用户资料
    @POST(ApiContact.MODIFYUSERINFO)
    Observable<BaseModel> modifyUserInfo(@Body BaseParams modifyParams);

    //达人详情评论列表
    @POST(ApiContact.MASTERCOMMENT)
    Observable<BaseModel<List<MasterCommentListBean>>> masterComment(@Body BaseParams masterCommentParams);

    //M点支付课程列表
    @POST(ApiContact.MPAYCOURSE)
    Observable<BaseModel<List<MCourseBean>>> queryMCourse(@Body BaseParams mCourseParams);

    //删除订单
    @POST(ApiContact.DELETEORDER)
    Observable<BaseModel> deleteOrder(@Body BaseParams deleteOrderParams);

    //更改订单状态
    @POST(ApiContact.UPDATEORDERSTATUS)
    Observable<BaseModel> updateOrderStatus(@Body BaseParams deleteOrderParams);

    //多图片上传
    @POST(ApiContact.MULTIUPLOAD)
    Observable<BaseModel<List<ImageListBean>>> multiUpload(@Body BaseParams multiUploadParams);

    //牛人发布分享
    @POST(ApiContact.USERADD)
    Observable<BaseModel<UserAddBean>> userAddUpload(@Body BaseParams UserAddParams);

    //商城列表专题
    @POST(ApiContact.CATEGORYLIST)
    Observable<BaseModel<SpecialBean>> mallSpecial(@Body BaseParams mallStartParams);

    //商城列表分类
    @POST(ApiContact.CATEGORYLIST)
    Observable<BaseModel<ClassBean>> mallClass(@Body BaseParams mallStartParams);

    //达人首页数据初始化
    @POST(ApiContact.MALLSTART)
    Observable<BaseModel<MallCategoryBean>> mallStart(@Body BaseParams mallStartParams);

    //课程详情
    @POST(ApiContact.COURSEDETAIL)
    Observable<BaseModel<CourseDetailBean>> courseDetail(@Body BaseParams courseDetailParams);

    //客户端启动配置接口
    @POST(ApiContact.INITCONF)
    Observable<BaseModel<InfoConfBean>> initConfDetail(@Body BaseParams initConfDetailParams);

    //根据关键字查询课程列表
    @POST(ApiContact.SEARCHBYKEYWORDS)
    Observable<BaseModel<List<SearchKeyWordsBean>>> searchByKeywords(@Body BaseParams searchByKeywordsParams);

    //下单确认
    @POST(ApiContact.PAYSURE)
    Observable<BaseModel<PaySureBean>> paySure(@Body BaseParams paySureParams);

    //课程收藏接口
    @POST(ApiContact.COLLECT)
    Observable<BaseModel> collect(@Body BaseParams collectParams);

    //课程取消收藏接口
    @POST(ApiContact.CANCELCOLLECT)
    Observable<BaseModel> cancelCollect(@Body BaseParams cancelCollectParams);

    //日历获取数据
    @POST(ApiContact.CALENDAR)
    Observable<BaseModel<List<CalendarBean>>> calendar(@Body BaseParams calendarParams);

    //日历获取数据
    @POST(ApiContact.BUYSURE)
    Observable<BaseModel<BuySureBean>> buySure(@Body BaseParams buySureParams);

    //酱油卡详情
    @POST(ApiContact.CARDDEATIL)
    Observable<BaseModel<CardDetailBean>> cardDetail(@Body BaseParams cardDetailParams);

    //获得用户个人主页
    @POST(ApiContact.USEROTHER)
    Observable<BaseModel<UserHomePageBean>> userOther(@Body BaseParams userOtherParams);

    //TA们也想学
    @POST(ApiContact.OTHERSTUDY)
    Observable<BaseModel<List<OtherStudyBean>>> otherStudy(@Body BaseParams otherStudyParams);

    //用户点赞
    @POST(ApiContact.USERZAMBIA)
    Observable<BaseModel> userZambia(@Body BaseParams userZambiaParams);

    //用户取消点赞
    @POST(ApiContact.USERCANCELZAMBIA)
    Observable<BaseModel> userCancelZambia(@Body BaseParams userCancelZambiaParams);

    //获取个人订单列表
    @POST(ApiContact.ORDERLIST)
    Observable<BaseModel<List<OrderListBean>>> orderList(@Body BaseParams orderListParams);

    //心愿单
    @POST(ApiContact.COLLECTSLIST)
    Observable<BaseModel<List<CollectsListBean>>> collectsList(@Body BaseParams collectsListParams);

    //兑换码接口
    @POST(ApiContact.CODEEXCHANGE)
    Observable<BaseModel> codeExchange(@Body BaseParams codeExchangeParams);

    //酱油卡下单确认
    @POST(ApiContact.CARDORDER)
    Observable<BaseModel<CardOrderBean>> cardOrder(@Body BaseParams cardOrderParams);

    //酱油卡下单
    @POST(ApiContact.PAYCARD)
    Observable<BaseModel<BuySureBean>> payCard(@Body BaseParams payCardParams);
    //酱油卡购买列表
    @POST(ApiContact.CARDLISTS)
    Observable<BaseModel<List<CardListsBean>>> cardLists(@Body BaseParams cardListsParams);
}

