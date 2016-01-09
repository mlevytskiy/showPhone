package com.github.overotherSample;

import com.github.overlayApp.ActivityOverOther;

/**
 * Created by max on 08.01.16.
 */
public class TestActivity extends ActivityOverOther {

    @Override
    protected Class getSurvivableForegroundServiceImpl() {
        return ImplSurvivableForegroundService.class;
    }

}
