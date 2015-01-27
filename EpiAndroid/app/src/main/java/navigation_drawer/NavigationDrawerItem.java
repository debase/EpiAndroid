package navigation_drawer;

/**
 * Created by debas on 25/01/15.
 */
public class NavigationDrawerItem {
    private int mSectionIcon;
    private String mSectionString;

    public NavigationDrawerItem(int iconId, String str) {
        mSectionIcon = iconId;
        mSectionString = str;
    }

    public int getSectionIcon() {
        return mSectionIcon;
    }

    public String getSectionString() {
        return mSectionString;
    }
}
