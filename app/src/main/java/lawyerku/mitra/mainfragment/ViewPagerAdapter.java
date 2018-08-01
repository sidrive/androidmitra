package lawyerku.mitra.mainfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new PerkaraOnProgressFragment();
        }
        else if (position == 1)
        {
            fragment = new PerkaraNewFragment();
        }
        else if (position == 2)
        {
            fragment = new PerkaraRejectedFragment();
        }
        else if (position == 3)
        {

            fragment = new HistoryFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "On Progress";
        }
        else if (position == 1)
        {
            title = "New Case";
        }
        else if (position == 2)
        {
            title = "Rejected Case";
        }
        else if (position == 3)
        {
            title = "History";
        }

        return title;
    }
}
