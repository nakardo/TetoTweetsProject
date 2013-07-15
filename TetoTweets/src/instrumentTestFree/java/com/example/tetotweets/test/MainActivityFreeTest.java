package com.example.tetotweets.test;

import android.R;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

import com.example.tetotweets.MainActivity;
import com.jayway.android.robotium.solo.Condition;
import com.jayway.android.robotium.solo.Solo;

/**
 * Created by dacosta on 7/14/13.
 */
public class MainActivityFreeTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo mSolo;

    public MainActivityFreeTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    public void testActivityTitle() {
        assertTrue("Activity title is not correct", mSolo.searchText("Teto Tweets Free"));
    }

    public void testPaging() throws Exception {
        final ListView listView = (ListView) mSolo.getView(R.id.list);
        boolean isVisible = mSolo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return listView.getVisibility() == View.VISIBLE;
            }
        }, 5000);

        assertTrue("Failed to load initial 20 tweets", isVisible);
        mSolo.scrollListToBottom(listView);

        boolean hasLoadedAnotherPage = mSolo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return listView.getAdapter().getCount() == 40;
            }
        }, 5000);

        assertTrue("Failed to load another page", hasLoadedAnotherPage);

        boolean isDialogVisible = mSolo.waitForDialogToOpen(2000);
        assertTrue("Dialog is not visible", isDialogVisible);
    }

    @Override
    protected void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
    }
}
