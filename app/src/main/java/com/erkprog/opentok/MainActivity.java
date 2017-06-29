package com.erkprog.opentok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static String API_KEY = "45903602";
    private static String SESSION_ID = "2_MX40NTkwMzYwMn5-MTQ5ODcyMDEwMzk0MX5GTm1aN2kxMDJwSFg0ZFhqbUhLNTVKdi9-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NTkwMzYwMiZzaWc9NDNiNjI2YTQ5NjAwOWJmMTE3N2QxODM5NDAxMDIwZjdkYTI2MzQ3NzpzZXNzaW9uX2lkPTJfTVg0ME5Ua3dNell3TW41LU1UUTVPRGN5TURFd016azBNWDVHVG0xYU4ya3hNREp3U0ZnMFpGaHFiVWhMTlRWS2RpOS1mZyZjcmVhdGVfdGltZT0xNDk4NzIwMTM3Jm5vbmNlPTAuMTc5NDE2Njk0MDM5MjQxNDMmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTQ5ODc0MTczNQ==";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
