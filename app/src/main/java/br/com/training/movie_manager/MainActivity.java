package br.com.training.movie_manager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class for main activity.
 *
 * @author Jefferson Sampaio de Medeiros <jefferson.medeiros@nutes.uepb.edu.br>
 * @copyright Copyright (c) 2020, NUTES/UEPB
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_bottom)
    ActionMenuView bottomBar;

    public static final String API_KEY = "2a946dd20bb20e6eed31ee5e8e8687c9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Menu bottomMenu = (Menu) bottomBar.getMenu();

        getMenuInflater().inflate(R.menu.menu_main, bottomMenu);

        for (int i = 0; i < bottomMenu.size(); i++){
            bottomMenu.getItem(i).setOnMenuItemClickListener(item -> {
                this.resetMenuItemIcons(bottomMenu);

                return onOptionsItemSelected(item);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, homeFragment).commit();

                menuItem.setIcon(R.drawable.ic_action_home_clicked);

                break;
            case R.id.action_search:
                SearchFragment searchFragment= new SearchFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, searchFragment).commit();

                menuItem.setIcon(R.drawable.ic_action_search_clicked);

                break;
            case R.id.action_fav:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, favoriteFragment).commit();

                menuItem.setIcon(R.drawable.ic_action_favorite_clicked);

                break;
            case R.id.action_more:
                menuItem.setIcon(R.drawable.ic_action_more_options_clicked);

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void resetMenuItemIcons(Menu menu) {
        menu.getItem(0).setIcon(R.drawable.ic_action_home);
        menu.getItem(1).setIcon(R.drawable.ic_action_search);
        menu.getItem(2).setIcon(R.drawable.ic_action_favorite);
        menu.getItem(3).setIcon(R.drawable.ic_action_more_options);
    }
}
