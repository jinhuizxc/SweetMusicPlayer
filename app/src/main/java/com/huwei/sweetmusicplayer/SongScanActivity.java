package com.huwei.sweetmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.interfaces.OnScanListener;
import com.huwei.sweetmusicplayer.models.MusicInfo;
import com.huwei.sweetmusicplayer.util.MusicUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_songscan)
public class SongScanActivity extends BaseActivity {

    private int songCount = 0 ;
    private boolean mAutoEnterMain;
    private MusicUtils mMusicUtils = new MusicUtils(this);

    @ViewById
    TextView scannow_tv;
    @ViewById
    TextView scancount_tv;
    @ViewById
    Button scanfinish_btn;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void init(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("歌曲扫描");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initData();
    }

    void initData(){
        mAutoEnterMain = getIntent().getBooleanExtra(IntentExtra.EXTRA_AUTO_ENTERMAIN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicUtils.setOnScanListener(new OnScanListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext,"扫描完毕",Toast.LENGTH_SHORT).show();
                checkToMain();
            }

            @Override
            public void onFail() {
                Toast.makeText(mContext,"扫描失败",Toast.LENGTH_SHORT).show();
                checkToMain();
            }

            @Override
            public void onScan(MusicInfo musicInfo) {
                songCount ++ ;
                scancount_tv.setText(String.valueOf(songCount));
                scannow_tv.setText(musicInfo.getTitle() + "--" + musicInfo.getPath());
            }
        });

        mMusicUtils.startScan();
    }

    private void checkToMain(){
        if (mAutoEnterMain) {
            startActivity(MainActivity.getStartActIntent(mContext));
        }
    }

    public static Intent getStartActIntent(Context from){
        return getStartActIntent(from, false);
    }

    public static Intent getStartActIntent(Context from, boolean autoEnterMain){
        Intent intent = new Intent(from,SongScanActivity_.class);
        intent.putExtra(IntentExtra.EXTRA_AUTO_ENTERMAIN, autoEnterMain);
        return intent;
    }
}
 
 




