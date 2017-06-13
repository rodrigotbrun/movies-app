package br.com.newestapps.movie.events;

import android.support.v4.app.Fragment;

public class ChangeFragment {

    private Fragment fragment;

    public ChangeFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
