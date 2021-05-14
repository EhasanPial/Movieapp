package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ui.Cast;
import ui.Info;
import ui.Reviews;

public class ViewPageAdapter extends FragmentPagerAdapter {

    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Info();
            case 1:
                return new Cast();
            case 2:
                return new Reviews();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "INFO";
        }
        else if (position == 1)
        {
            title = "CAST";
        }
        else if (position == 2)
        {
            title = "REVIEWS";
        }
        return title;
    }
}
