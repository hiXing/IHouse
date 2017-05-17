package com.next.message;

/**
 * <^^>
 * Created by hiXing on 2017/5/16.
 */

public class SHMsgManager {
    private static SHMsgManager manager;
    private SHReel mReel;
    public static SHMsgManager getInstance() {
        if (manager == null){
            manager = new SHMsgManager();
        }
        return manager;
    }

    public void setReel(SHReel reel){
        mReel = reel;
    }
    public SHReel getReel(){
        return mReel;
    }
}
