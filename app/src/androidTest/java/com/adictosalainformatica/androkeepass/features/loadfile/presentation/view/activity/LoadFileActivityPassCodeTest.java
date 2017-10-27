package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.adictosalainformatica.androkeepass.R;

import junit.framework.Assert;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wordpress.passcodelock.AppLockManager;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoadFileActivityPassCodeTest {

    @Rule
    public ActivityTestRule<LoadFileActivity> activityTestRule = new ActivityTestRule<>(LoadFileActivity.class);

    @Test
    public void loadFileActivityPassCode1Test() throws InterruptedException {
        Thread.sleep(4000);

        // check appLock is disabled
        onView(withId(R.id.menu_passcodelock)).perform(click());
        onView(withText("Enable app lock in setting"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        // enable appLock
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Opcions"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(android.R.id.list),
                        1),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.button1), withText("1")));
        button3.perform(scrollTo(), click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.button2), withText("2")));
        button4.perform(scrollTo(), click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.button3), withText("3")));
        button5.perform(scrollTo(), click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.button4), withText("4")));
        button6.perform(scrollTo(), click());

        ViewInteraction button7 = onView(
                allOf(withId(R.id.button1), withText("1")));
        button7.perform(scrollTo(), click());

        ViewInteraction button8 = onView(
                allOf(withId(R.id.button2), withText("2")));
        button8.perform(scrollTo(), click());

        ViewInteraction button9 = onView(
                allOf(withId(R.id.button3), withText("3")));
        button9.perform(scrollTo(), click());

        ViewInteraction button10 = onView(
                allOf(withId(R.id.button4), withText("4")));
        button10.perform(scrollTo(), click());

        // check appLock is enabled
        Assert.assertTrue(AppLockManager.getInstance().isAppLockFeatureEnabled());

        // disable appLock
        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        withId(android.R.id.list),
                        1),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction button11 = onView(
                allOf(withId(R.id.button1), withText("1")));
        button11.perform(scrollTo(), click());

        ViewInteraction button12 = onView(
                allOf(withId(R.id.button2), withText("2")));
        button12.perform(scrollTo(), click());

        ViewInteraction button13 = onView(
                allOf(withId(R.id.button3), withText("3")));
        button13.perform(scrollTo(), click());

        ViewInteraction button14 = onView(
                allOf(withId(R.id.button4), withText("4")));
        button14.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
