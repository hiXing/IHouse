package silverlion.com.house.commous;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class UserPrefs {
    private static final String PREFS_NAME = "user_info";
    private static final String KEY_AVATAR = "key_avatar";
    private static final String KEY_CURRENT_USER = "key_current_user";
    private static UserPrefs sInstance;

    public static void init(Context context) {
        sInstance = new UserPrefs(context);
    }

    public static UserPrefs getInstance() {
        return sInstance;
    }

    private final SharedPreferences preferences;

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    private int tokenId;

    private UserPrefs(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private  @Nullable String getCurrentUser() {
        return preferences.getString(KEY_CURRENT_USER, null);
    }

    public void setCurrentUser(String username) {
        preferences.edit().putString(KEY_CURRENT_USER, username).apply();
    }

    public @Nullable String getAvatar() {
        return preferences.getString(KEY_AVATAR + getCurrentUser(), null);
    }

    public void setAvatar(String avatarUrl) {
        preferences.edit().putString(KEY_AVATAR + getCurrentUser(), avatarUrl).apply();
    }
}
