package ap.ky.stopwatch;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by kylin25 on 2018/2/20.
 */

public class PgeAdapter extends android.support.v4.view.PagerAdapter {
    private List<View> lstPage;
    Context context;

    public PgeAdapter(Context context) {
        this.context = context;
    }

    public PgeAdapter(List<View> views) {
        this.lstPage = views;
    }

    @Override
    public int getCount() {
        return lstPage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = lstPage.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
