package com.github.overotherSample;

import com.github.overlayApp.BootCompleteReceiver;

/**
 * Created by max on 08.01.16.
 */
public class ImplBootCompleteReceiver extends BootCompleteReceiver {

    @Override
    protected Class getSurvivableForegroundServiceImpl() {
        return ImplSurvivableForegroundService.class;
    }

}
