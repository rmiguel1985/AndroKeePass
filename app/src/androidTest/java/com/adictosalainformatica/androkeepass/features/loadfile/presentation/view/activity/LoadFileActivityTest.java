/*
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                  Version 2, December 2004
 *
 *      Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *      Everyone is permitted to copy and distribute verbatim or modified
 *      copies of this license document, and changing it is allowed as long
 *      as the name is changed.
 *
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *      TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *      0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.adictosalainformatica.androkeepass.R;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.adictosalainformatica.androkeepass.matchers.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.not;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class LoadFileActivityTest {

    @Rule
    public ActivityTestRule<LoadFileActivity> activityRule = new ActivityTestRule<>(LoadFileActivity.class);
    private Context context;
    private String databaseName = "01234";

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown(){
        context = null;
    }

    @Test
    public void createDatabase_creates_expected_database() throws Exception {
        onView(withId(R.id.loadfile_button_create_file)).perform(click());

        onView(withHint(context.getString(R.string.loadfile_cretate_database_dialog_database))).perform(typeText(databaseName), closeSoftKeyboard());
        onView(withHint(context.getString(R.string.password))).perform(typeText("1234"), closeSoftKeyboard());
        onView(withHint(context.getString(R.string.repeat_password))).perform(typeText("1234"),
                closeSoftKeyboard());

        onView(withText(context.getString(R.string.loadfile_create_database_button))).perform(click());

        onView(withText(context.getString(R.string.loadfile_create_database_toast_message, databaseName)))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deleteDatabase_deletes_expected_database() throws Exception {
        Thread.sleep(4000);

        onView(withRecyclerView(R.id.loadfile_recycler_view_recents_databases)
                .atPositionOnView(0, R.id.loadfile_textview_database_list_item))
                .perform(longClick());

        onView(withText(context.getString(R.string.ok))).perform(click());

        onView(withText(context.getString(R.string.loadfile_delete_database_toast_message, databaseName+".kdbx")))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}