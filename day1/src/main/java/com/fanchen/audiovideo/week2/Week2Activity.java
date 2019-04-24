package com.fanchen.audiovideo.week2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanchen.audiovideo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 功能描述: 在 Android 平台使用 AudioRecord 和 AudioTrack API 完成音频 PCM 数据的采集和播放，并实现读写音频 wav 文件
 * 作者:fanChen
 * 时间:2019/4/20
 * 联系方式：1546479204@qq.com
 */
public class Week2Activity extends AppCompatActivity {

    AudioRecord audioRecord;
    Handler handler;
    boolean isRecording;
    int bufferSizeInBytes;

    File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week2);

        //1、音频来源
        int audioSource = MediaRecorder.AudioSource.MIC;
        //2、用hz表示的采样率： 44100hz所有设备通用；
        //                  AudioFormat.SAMPLE_RATE_UNSPECIFIED未指定依赖于源的采样率
        int sampleRateInHz = 44100;
        //3、通道配置： CHANNEL_IN_MONO通用；CHANNEL_IN_STEREO
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        //4、音频格式：ENCODING_PCM_8BIT,ENCODING_PCM_16BIT,ENCODING_PCM_FLOAT
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        //5、用byte表示缓存的大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
        saveFile = new File(getExternalCacheDir(), "file.pcm");
    }

    //采集音频并保存成pcm格式
    private void takeData() {

    }

    public void startCollect(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            Log.e("sun", "---->have record audio permission");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.e("sun", "---->have write  permission");
        }
        isRecording = true;
        audioRecord.startRecording();
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffers = new byte[bufferSizeInBytes];
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(saveFile);
                    Log.e("sun", "----->file is success");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                while (isRecording) {//不断读取音频数据，保存到文件中
                    int length = audioRecord.read(buffers, 0, bufferSizeInBytes);
                    if (length >= AudioRecord.SUCCESS) {
                        try {
                            if (fos != null) {
                                Log.e("sun", "------->start write");
                                fos.write(buffers, 0, length);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    fos.flush();
                    fos.close();
                    Log.e("sun", "------>write success");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopCollect(View view) {
        isRecording = false;
        audioRecord.stop();
    }

    public void play(View view) {
    }

    public boolean makePcmFileToWavFile(String srcPath, String destPath, boolean deleteSrcFile) {
        byte buffer[] = null;
        long total = 0;
        File file = new File(srcPath);
        if (!file.exists()) {
            return false;
        }
        total = file.length();
        return false;

    }

    public void addWavHeader(long fileSize) {
        char header[] = new char[44];
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';

        int size = (int) (fileSize - 8);
        header[4] = (char) (size & 0xff);
        header[5] = (char) (size >> 8 & 0xff);
        header[6] = (char) (size >> 16 & 0xff);
        header[7] = (char) (size >> 24 & 0xff);
    }


}
