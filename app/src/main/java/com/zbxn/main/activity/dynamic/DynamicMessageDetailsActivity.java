package com.zbxn.main.activity.dynamic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.ConfigUtils;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.NoScrollListview;
import com.zbxn.R;
import com.zbxn.main.entity.DynamicMessageEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class DynamicMessageDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_userHead)
    ImageView ivUserHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.web_details)
    WebView webDetails;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.mCommentListView)
    NoScrollListview mCommentListView;
    private String id = "";
    private String url = "";
    private List<DynamicMessageEntity> commentList = new ArrayList<>();
    private CommentMessageAdapter mCommentAdapter;
    private DynamicMessageEntity InfoEntity;
    //0--没开始加载  1--加载中  2--加载完成
    private int mWebviewState = 0;
    private int pid;//回复某条
    private String replyMessage = "";//@ + xxx + :
    private boolean isReply;//是否是回复某条回复

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_message_details);
        ButterKnife.bind(this);
        setTitle("消息详情");
        id = getIntent().getStringExtra("id");
        GetUserMessageInfo();
        initView();

        initWeb();

    }

    private void initWeb() {
        mWebviewState = 1;
        //获取内容
        webDetails.loadUrl("javascript:getHTML()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //0--没开始加载  1--加载中  2--加载完成
                while (mWebviewState == 1) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mWebviewState = 1;
                handler.sendEmptyMessage(1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //0--没开始加载  1--加载中  2--加载完成
                while (mWebviewState == 1) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mWebviewState = 1;
                handler.sendEmptyMessage(3);
            }
        }).start();


    }

    private void initView() {
        mCommentAdapter = new CommentMessageAdapter(this, commentList);
        mCommentListView.setAdapter(mCommentAdapter);
        mCommentListView.setFocusable(false);
        mCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pid = commentList.get(position).getId();
                replyMessage = "@" + commentList.get(position).getUser().getRealName() + ":";
                etComment.setText(replyMessage);
                isReply = true;
                //重新定位光标位置
                Editable editable = etComment.getText();
                Spannable spanText = editable;
                Selection.setSelection(spanText, editable.length());
            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < replyMessage.length()) {
                    etComment.getText().clear();
                    replyMessage = "";
                    isReply = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tv_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_publish:
                if (isReply) {//回复某条回复
                    String message = StringUtils.getEditText(etComment).substring(replyMessage.length());
                    if (StringUtils.isEmpty(message)) {
                        MyToast.showToast("回复内容不能为空");
                        return;
                    }
                    replyMessage(message);
                } else {//回复消息
                    if (StringUtils.isEmpty(etComment)) {
                        MyToast.showToast("回复内容不能为空");
                        return;
                    }
                    pid = InfoEntity.getId();
                    replyMessage(StringUtils.getEditText(etComment));
                }

                break;
            default:
                break;
        }
    }


    //获取列表详情
    public void GetUserMessageInfo() {
        Call call = HttpRequest.getIResource().GetUserMessageInfo("8");
        callRequest(call, new HttpCallBack(DynamicMessageEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<DynamicMessageEntity> mList = (List<DynamicMessageEntity>) mResult.getDataList();
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).isRoot()) {
                            InfoEntity = mList.get(i);
                        } else {
                            commentList.add(mList.get(i));
                        }
                    }
                    pid = InfoEntity.getId();
                    mCommentAdapter.notifyDataSetChanged();//回复adapter刷新
                    tvTitle.setText(InfoEntity.getTitile());
                    tvTime.setText(InfoEntity.getCreationDateStr());
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                            //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                            .build();                                   // 创建配置过得DisplayImageOption对象
                    String server = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
//        if (entity.getUserIcon().contains("http")) {
                    ImageLoader.getInstance().displayImage(server + InfoEntity.getUser().getHeadImgUrl(), ivUserHead, options);
                    url = InfoEntity.getMessage();

                    Log.d("infomation", InfoEntity.toString());

                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://
//                    postUpdateSchedule();
                    break;
                case 3://
                    //设置内容
                    webDetails.loadUrl("javascript:setHTML('" + url + "')");
                    break;
            }
        }
    };

    private void replyMessage(String message) {
        Call call = HttpRequest.getIResource().ReplyMessage("", message, pid);
        callRequest(call, new HttpCallBack(DynamicMessageEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    etComment.getText().clear();
                    replyMessage = "";
                    GetUserMessageInfo();
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }
}
