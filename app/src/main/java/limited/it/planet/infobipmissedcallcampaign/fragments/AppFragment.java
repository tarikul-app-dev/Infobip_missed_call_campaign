package limited.it.planet.infobipmissedcallcampaign.fragments;

/**
 * Created by Tarikul on 2/26/2018.
 */
import android.support.v4.app.Fragment;

import limited.it.planet.infobipmissedcallcampaign.util.ToolbarUI;

public class AppFragment extends Fragment {

    private ToolbarUI mToolbarListener;

    public void setToolbarUI(ToolbarUI t, String type){
        mToolbarListener = t;
        t.setToolbarType(type);
    }
}
