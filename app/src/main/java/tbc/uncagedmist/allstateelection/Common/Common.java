package tbc.uncagedmist.allstateelection.Common;

import android.content.Context;
import android.content.Intent;

public class Common {
    public static final String HOME_PAGE = "http://sec.bihar.gov.in/";
    public static final String KNOW_BOOTH = "http://sec2021.bihar.gov.in/claim-objection/booth-list";
    public static final String ELECTORAL_ROLL = "http://sec2021.bihar.gov.in/claim-objection/download";
    public static final String SEARCH_ROLL = "http://sec2021.bihar.gov.in/claim-objection/search";
    public static final String PANCHAYAT_LIST = "http://sec2021.bihar.gov.in/claim-objection/download";
    public static final String CONTESTING_CANDIDATES = "http://sec.bihar.gov.in/archive";
    public static final String PHOTO_GALLERY = "http://sec2021.bihar.gov.in/Admin/gallery.aspx";
    public static final String NOMINATION = "http://sec.bihar.gov.in/OnlineOffline.aspx";
    public static final String CONTESTING_21 = "http://sec2021.bihar.gov.in/nomination/PUBLICVIEW_NOMINATION.ASPX";
    public static final String ELECTION_RESULT = "http://sec2021.bihar.gov.in/claim-objection/NominationPhase.aspx";

    //Voter id URLs
    public static final String VOTER_REPRINT = "https://voterportal.eci.gov.in/";
    public static final String TRACK_VOTER = "https://www.nvsp.in/Forms/trackstatus";
    public static final String VOTER_SERVICES = "https://www.nvsp.in/";
    public static final String SEARCH_VOTER = "https://electoralsearch.in/";
    public static final String APPLY_VOTER = "https://www.nvsp.in/Account/Login";
    public static final String DOWNLOAD_VOTER = "https://www.nvsp.in/Home/DownloadPdf";
    public static final String EDIT_VOTER = "https://www.nvsp.in/Account/Login";

    public static final String WIN_URL = "https://894.win.qureka.com";

    public static final String PRIVACY_URL =
            "https://docs.google.com/document/d/1RisIrR9KrC2kQTu9rWGxemVYGLVY6db9u-KvrQnPOk4/edit?usp=sharing";

    public static String CurrentURL;
    public static String CurrentName;

    public static void shareApp(Context context)    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Never Miss A Thing About Panchayat Election. Install Panchayat Election App and Stay Updated! \n https://play.google.com/store/apps/details?id="+ context.getPackageName();
        intent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(intent, "Share Panchayat Election  App Using"));
    }

}
