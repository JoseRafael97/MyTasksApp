package br.com.mytasks.controller.activity.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Date;

import br.com.mytasks.controller.activity.fragments.PageEventHomeFragment;
import br.com.mytasks.controller.activity.fragments.PageHomeFragment;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.validation.DateValidation;


public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;
    private Date dateCurrent;


    public TabsAdapter(FragmentManager fm, Context context, Date date) {
        super(fm);
        this.context = context;
        this.dateCurrent = date;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = PageHomeFragment.newInstance(position, this.context);
                break;
            case 1:
                frag = PageEventHomeFragment.newInstance(position, this.context);
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {

        return  2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        String aux = "Today";

        if(!DateValidation.equalsDateForDay(dateCurrent, new Date())){
            aux  = DateConversor.dateToStringConversor(dateCurrent);
        }

        switch (position) {

            case 0:

                title = "activities  " + aux;
                break;
            case 1:
                title = "Events  " + aux;
                break;
        }
        return title;
    }

}
