package awais.instagrabber.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import awais.instagrabber.R;
import awais.instagrabber.databinding.ActivityDirectMessagesBinding;
import awais.instagrabber.fragments.directmessages.DirectMessageThreadFragmentArgs;

public class DirectMessagesActivity extends BaseLanguageActivity implements NavController.OnDestinationChangedListener {

    private TextView toolbarTitle;
    private AppCompatImageView infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDirectMessagesBinding binding = ActivityDirectMessagesBinding.inflate(getLayoutInflater());
        final CoordinatorLayout root = binding.getRoot();
        setContentView(root);

        toolbarTitle = binding.toolbarTitle;

        final Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        infoButton = binding.dmInfo;

        final NavController navController = Navigation.findNavController(this, R.id.direct_messages_nav_host_fragment);
        navController.addOnDestinationChangedListener(this);
        final AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    @Override
    public void onDestinationChanged(@NonNull final NavController controller,
                                     @NonNull final NavDestination destination,
                                     @Nullable final Bundle arguments) {
        switch (destination.getId()) {
            case R.id.directMessagesInboxFragment:
                setToolbarTitle(R.string.action_dms);
                infoButton.setVisibility(View.GONE);
                return;
            case R.id.directMessagesThreadFragment:
                if (arguments == null) {
                    return;
                }
                final String title = DirectMessageThreadFragmentArgs.fromBundle(arguments).getTitle();
                setToolbarTitle(title);
                infoButton.setVisibility(View.VISIBLE);
                return;
            case R.id.directMessagesSettingsFragment:
                if (arguments == null) {
                    return;
                }
                setToolbarTitle(R.string.action_settings);
                infoButton.setVisibility(View.GONE);
                return;
        }
    }

    private void setToolbarTitle(final String text) {
        if (toolbarTitle == null) {
            return;
        }
        toolbarTitle.setText(text);
    }

    private void setToolbarTitle(final int resourceId) {
        if (toolbarTitle == null) {
            return;
        }
        toolbarTitle.setText(resourceId);
    }
}
