package com.charlyge.android.globalacelchallengeapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.charlyge.android.globalacelchallengeapp.R;

/**
 * Created by DELL PC on 7/14/2018.
 */

public class pagePreference {
    public static String changePageNo(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String pageKey = context.getString(R.string.pagekey);
        String defaultt = context.getString(R.string.page_default);
        return prefs.getString(pageKey,defaultt);

    }

}
