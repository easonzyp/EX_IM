package com.develop.ex_im_z.android.activity.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseActivity;
import com.develop.ex_im_z.android.fragment.FragmentChatList;
import com.develop.ex_im_z.android.fragment.FragmentFriendList;
import com.develop.ex_im_z.android.fragment.FragmentMy;
import com.nineoldandroids.view.ViewHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import imsdk.data.IMMyself;
import imsdk.data.relations.IMMyselfRelations;

public class ActivityHome extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_chat;  //聊天
    private RelativeLayout rl_friend;  //好友
    private RelativeLayout rl_home;  //家
    private LinearLayout ll_content;

    private ImageView iv_chat;
    private ImageView iv_friend;
    private ImageView iv_home;
    private TextView tv_chat;
    private TextView tv_friend;
    private TextView tv_home;
    private RelativeLayout[] relativeLayouts;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private static final int SELECT_CHAT = 0;
    private static final int SELECT_FRIEND = 1;
    private static final int SELECT_HOME = 2;
    private FragmentChatList fg_chat;
    private FragmentFriendList fg_friend;
    private FragmentMy fg_my;
    private DrawerLayout id_drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        initView();
        initListener();
        initRequest();
        initChat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class FirstEvent {

        private String mMsg;

        public FirstEvent(String msg) {
            mMsg = msg;
        }

        public String getMsg() {
            return mMsg;
        }
    }

    private void initRequest() {
        IMMyselfRelations.setOnRelationsEventListener(new IMMyselfRelations.OnRelationsEventListener() {
            @Override
            public void onInitialized() {
            }

            @Override
            public void onReceiveFriendRequest(String text, final String fromCustomUserID, long serverSendTime) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHome.this);
                builder.setTitle("'" + fromCustomUserID + "' 请求加你为好友")    //标题
                        .setCancelable(false)    //不响应back按钮
                        .setMessage(text)    //对话框显示内容
                        //设置按钮
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(DialogSampleActivity.this, "点击了确定按钮", Toast.LENGTH_SHORT).show();
                                IMMyselfRelations.agreeToFriendRequest(fromCustomUserID, 5, new IMMyself.OnActionListener() {
                                    @Override
                                    public void onSuccess() {
                                        // 回执发送成功
                                        Toast.makeText(ActivityHome.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        // 回执发送失败
                                        Toast.makeText(ActivityHome.this, "添加失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(DialogSampleActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                                IMMyselfRelations.rejectToFriendRequest(fromCustomUserID, fromCustomUserID, 5, new IMMyself.OnActionListener() {
                                    @Override
                                    public void onSuccess() {
                                        // 回执发送成功
                                        Toast.makeText(ActivityHome.this, "以拒绝", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        // 回执发送失败
                                        Toast.makeText(ActivityHome.this, "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).show();

            }

            @Override
            public void onReceiveAgreeToFriendRequest(String fromCustomUserID, long serverSendTime) {
                Toast.makeText(ActivityHome.this, "成功添加" + fromCustomUserID + "为好友", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceiveRejectToFriendRequest(String reason, String fromCustomUserID, long serverSendTime) {
                Toast.makeText(ActivityHome.this, fromCustomUserID + "拒绝添加你为好友", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBuildFriendshipWithUser(String customUserID, long serverSendTime) {
                Toast.makeText(ActivityHome.this, customUserID + "与您已是好友关系", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new FirstEvent("refresh"));
            }
        });
    }

    public void initView() {
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        rl_chat = (RelativeLayout) findViewById(R.id.rl_chat);
        rl_friend = (RelativeLayout) findViewById(R.id.rl_friend);
        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        iv_chat = (ImageView) findViewById(R.id.iv_chat);
        iv_friend = (ImageView) findViewById(R.id.iv_friend);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_friend = (TextView) findViewById(R.id.tv_friend);
        tv_home = (TextView) findViewById(R.id.tv_home);
        id_drawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        selectUI(SELECT_CHAT);
    }

    public void initListener() {
        rl_chat.setOnClickListener(this);
        rl_friend.setOnClickListener(this);
        rl_home.setOnClickListener(this);
        // 设置监听
        id_drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int newState) {}

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = id_drawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals(
                        "LEFT")) {// 展开左侧菜单
                    float leftScale = 1 - 0.3f * scale;

                    // 设置左侧菜单缩放效果
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));

                    // 设置中间View缩放效果
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            // 菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
            }

            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                id_drawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_chat) {
            //选择聊天列表界面
            initChat();
            selectUI(SELECT_CHAT);

        }
        if (id == R.id.rl_friend) {
            //选择好友列表界面
            initFriend();
            selectUI(SELECT_FRIEND);
        }
        if (id == R.id.rl_home) {
            //选择家界面
            initMy();
            selectUI(SELECT_HOME);
        }
    }

    public void initChat() {
        if (fg_chat == null) {
            fg_chat = new FragmentChatList();
        }
        fragmentManager.beginTransaction().replace(R.id.ll_content, fg_chat).addToBackStack(null).commit();
    }

    public void initFriend() {
        if (fg_friend == null) {
            fg_friend = new FragmentFriendList();
        }
        fragmentManager.beginTransaction().replace(R.id.ll_content, fg_friend).addToBackStack(null).commit();
    }

    public void initMy() {
        if (fg_my == null) {
            fg_my = new FragmentMy();
        }
        fragmentManager.beginTransaction().replace(R.id.ll_content, fg_my).addToBackStack(null).commit();
    }

    private void selectUI(int i) {
        switch (i) {
            case SELECT_CHAT:
                setBottomStyle();
                tv_chat.setTextColor(getResources().getColor(R.color.new_nlp_blue));
                iv_chat.setBackground(getResources().getDrawable(R.mipmap.tv_home_on));
                iv_friend.setBackground(getResources().getDrawable(R.mipmap.tv_my_off));
                iv_home.setBackground(getResources().getDrawable(R.mipmap.tv_find_off));
                break;
            case SELECT_FRIEND:
                setBottomStyle();
                tv_friend.setTextColor(getResources().getColor(R.color.new_nlp_blue));
                iv_friend.setBackground(getResources().getDrawable(R.mipmap.tv_my_on));
                iv_chat.setBackground(getResources().getDrawable(R.mipmap.tv_home_off));
                iv_home.setBackground(getResources().getDrawable(R.mipmap.tv_find_off));
                break;
            case SELECT_HOME:
                setBottomStyle();
                tv_home.setTextColor(getResources().getColor(R.color.new_nlp_blue));
                iv_friend.setBackground(getResources().getDrawable(R.mipmap.tv_my_off));
                iv_chat.setBackground(getResources().getDrawable(R.mipmap.tv_home_off));
                iv_home.setBackground(getResources().getDrawable(R.mipmap.tv_find_on));
                break;
        }
    }

    private void setUI() {

    }

    public void setBottomStyle() {
        textViews = new TextView[]{tv_chat, tv_friend, tv_home};
        imageViews = new ImageView[]{iv_chat, iv_friend, iv_home};
        for (int i = 0; i < 3; i++) {
            textViews[i].setTextColor(getResources().getColor(R.color.n_content));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToast("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(ActivityHome.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public String getTitleCenter() {
        return null;
    }

    public void OpenLeftMenu(View view){
        id_drawerLayout.openDrawer(Gravity.LEFT);// 展开侧边的菜单
        id_drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);// 打开手势滑动
    }
}
